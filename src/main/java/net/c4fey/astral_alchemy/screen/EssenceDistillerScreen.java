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
        /*
        context.drawTexture(TEXTURE, this.x + 27, this.y + 37 + this.getScreenHandler().
                        getBlockEntity().getFireSizeReverse(),
                176, this.getScreenHandler().getBlockEntity().getFireSizeReverse(), 14,
                this.getScreenHandler().getBlockEntity().getFireSize()); // fire
        context.drawTexture(TEXTURE, this.x + 103, this.y + 17, 177, 14,
                this.getScreenHandler().getBlockEntity().getArrowSize(), 16);
        context.drawTexture(TEXTURE, this.x + 103, this.y + 53, 177, 14,
                this.getScreenHandler().getBlockEntity().getArrowSize(), 16);
         */
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTexture(TEXTURE, this.x + 27, this.y + 37 + this.getScreenHandler().
                        getBlockEntity().getFireSizeReverse(),
                176, this.getScreenHandler().getBlockEntity().getFireSizeReverse(), 14,
                this.getScreenHandler().getBlockEntity().getFireSize()); // fire
        context.drawTexture(TEXTURE, this.x + 103, this.y + 17, 177, 14,
                this.getScreenHandler().getBlockEntity().getArrowSize(), 16);
        context.drawTexture(TEXTURE, this.x + 103, this.y + 53, 177, 14,
                this.getScreenHandler().getBlockEntity().getArrowSize(), 16);

        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
