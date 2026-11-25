package dev.echoabyss;

import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/** A weaker Warden that counts as a boss: it has a boss bar and its own sounds. Stats are set in ModEntities. */
public abstract class MiniWarden extends Warden {
    private final ServerBossEvent bossEvent;
    private float lastHealth = -1.0F;
    private boolean wasAggressive;
    private int ambientTimer = 80;

    public MiniWarden(EntityType<? extends Warden> type, Level level, BossEvent.BossBarColor color) {
        super(type, level);
        this.bossEvent = new ServerBossEvent(Mth.createInsecureUUID(this.random), this.getDisplayName(), color, BossEvent.BossBarOverlay.PROGRESS);
    }

    protected abstract SoundEvent ambientSound();

    protected abstract SoundEvent hurtSound();

    protected abstract SoundEvent roarSound();

    protected abstract SoundEvent deathSound();

    private void playMobSound(SoundEvent sound, float volume) {
        float pitch = 0.9F + this.random.nextFloat() * 0.2F;
        this.level().playSound((Player) null, this.getX(), this.getY(), this.getZ(), sound, SoundSource.HOSTILE, volume, pitch);
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        super.customServerAiStep(level);
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());

        float health = this.getHealth();
        if (this.lastHealth < 0.0F) {
            this.lastHealth = health;
        }
        if (health < this.lastHealth - 0.01F) {
            this.playMobSound(this.hurtSound(), 0.9F);
        }
        this.lastHealth = health;

        boolean aggressive = this.isAggressive();
        if (aggressive && !this.wasAggressive) {
            this.playMobSound(this.roarSound(), 1.3F);
        }
        this.wasAggressive = aggressive;

        this.ambientTimer--;
        if (this.ambientTimer <= 0) {
            this.ambientTimer = 100 + this.random.nextInt(120);
            if (!aggressive) {
                this.playMobSound(this.ambientSound(), 1.0F);
            }
        }
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        this.playMobSound(this.deathSound(), 1.4F);
    }
}
