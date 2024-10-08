package net.c4fey.astral_alchemy.screen;

import net.c4fey.astral_alchemy.block.ModBlocks;
import net.c4fey.astral_alchemy.block.entity.EssenceDistillerBlockEntity;
import net.c4fey.astral_alchemy.network.BlockPosPayload;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

public class EssenceDistillerScreenHandler extends ScreenHandler {

    private final EssenceDistillerBlockEntity blockEntity;
    private final ScreenHandlerContext context;

    // Client constructor
    public EssenceDistillerScreenHandler(int syncId, PlayerInventory playerInventory, BlockPosPayload payload) {
        this(syncId, playerInventory, (EssenceDistillerBlockEntity) playerInventory.player.getWorld().getBlockEntity(payload.pos()));
    }

    // Main constructor - called from server and by client constructor
    public EssenceDistillerScreenHandler(int syncId, PlayerInventory playerInventory, EssenceDistillerBlockEntity blockEntity) {
        super(ModScreenHandlerTypes.ESSENCE_DISTILLER, syncId);

        this.blockEntity = blockEntity;
        this.context = ScreenHandlerContext.create(this.blockEntity.getWorld(), this.blockEntity.getPos());

        SimpleInventory inventory = this.blockEntity.getInventory();
        inventory.onOpen(playerInventory.player);
        checkSize(inventory, 6);

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        addBlockInventory(inventory);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInventory, 9 + (column + (row * 9)), 8 + (column * 18), 84 + (row * 18)));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInventory, column, 8 + (column * 18), 142));
        }
    }

    private void addBlockInventory(SimpleInventory inventory) {
        addSlot(new Slot(inventory, 0, 26, 53));
        addSlot(new Slot(inventory, 1, 26, 17));
        addSlot(new Slot(inventory, 2, 80, 17));
        addSlot(new Slot(inventory, 3, 80, 53));
        addSlot(new Slot(inventory, 4, 134, 17));
        addSlot(new Slot(inventory, 5, 134, 53));
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.blockEntity.getInventory().onClose(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = getSlot(slotIndex);
        if (slot != null && slot.hasStack()) {
            ItemStack inSlot = slot.getStack();
            newStack = inSlot.copy();
            if (slotIndex < 6) {
                if (!insertItem(inSlot, 6, this.slots.size(), true)) return ItemStack.EMPTY;
            } else if (slotIndex > 6) {
                if (!insertItem(inSlot, 0, 6, false)) return ItemStack.EMPTY;
            }
            if (inSlot.isEmpty())
                slot.setStack(ItemStack.EMPTY);
            else slot.markDirty();
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModBlocks.ESSENCE_DISTILLER);
    }

    public EssenceDistillerBlockEntity getBlockEntity() {
        return this.blockEntity;
    }
}
