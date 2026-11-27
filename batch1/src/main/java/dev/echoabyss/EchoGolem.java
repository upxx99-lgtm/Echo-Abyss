package dev.echoabyss;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.level.Level;

/** A neutral guardian: it only fights when attacked. */
public class EchoGolem extends IronGolem {
    public EchoGolem(EntityType<? extends IronGolem> type, Level level) {
        super(type, level);
    }
}
