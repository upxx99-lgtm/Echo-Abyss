package dev.echoabyss;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoAbyss implements ModInitializer {
	public static final String MOD_ID = "echo_abyss";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		ModSounds.initialize();
		ModParticles.initialize();
		ModEntities.initialize();
		ModItems.initialize();
		SoundWaves.initialize();
		LootInjection.initialize();
		LOGGER.info("Echo Abyss loaded (stage 1: sound waves)");
	}
}
