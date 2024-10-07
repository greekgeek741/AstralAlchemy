package net.c4fey.astral_alchemy.screen;

import net.c4fey.astral_alchemy.AstralAlchemy;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class EssenceDistillerScreen extends HandledScreen<EssenceDistillerScreenHandler> {

    private static final Identifier TEXTURE = Identifier.of(AstralAlchemy.MOD_ID,
            "textures/gui/container/essence_distiller.png");

    public EssenceDistillerScreen(EssenceDistillerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
