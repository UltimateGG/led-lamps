package me.ultimate;

import me.ultimate.block.ModBlockEntities;
import me.ultimate.block.ModBlocks;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LEDLamps implements ModInitializer {
	public static final String MOD_ID = "ledlamps";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.initialize();
		ModBlockEntities.initialize();

		LOGGER.info("LED Lamps loaded");
	}
}