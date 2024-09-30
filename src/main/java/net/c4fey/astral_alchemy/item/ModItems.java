package net.c4fey.astral_alchemy.item;

import net.c4fey.astral_alchemy.AstralAlchemy;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    // Elemental Essences
    public static final Item ASTRAL_ESSENCE = registerItem("astral_essence", new Item(new Item.Settings()));
    public static final Item VERNAL_ESSENCE = registerItem("vernal_essence", new Item(new Item.Settings()));
    public static final Item TORRENTIAL_ESSENCE = registerItem("torrential_essence", new Item(new Item.Settings()));
    public static final Item FULMINAL_ESSENCE = registerItem("fulminal_essence", new Item(new Item.Settings()));
    public static final Item HIBERNAL_ESSENCE = registerItem("hibernal_essence", new Item(new Item.Settings()));
    public static final Item UMBRAL_ESSENCE = registerItem("umbral_essence", new Item(new Item.Settings()));
    public static final Item INFERNAL_ESSENCE = registerItem("infernal_essence", new Item(new Item.Settings()));

    // Other Items
    public static final Item AMETHYST_FLASK = registerItem("amethyst_flask", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(AstralAlchemy.MOD_ID, name), item);
    }

    public static void registerModItems() {
        AstralAlchemy.LOGGER.info("Registering Mod Items for " + AstralAlchemy.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(ASTRAL_ESSENCE);
            entries.add(VERNAL_ESSENCE);
            entries.add(TORRENTIAL_ESSENCE);
            entries.add(FULMINAL_ESSENCE);
            entries.add(HIBERNAL_ESSENCE);
            entries.add(UMBRAL_ESSENCE);
            entries.add(INFERNAL_ESSENCE);
        });
    }
}
