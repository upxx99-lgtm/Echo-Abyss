package dev.echoabyss;

import java.util.Set;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;

/** Puts the Echo Key and the upgrade template into chests of vanilla structures. */
public final class LootInjection {
	// about 1 chest in 8 has a key
	private static final Set<String> KEY_CHESTS = Set.of(
		"minecraft:chests/simple_dungeon",
		"minecraft:chests/abandoned_mineshaft",
		"minecraft:chests/desert_pyramid",
		"minecraft:chests/jungle_temple",
		"minecraft:chests/stronghold_corridor",
		"minecraft:chests/woodland_mansion",
		"minecraft:chests/buried_treasure",
		"minecraft:chests/ancient_city",
		"minecraft:chests/ruined_portal",
		"minecraft:chests/end_city_treasure"
	);

	// the template is found only inside the Echo dimension (dungeon chests)
	private static final Set<String> TEMPLATE_CHESTS = Set.of();

	private LootInjection() {
	}

	public static void initialize() {
		LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
			String id = key.identifier().toString();
			if (KEY_CHESTS.contains(id)) {
				tableBuilder.withPool(chance(ModItems.ECHO_KEY, 8));
			}
			if (TEMPLATE_CHESTS.contains(id)) {
				tableBuilder.withPool(chance(ModItems.ECHO_UPGRADE_TEMPLATE, 6));
			}
		});
	}

	private static LootPool.Builder chance(Item item, int oneIn) {
		return LootPool.lootPool()
			.add(LootItem.lootTableItem(item).setWeight(1))
			.add(EmptyLootItem.emptyItem().setWeight(oneIn - 1));
	}
}
