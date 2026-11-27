package dev.echoabyss;

import java.util.function.Function;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public final class ModItems {
	// ---- tags (the files are in data/echo_abyss/tags) ----
	public static final TagKey<Block> INCORRECT_FOR_ECHO_TOOL = TagKey.create(Registries.BLOCK, EchoAbyss.id("incorrect_for_echo_tool"));
	public static final TagKey<Item> REPAIRS_ECHO_GEAR = TagKey.create(Registries.ITEM, EchoAbyss.id("repairs_echo_gear"));
	public static final TagKey<Item> REPAIRS_ECHO_KEY = TagKey.create(Registries.ITEM, EchoAbyss.id("repairs_echo_key"));

	// ---- material for the Echo tools (a bit stronger than netherite) ----
	public static final ToolMaterial ECHO_MATERIAL = new ToolMaterial(
		INCORRECT_FOR_ECHO_TOOL, // incorrect blocks for drops (empty tag: mines everything)
		3200, // durability
		11.0F, // speed
		6.0F, // attack damage bonus
		20, // enchantment value
		REPAIRS_ECHO_GEAR // repair items
	);

	// ---- wave items ----
	public static final Item TUNING_FORK = register("tuning_fork",
		p -> new WaveItem(p, 24.0, 0.0, 100, ModSounds.ECHO_FORK, 1.0F),
		new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));

	public static final Item ECHO_BELL = register("echo_bell",
		p -> new WaveItem(p, 12.0, 12.0, 40, ModSounds.ECHO_BELL, 1.0F),
		new Item.Properties().stacksTo(1).rarity(Rarity.RARE));

	// Teleporter: has durability, every trip INTO the abyss costs 1. Repair with Echo Shards in an anvil.
	public static final Item ECHO_KEY = register("echo_key", AbyssKeyItem::new,
		new Item.Properties().durability(12).rarity(Rarity.EPIC).repairable(REPAIRS_ECHO_KEY));

	// ---- materials ----
	public static final Item BELL_CRYSTAL = register("bell_crystal", Item::new, new Item.Properties());
	public static final Item ECHO_SHARD = register("echo_shard", Item::new, new Item.Properties());
	public static final Item ECHO_INGOT = register("echo_ingot", Item::new, new Item.Properties());
	public static final Item ECHO_UPGRADE_TEMPLATE = register("echo_upgrade_template", Item::new, new Item.Properties());

	// ---- ore block ----
	public static final Block ECHO_ORE = registerBlock("echo_ore", Block::new,
		BlockBehaviour.Properties.of().strength(4.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.ANCIENT_DEBRIS));

	public static final Block ECHO_BLOCK = registerBlock("echo_block", Block::new,
		BlockBehaviour.Properties.of().strength(50.0F, 1200.0F).requiresCorrectToolForDrops().sound(SoundType.NETHERITE_BLOCK));

	public static final Block ECHO_STONE = registerBlock("echo_stone", Block::new,
		BlockBehaviour.Properties.of().strength(3.0F, 9.0F).requiresCorrectToolForDrops().sound(SoundType.STONE));
	public static final Block DEEP_ECHO_STONE = registerBlock("deep_echo_stone", Block::new,
		BlockBehaviour.Properties.of().strength(4.0F, 12.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE));

	// building blocks for the dungeons (like purpur and end stone bricks in End cities)
	public static final Block ECHO_PURPUR = registerBlock("echo_purpur", Block::new,
		BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.STONE));
	public static final Block ECHO_BRICKS = registerBlock("echo_bricks", Block::new,
		BlockBehaviour.Properties.of().strength(3.0F, 9.0F).requiresCorrectToolForDrops().sound(SoundType.STONE));
	public static final Block ECHO_PILLAR = registerBlock("echo_pillar", RotatedPillarBlock::new,
		BlockBehaviour.Properties.of().strength(1.5F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.STONE));
	public static final Block ECHO_GLOW = registerBlock("echo_glow", Block::new,
		BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.GLASS).lightLevel(state -> 14));

	// ---- tools ----
	public static final Item ECHO_SWORD = register("echo_sword", Item::new, new Item.Properties().sword(ECHO_MATERIAL, 3.0F, -2.4F));
	public static final Item ECHO_PICKAXE = register("echo_pickaxe", Item::new, new Item.Properties().pickaxe(ECHO_MATERIAL, 1.0F, -2.8F));
	public static final Item ECHO_AXE = register("echo_axe", Item::new, new Item.Properties().axe(ECHO_MATERIAL, 5.0F, -3.0F));
	public static final Item ECHO_SHOVEL = register("echo_shovel", Item::new, new Item.Properties().shovel(ECHO_MATERIAL, 1.5F, -3.0F));
	public static final Item ECHO_HOE = register("echo_hoe", Item::new, new Item.Properties().hoe(ECHO_MATERIAL, -4.0F, 0.0F));
	public static final Item ECHO_SPEAR = register("echo_spear", Item::new,
		new Item.Properties().spear(ECHO_MATERIAL, 2.8F, 0.3F, 1.4F, 1.1F, 2.0F, 1.1F, 0.15F, 0.25F, 0.45F));

	// ---- spawn eggs for the mini-bosses ----
	public static final Item ECHO_WARDEN_SPAWN_EGG = register("echo_warden_spawn_egg", SpawnEggItem::new, new Item.Properties().spawnEgg(ModEntities.ECHO_WARDEN));
	public static final Item BELL_WARDEN_SPAWN_EGG = register("bell_warden_spawn_egg", SpawnEggItem::new, new Item.Properties().spawnEgg(ModEntities.BELL_WARDEN));

	public static final Item ECHO_BLAZE_SPAWN_EGG = register("echo_blaze_spawn_egg", SpawnEggItem::new, new Item.Properties().spawnEgg(ModEntities.ECHO_BLAZE));
	public static final Item ECHO_GOLEM_SPAWN_EGG = register("echo_golem_spawn_egg", SpawnEggItem::new, new Item.Properties().spawnEgg(ModEntities.ECHO_GOLEM));

	// dropped by the Echo Blaze; used to craft the dungeon compass
	public static final Item ECHO_FRAGMENT = register("echo_fragment", Item::new, new Item.Properties());
	public static final Item ECHO_COMPASS = register("echo_compass", DungeonCompassItem::new, new Item.Properties().stacksTo(1));

	// ---- armor ----
	public static final Item ECHO_HELMET = register("echo_helmet", Item::new,
		new Item.Properties().humanoidArmor(EchoArmorMaterial.INSTANCE, ArmorType.HELMET)
			.durability(ArmorType.HELMET.getDurability(EchoArmorMaterial.BASE_DURABILITY)));
	public static final Item ECHO_CHESTPLATE = register("echo_chestplate", Item::new,
		new Item.Properties().humanoidArmor(EchoArmorMaterial.INSTANCE, ArmorType.CHESTPLATE)
			.durability(ArmorType.CHESTPLATE.getDurability(EchoArmorMaterial.BASE_DURABILITY)));
	public static final Item ECHO_LEGGINGS = register("echo_leggings", Item::new,
		new Item.Properties().humanoidArmor(EchoArmorMaterial.INSTANCE, ArmorType.LEGGINGS)
			.durability(ArmorType.LEGGINGS.getDurability(EchoArmorMaterial.BASE_DURABILITY)));
	public static final Item ECHO_BOOTS = register("echo_boots", Item::new,
		new Item.Properties().humanoidArmor(EchoArmorMaterial.INSTANCE, ArmorType.BOOTS)
			.durability(ArmorType.BOOTS.getDurability(EchoArmorMaterial.BASE_DURABILITY)));

	public static final ResourceKey<CreativeModeTab> TAB_KEY =
		ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), EchoAbyss.id("main"));

	public static final CreativeModeTab TAB = FabricCreativeModeTab.builder()
		.icon(() -> new ItemStack(TUNING_FORK))
		.title(Component.translatable("creativeTab.echo_abyss"))
		.displayItems((params, output) -> {
			output.accept(ECHO_KEY);
			output.accept(TUNING_FORK);
			output.accept(ECHO_BELL);
			output.accept(BELL_CRYSTAL);
			output.accept(ECHO_ORE);
			output.accept(ECHO_SHARD);
			output.accept(ECHO_INGOT);
			output.accept(ECHO_BLOCK);
			output.accept(ECHO_STONE);
			output.accept(DEEP_ECHO_STONE);
			output.accept(ECHO_PURPUR);
			output.accept(ECHO_BRICKS);
			output.accept(ECHO_PILLAR);
			output.accept(ECHO_GLOW);
			output.accept(ECHO_UPGRADE_TEMPLATE);
			output.accept(ECHO_SWORD);
			output.accept(ECHO_PICKAXE);
			output.accept(ECHO_AXE);
			output.accept(ECHO_SHOVEL);
			output.accept(ECHO_HOE);
			output.accept(ECHO_SPEAR);
			output.accept(ECHO_HELMET);
			output.accept(ECHO_CHESTPLATE);
			output.accept(ECHO_LEGGINGS);
			output.accept(ECHO_BOOTS);
			output.accept(ECHO_WARDEN_SPAWN_EGG);
			output.accept(BELL_WARDEN_SPAWN_EGG);
			output.accept(ECHO_BLAZE_SPAWN_EGG);
			output.accept(ECHO_GOLEM_SPAWN_EGG);
			output.accept(ECHO_FRAGMENT);
			output.accept(ECHO_COMPASS);
		})
		.build();

	private ModItems() {
	}

	private static <T extends Item> T register(String name, Function<Item.Properties, T> factory, Item.Properties properties) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, EchoAbyss.id(name));
		T item = factory.apply(properties.setId(key));
		Registry.register(BuiltInRegistries.ITEM, key, item);
		return item;
	}

	private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
		ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, EchoAbyss.id(name));
		Block block = factory.apply(properties.setId(key));
		Registry.register(BuiltInRegistries.BLOCK, key, block);
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, EchoAbyss.id(name));
		Registry.register(BuiltInRegistries.ITEM, itemKey, new BlockItem(block, new Item.Properties().setId(itemKey)));
		return block;
	}

	public static void initialize() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_KEY, TAB);
	}
}
