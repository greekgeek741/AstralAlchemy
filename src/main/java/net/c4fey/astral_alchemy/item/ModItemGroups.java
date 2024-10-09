package net.c4fey.astral_alchemy.item;

import net.c4fey.astral_alchemy.AstralAlchemy;
import net.c4fey.astral_alchemy.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup ASTRAL_ALCHEMY_MATERIALS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(AstralAlchemy.MOD_ID, "astral_alchemy_materials"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.ASTRAL_ESSENCE))
                    .displayName(Text.translatable("itemgroup.astral_alchemy.astral_alchemy_materials"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.AMETHYST_FLASK);
                        entries.add(ModItems.ASTRAL_ESSENCE);
                        entries.add(ModItems.VERNAL_ESSENCE);
                        entries.add(ModItems.TORRENTIAL_ESSENCE);
                        entries.add(ModItems.FULMINAL_ESSENCE);
                        entries.add(ModItems.HIBERNAL_ESSENCE);
                        entries.add(ModItems.UMBRAL_ESSENCE);
                        entries.add(ModItems.INFERNAL_ESSENCE);
                        entries.add(ModItems.FERRIC_DUST);
                        entries.add(ModItems.STARDUST);
                        entries.add(ModItems.ALCHEMISTS_TOME);
                    }).build());
    public static final ItemGroup ASTRAL_ALCHEMY_BLOCKS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(AstralAlchemy.MOD_ID, "astral_alchemy_blocks"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModBlocks.ESSENCE_DISTILLER))
                    .displayName(Text.translatable("itemgroup.astral_alchemy.astral_alchemy_blocks"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.ESSENCE_DISTILLER);
                    }).build());

    public static void registerItemGroups() {
        AstralAlchemy.LOGGER.info("Registering Item Groups for " + AstralAlchemy.MOD_ID);
    }
}
