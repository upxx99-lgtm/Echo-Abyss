package dev.echoabyss;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;

/** Right click: tells the distance and direction to the nearest dungeon. */
public class DungeonCompassItem extends Item {
    private static final TagKey<Structure> DUNGEONS = TagKey.create(Registries.STRUCTURE, EchoAbyss.id("echo_dungeon"));
    private final Map<UUID, Long> lastUse = new HashMap<>();

    public DungeonCompassItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player user, InteractionHand hand) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.PASS;
        }
        long now = serverLevel.getGameTime();
        Long last = this.lastUse.get(user.getUUID());
        if (last != null && now >= last && now - last < 20) {
            return InteractionResult.FAIL;
        }
        this.lastUse.put(user.getUUID(), now);

        BlockPos found = serverLevel.findNearestMapStructure(DUNGEONS, user.blockPosition(), 100, false);
        if (found == null) {
            user.displayClientMessage(Component.translatable("message.echo_abyss.compass.none"), true);
        } else {
            double dx = found.getX() - user.getX();
            double dz = found.getZ() - user.getZ();
            int distance = (int) Math.sqrt(dx * dx + dz * dz);
            int sector = Math.floorMod(Math.round((float) (Math.toDegrees(Math.atan2(dz, dx)) / 45.0)), 8);
            user.displayClientMessage(Component.translatable("message.echo_abyss.compass.found", distance, Component.translatable("compass.echo_abyss.dir." + sector)), true);
        }
        return InteractionResult.SUCCESS;
    }
}
