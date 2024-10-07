package net.c4fey.astral_alchemy.recipe;

import net.c4fey.astral_alchemy.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class DistillingMap {
    private Item input;
    public ItemStack returnedOutput;
    public ItemStack distilledElement;

    public static DistillingMap[] distillingMaps = {
            new DistillingMap(ModItems.STARDUST, new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(ModItems.ASTRAL_ESSENCE)),
            new DistillingMap(Items.GRASS_BLOCK, new ItemStack(Items.DIRT), new ItemStack(ModItems.VERNAL_ESSENCE)),
            new DistillingMap(Items.PRISMARINE, new ItemStack(Items.COBBLESTONE), new ItemStack(ModItems.TORRENTIAL_ESSENCE)),
            new DistillingMap(Items.REDSTONE, new ItemStack(ModItems.FERRIC_DUST), new ItemStack(ModItems.FULMINAL_ESSENCE)),
            new DistillingMap(Items.POWDER_SNOW_BUCKET, new ItemStack(Items.WATER_BUCKET), new ItemStack(ModItems.HIBERNAL_ESSENCE)),
            new DistillingMap(Items.BLACKSTONE, new ItemStack(Items.COBBLESTONE), new ItemStack(ModItems.UMBRAL_ESSENCE)),
            new DistillingMap(Items.BLAZE_ROD, new ItemStack(Items.BREEZE_ROD), new ItemStack(ModItems.INFERNAL_ESSENCE))
    };

    public DistillingMap(Item a, ItemStack b, ItemStack c) {
        this.input = a;
        this.returnedOutput = b;
        this.distilledElement = c;
    }

    public static DistillingMap getDistillingMapFromInput(Item item) {
        for (DistillingMap map : distillingMaps) {
            if (map.input == item) {
                return map;
            }
        }
        return null;
    }
}
