package net.c4fey.astral_alchemy.screen;

import net.c4fey.astral_alchemy.AstralAlchemy;
import net.c4fey.astral_alchemy.network.BlockPosPayload;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlerTypes {

    public static final ScreenHandlerType<EssenceDistillerScreenHandler> ESSENCE_DISTILLER =
            register("essence_distiller", EssenceDistillerScreenHandler::new, BlockPosPayload.PACKET_CODEC);

    public static <T extends ScreenHandler, D extends CustomPayload> ExtendedScreenHandlerType<T, D>
    register(
            String name,
            ExtendedScreenHandlerType.ExtendedFactory<T, D> factory,
            PacketCodec<? super RegistryByteBuf, D> packetCodec) {
        return Registry.register(Registries.SCREEN_HANDLER, Identifier.of(AstralAlchemy.MOD_ID, name), new ExtendedScreenHandlerType<>(factory, packetCodec));
    }

    public static void registerModScreenHandlers() {

    }
}
