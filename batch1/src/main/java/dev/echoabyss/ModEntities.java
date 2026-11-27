package dev.echoabyss;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.warden.Warden;

public final class ModEntities {
	public static final ResourceKey<EntityType<?>> ECHO_WARDEN_KEY = ResourceKey.create(Registries.ENTITY_TYPE, EchoAbyss.id("echo_warden"));
	public static final ResourceKey<EntityType<?>> BELL_WARDEN_KEY = ResourceKey.create(Registries.ENTITY_TYPE, EchoAbyss.id("bell_warden"));

	// Warden has 500 health and 30 damage. These are weaker, but still bosses.
	public static final EntityType<EchoWarden> ECHO_WARDEN = EntityType.Builder.of(EchoWarden::new, MobCategory.MONSTER)
		.sized(0.9F, 2.9F)
		.clientTrackingRange(16)
		.build(ECHO_WARDEN_KEY);

	public static final EntityType<BellWarden> BELL_WARDEN = EntityType.Builder.of(BellWarden::new, MobCategory.MONSTER)
		.sized(0.9F, 2.9F)
		.clientTrackingRange(16)
		.build(BELL_WARDEN_KEY);

	static {
		Registry.register(BuiltInRegistries.ENTITY_TYPE, ECHO_WARDEN_KEY, ECHO_WARDEN);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, BELL_WARDEN_KEY, BELL_WARDEN);
		FabricDefaultAttributeRegistry.register(ECHO_WARDEN, Warden.createAttributes()
			.add(Attributes.MAX_HEALTH, 150.0)
			.add(Attributes.ATTACK_DAMAGE, 18.0));
		FabricDefaultAttributeRegistry.register(BELL_WARDEN, Warden.createAttributes()
			.add(Attributes.MAX_HEALTH, 100.0)
			.add(Attributes.ATTACK_DAMAGE, 14.0));
	}

	private ModEntities() {
	}

	/** Calling this loads the class, and the static block above registers everything. */
	public static void initialize() {
	}
}
