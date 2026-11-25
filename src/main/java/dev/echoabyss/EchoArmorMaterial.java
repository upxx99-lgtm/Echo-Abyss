package dev.echoabyss;

import java.util.Map;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

/** Echo armor: a bit better than netherite. The look is in assets/echo_abyss/equipment/echo.json. */
public final class EchoArmorMaterial {
	public static final int BASE_DURABILITY = 42;

	public static final ResourceKey<EquipmentAsset> KEY = ResourceKey.create(EquipmentAssets.ROOT_ID, EchoAbyss.id("echo"));
	public static final TagKey<Item> REPAIRS = TagKey.create(Registries.ITEM, EchoAbyss.id("repairs_echo_gear"));

	public static final ArmorMaterial INSTANCE = new ArmorMaterial(
		BASE_DURABILITY,
		Map.of(
			ArmorType.HELMET, 4,
			ArmorType.CHESTPLATE, 9,
			ArmorType.LEGGINGS, 7,
			ArmorType.BOOTS, 4
		),
		20,
		SoundEvents.ARMOR_EQUIP_NETHERITE,
		4.0F,
		0.15F,
		REPAIRS,
		KEY
	);

	private EchoArmorMaterial() {
	}
}
