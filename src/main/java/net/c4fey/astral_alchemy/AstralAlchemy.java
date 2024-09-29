package net.c4fey.astral_alchemy;

import net.c4fey.astral_alchemy.block.ModBlocks;
import net.c4fey.astral_alchemy.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AstralAlchemy implements ModInitializer {
	public static final String MOD_ID = "astral_alchemy";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
	}
}