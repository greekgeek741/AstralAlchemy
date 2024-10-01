package net.c4fey.astral_alchemy.datagen;

import net.c4fey.astral_alchemy.block.ModBlocks;
import net.c4fey.astral_alchemy.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // Elemental Essences
        itemModelGenerator.register(ModItems.ASTRAL_ESSENCE, Models.GENERATED);
        itemModelGenerator.register(ModItems.VERNAL_ESSENCE, Models.GENERATED);
        itemModelGenerator.register(ModItems.TORRENTIAL_ESSENCE, Models.GENERATED);
        itemModelGenerator.register(ModItems.FULMINAL_ESSENCE, Models.GENERATED);
        itemModelGenerator.register(ModItems.HIBERNAL_ESSENCE, Models.GENERATED);
        itemModelGenerator.register(ModItems.UMBRAL_ESSENCE, Models.GENERATED);
        itemModelGenerator.register(ModItems.INFERNAL_ESSENCE, Models.GENERATED);
        // Other Items
        itemModelGenerator.register(ModItems.AMETHYST_FLASK, Models.GENERATED);
    }
}
