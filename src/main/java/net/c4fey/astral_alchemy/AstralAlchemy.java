package net.c4fey.astral_alchemy;

import net.c4fey.astral_alchemy.block.ModBlocks;
import net.c4fey.astral_alchemy.block.entity.EssenceDistillerBlockEntity;
import net.c4fey.astral_alchemy.block.entity.ModBlockEntityTypes;
import net.c4fey.astral_alchemy.item.ModItemGroups;
import net.c4fey.astral_alchemy.item.ModItems;
import net.c4fey.astral_alchemy.screen.ModScreenHandlerTypes;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AstralAlchemy implements ModInitializer {
	public static final String MOD_ID = "astral_alchemy";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModBlockEntityTypes.registerModBlockEntityTypes();
		ModScreenHandlerTypes.registerModScreenHandlers();

		ItemStorage.SIDED.registerForBlockEntity(EssenceDistillerBlockEntity::getInventoryProvider, ModBlockEntityTypes.ESSENCE_DISTILLER_BLOCK_ENTITY);
	}
}