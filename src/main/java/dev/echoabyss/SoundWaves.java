package dev.echoabyss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Stage 1 of Echo Abyss: every noise becomes a visible expanding ring,
 * and hostile mobs that "hear" it walk to where it came from.
 *
 * Noise sources: walking (small), sprinting (big), breaking blocks (big).
 * Sneaking is silent. Wool under your feet makes steps one level quieter.
 */
public final class SoundWaves {
	// Stage 1: works in every dimension so it is easy to test.
	// Stage 2: set to false, then it only works in the Echo Abyss dimension.
	private static final boolean EVERYWHERE = true;
	private static final ResourceKey<Level> ABYSS = ResourceKey.create(Registries.DIMENSION, EchoAbyss.id("echo_abyss"));

	private static final double WALK_RADIUS = 6.0;
	private static final double SPRINT_RADIUS = 14.0;
	private static final double BREAK_RADIUS = 12.0;

	private static final double WAVE_STEP = 1.6; // blocks per processing step (every 2 ticks)
	private static final int MAX_WAVES = 40;
	private static final int MOVE_SAMPLE_TICKS = 10;
	private static final double MIN_MOVE_SQ = 1.0; // must move at least 1 block per sample to make noise
	private static final double MAX_MOVE_SQ = 100.0; // ignore teleports

	private static final List<Wave> WAVES = new ArrayList<>();
	private static Map<UUID, Vec3> lastPositions = new HashMap<>();
	private static int tick = 0;

	private SoundWaves() {
	}

	private static final class Wave {
		final ServerLevel level;
		final Vec3 origin;
		final double maxRadius;
		double radius = 0.0;

		Wave(ServerLevel level, Vec3 origin, double maxRadius) {
			this.level = level;
			this.origin = origin;
			this.maxRadius = maxRadius;
		}
	}

	public static void initialize() {
		ServerTickEvents.END_SERVER_TICK.register(SoundWaves::onServerTick);

		PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
			if (level instanceof ServerLevel serverLevel) {
				emit(serverLevel, Vec3.atCenterOf(pos), BREAK_RADIUS);
			}
		});

		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			WAVES.clear();
			lastPositions = new HashMap<>();
		});
	}

	/** Creates a new sound wave. Other code (items, mobs, explosions) can call this later. */
	public static void emit(ServerLevel level, Vec3 origin, double maxRadius) {
		if (!EVERYWHERE && !level.dimension().equals(ABYSS)) {
			return;
		}
		if (WAVES.size() >= MAX_WAVES) {
			return;
		}
		WAVES.add(new Wave(level, origin, maxRadius));
		if (maxRadius >= 10.0) {
			// big noise: flash at the source
			level.sendParticles(ModParticles.ECHO_RING, origin.x, origin.y + 1.0, origin.z, 12, 0.6, 0.4, 0.6, 0.02);
			level.sendParticles(ModParticles.ECHO_SPARK, origin.x, origin.y + 0.3, origin.z, 24, 0.4, 0.1, 0.4, 0.12);
		}
	}

	private static void onServerTick(MinecraftServer server) {
		tick++;
		if (tick % MOVE_SAMPLE_TICKS == 0) {
			sampleMovement(server);
		}
		if (tick % 2 == 0) {
			advanceWaves();
		}
	}

	private static void sampleMovement(MinecraftServer server) {
		Map<UUID, Vec3> next = new HashMap<>();

		for (ServerPlayer player : server.getPlayerList().getPlayers()) {
			Vec3 pos = player.position();
			next.put(player.getUUID(), pos);

			Vec3 prev = lastPositions.get(player.getUUID());
			if (prev == null || player.isSpectator() || player.isShiftKeyDown()) {
				continue;
			}

			double dx = pos.x - prev.x;
			double dz = pos.z - prev.z;
			double distSq = dx * dx + dz * dz;
			if (distSq < MIN_MOVE_SQ || distSq > MAX_MOVE_SQ) {
				continue;
			}

			if (!(player.level() instanceof ServerLevel level)) {
				continue;
			}

			double radius = player.isSprinting() ? SPRINT_RADIUS : WALK_RADIUS;
			if (isMuffled(level, player)) {
				radius = (radius == SPRINT_RADIUS) ? WALK_RADIUS : 0.0;
			}
			if (radius > 0.0) {
				emit(level, pos, radius);
			}
		}

		lastPositions = next;
	}

	private static boolean isMuffled(ServerLevel level, ServerPlayer player) {
		BlockPos feet = player.blockPosition();
		return isWool(level.getBlockState(feet)) || isWool(level.getBlockState(feet.below()));
	}

	private static boolean isWool(BlockState state) {
		return state.is(BlockTags.WOOL) || state.is(BlockTags.WOOL_CARPETS);
	}

	private static void advanceWaves() {
		Iterator<Wave> iterator = WAVES.iterator();
		while (iterator.hasNext()) {
			Wave wave = iterator.next();
			double previous = wave.radius;
			wave.radius += WAVE_STEP;

			attractMobs(wave, previous, wave.radius);
			drawRing(wave);

			if (wave.radius >= wave.maxRadius) {
				iterator.remove();
			}
		}
	}

	/** Hostile mobs that the wave just passed walk toward its source. */
	private static void attractMobs(Wave wave, double previous, double current) {
		Vec3 o = wave.origin;
		AABB box = new AABB(o.x - current, o.y - current, o.z - current, o.x + current, o.y + current, o.z + current);

		for (Mob mob : wave.level.getEntitiesOfClass(Mob.class, box)) {
			if (mob.getType().getCategory() != MobCategory.MONSTER) {
				continue;
			}
			if (mob.getTarget() != null) {
				continue;
			}
			double distSq = mob.distanceToSqr(o.x, o.y, o.z);
			if (distSq > previous * previous && distSq <= current * current) {
				mob.getNavigation().moveTo(o.x, o.y, o.z, 1.0);
			}
		}
	}

	private static void drawRing(Wave wave) {
		double r = wave.radius;
		int points = (int) Math.max(16, Math.min(96, r * 4));
		for (int i = 0; i < points; i++) {
			double angle = (Math.PI * 2.0 * i) / points;
			double x = wave.origin.x + Math.cos(angle) * r;
			double z = wave.origin.z + Math.sin(angle) * r;
			wave.level.sendParticles(ModParticles.ECHO_SPARK, x, wave.origin.y + 0.2, z, 1, 0.0, 0.15, 0.0, 0.02);
			if (i % 3 == 0) {
				wave.level.sendParticles(ModParticles.ECHO_WISP, x, wave.origin.y + 0.6, z, 1, 0.1, 0.3, 0.1, 0.0);
			}
		}
	}
}
