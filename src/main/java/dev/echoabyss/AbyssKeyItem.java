package dev.echoabyss;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;

/** Right click: go to the Echo Abyss dimension. Right click again inside it: come back. */
public class AbyssKeyItem extends Item {
	public static final ResourceKey<Level> ABYSS = ResourceKey.create(Registries.DIMENSION, EchoAbyss.id("echo_abyss"));

	private record Origin(ResourceKey<Level> level, Vec3 pos) {
	}

	private static final Map<UUID, Origin> ORIGINS = new HashMap<>();
	private final Map<UUID, Long> lastUse = new HashMap<>();

	public AbyssKeyItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player user, InteractionHand hand) {
		if (!(level instanceof ServerLevel from) || !(user instanceof ServerPlayer player)) {
			return InteractionResult.PASS;
		}

		long now = from.getGameTime();
		Long last = lastUse.get(player.getUUID());
		if (last != null && now >= last && now - last < 60) {
			return InteractionResult.FAIL;
		}
		lastUse.put(player.getUUID(), now);

		MinecraftServer server = from.getServer();

		if (from.dimension().equals(ABYSS)) {
			Origin origin = ORIGINS.get(player.getUUID());
			ServerLevel target = origin != null ? server.getLevel(origin.level()) : null;
			Vec3 pos;
			if (target != null) {
				pos = origin.pos();
			} else {
				target = server.overworld();
				pos = Vec3.atCenterOf(target.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlockPos.ZERO));
			}
			go(player, target, pos);
			return InteractionResult.SUCCESS;
		}

		ServerLevel abyss = server.getLevel(ABYSS);
		if (abyss == null) {
			EchoAbyss.LOGGER.warn("Echo Abyss dimension is not loaded (create a NEW world to test it).");
			return InteractionResult.FAIL;
		}
		ItemStack stack = player.getItemInHand(hand);
		stack.hurtAndBreak(1, player, hand);
		ORIGINS.put(player.getUUID(), new Origin(from.dimension(), player.position()));
		go(player, abyss, findSpot(abyss));
		return InteractionResult.SUCCESS;
	}

	private static void go(ServerPlayer player, ServerLevel target, Vec3 pos) {
		ServerLevel source = (ServerLevel) player.level();
		burst(source, player.position());
		player.teleport(new TeleportTransition(target, pos, Vec3.ZERO, player.getYRot(), player.getXRot(), TeleportTransition.DO_NOTHING));
		burst(target, pos);
	}

	private static void burst(ServerLevel level, Vec3 pos) {
		level.sendParticles(ModParticles.ECHO_WISP, pos.x, pos.y + 1.0, pos.z, 90, 0.5, 1.0, 0.5, 0.6);
		level.sendParticles(ModParticles.ECHO_SPARK, pos.x, pos.y + 1.0, pos.z, 40, 0.3, 0.8, 0.3, 0.1);
		level.playSound((Player) null, pos.x, pos.y, pos.z, ModSounds.ECHO_PORTAL, SoundSource.PLAYERS, 1.0F, 1.0F);
	}

	/** Looks for open ground under the cave ceiling; builds a small platform if nothing is found. */
	private static Vec3 findSpot(ServerLevel level) {
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		for (int r = 0; r <= 96; r += 8) {
			for (int i = 0; i < 8; i++) {
				double angle = Math.PI * 2.0 * i / 8.0;
				int x = (int) Math.round(Math.cos(angle) * r);
				int z = (int) Math.round(Math.sin(angle) * r);

				pos.set(x, 150, z);
				if (!level.getBlockState(pos).isAir()) {
					continue;
				}
				for (int y = 149; y > 5; y--) {
					pos.set(x, y, z);
					if (level.getBlockState(pos).isAir()) {
						continue;
					}
					pos.set(x, y + 1, z);
					boolean free1 = level.getBlockState(pos).isAir();
					pos.set(x, y + 2, z);
					boolean free2 = level.getBlockState(pos).isAir();
					if (free1 && free2) {
						return new Vec3(x + 0.5, y + 1, z + 0.5);
					}
					break;
				}
			}
		}

		for (int dx = -1; dx <= 1; dx++) {
			for (int dz = -1; dz <= 1; dz++) {
				level.setBlockAndUpdate(new BlockPos(dx, 99, dz), Blocks.STONE.defaultBlockState());
				for (int dy = 100; dy <= 102; dy++) {
					level.setBlockAndUpdate(new BlockPos(dx, dy, dz), Blocks.AIR.defaultBlockState());
				}
			}
		}
		return new Vec3(0.5, 100, 0.5);
	}
}
