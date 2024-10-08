package net.c4fey.astral_alchemy.block.entity;

import net.c4fey.astral_alchemy.AstralAlchemy;
import net.c4fey.astral_alchemy.block.ModBlocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntityTypes {
    public static final BlockEntityType<EssenceDistillerBlockEntity> ESSENCE_DISTILLER_BLOCK_ENTITY = register("essence_distiller_block_entity",
            BlockEntityType.Builder.create(EssenceDistillerBlockEntity::new, ModBlocks.ESSENCE_DISTILLER)
                    .build());

    public static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, AstralAlchemy.MOD_ID + ":" + name, type);
    }

    public static void registerModBlockEntityTypes() {
        AstralAlchemy.LOGGER.info("Registering Mod Block Entity Types for " + AstralAlchemy.MOD_ID);
    }
}