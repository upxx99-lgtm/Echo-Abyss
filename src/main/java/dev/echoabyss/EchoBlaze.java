package dev.echoabyss;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.Level;

/** A hostile fire spirit of the Echo dimension. It drops Echo Fragments. */
public class EchoBlaze extends Blaze {
    public EchoBlaze(EntityType<? extends Blaze> type, Level level) {
        super(type, level);
    }
}
