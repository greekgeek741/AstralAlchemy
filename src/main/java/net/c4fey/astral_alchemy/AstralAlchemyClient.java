package net.c4fey.astral_alchemy;

import net.c4fey.astral_alchemy.screen.EssenceDistillerScreen;
import net.c4fey.astral_alchemy.screen.ModScreenHandlerTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class AstralAlchemyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        HandledScreens.register(ModScreenHandlerTypes.ESSENCE_DISTILLER, EssenceDistillerScreen::new);
    }
}
