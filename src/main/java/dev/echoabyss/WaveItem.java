package dev.echoabyss;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/** Right click: makes a sound wave (visible ring, mobs come to it). */
public class WaveItem extends Item {
	private final Map<UUID, Long> lastUse = new HashMap<>();
	private final double radius;
	private final double ahead;
	private final int cooldownTicks;
	private final SoundEvent sound;
	private final float pitch;

	/** ahead = 0: wave at the player. ahead > 0: wave that many blocks in front (distraction). */
	public WaveItem(Item.Properties properties, double radius, double ahead, int cooldownTicks, SoundEvent sound, float pitch) {
		super(properties);
		this.radius = radius;
		this.ahead = ahead;
		this.cooldownTicks = cooldownTicks;
		this.sound = sound;
		this.pitch = pitch;
	}

	@Override
	public InteractionResult use(Level level, Player user, InteractionHand hand) {
		if (!(level instanceof ServerLevel serverLevel)) {
			return InteractionResult.PASS;
		}

		long now = serverLevel.getGameTime();
		Long last = lastUse.get(user.getUUID());
		if (last != null && now >= last && now - last < cooldownTicks) {
			return InteractionResult.FAIL;
		}
		lastUse.put(user.getUUID(), now);

		Vec3 origin = ahead > 0.0
			? user.getEyePosition().add(user.getLookAngle().scale(ahead))
			: user.position();

		SoundWaves.emit(serverLevel, origin, radius);
		serverLevel.sendParticles(ModParticles.ECHO_NOTE, origin.x, origin.y + 0.6, origin.z, 8, 0.5, 0.5, 0.5, 0.02);
		serverLevel.playSound((Player) null, origin.x, origin.y, origin.z, sound, SoundSource.PLAYERS, 1.0F, pitch);
		return InteractionResult.SUCCESS;
	}
}
