package dev.echoabyss;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;

public class BellWarden extends MiniWarden {
    public BellWarden(EntityType<? extends Warden> type, Level level) {
        super(type, level, BossEvent.BossBarColor.BLUE);
    }

    @Override
    protected SoundEvent ambientSound() {
        return ModSounds.BELL_WARDEN_AMBIENT;
    }

    @Override
    protected SoundEvent hurtSound() {
        return ModSounds.BELL_WARDEN_HURT;
    }

    @Override
    protected SoundEvent roarSound() {
        return ModSounds.BELL_WARDEN_ROAR;
    }

    @Override
    protected SoundEvent deathSound() {
        return ModSounds.BELL_WARDEN_DEATH;
    }
}
