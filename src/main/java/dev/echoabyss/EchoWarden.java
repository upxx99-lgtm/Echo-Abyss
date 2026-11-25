package dev.echoabyss;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;

public class EchoWarden extends MiniWarden {
    public EchoWarden(EntityType<? extends Warden> type, Level level) {
        super(type, level, BossEvent.BossBarColor.PURPLE);
    }

    @Override
    protected SoundEvent ambientSound() {
        return ModSounds.ECHO_WARDEN_AMBIENT;
    }

    @Override
    protected SoundEvent hurtSound() {
        return ModSounds.ECHO_WARDEN_HURT;
    }

    @Override
    protected SoundEvent roarSound() {
        return ModSounds.ECHO_WARDEN_ROAR;
    }

    @Override
    protected SoundEvent deathSound() {
        return ModSounds.ECHO_WARDEN_DEATH;
    }
}
