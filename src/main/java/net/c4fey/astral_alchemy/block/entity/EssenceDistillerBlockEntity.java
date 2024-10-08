package net.c4fey.astral_alchemy.block.entity;

import net.c4fey.astral_alchemy.AstralAlchemy;
import net.c4fey.astral_alchemy.item.ModItems;
import net.c4fey.astral_alchemy.network.BlockPosPayload;
import net.c4fey.astral_alchemy.recipe.DistillingMap;
import net.c4fey.astral_alchemy.screen.EssenceDistillerScreenHandler;
import net.c4fey.astral_alchemy.util.TickableBlockEntity;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class EssenceDistillerBlockEntity extends BlockEntity implements TickableBlockEntity, ExtendedScreenHandlerFactory<BlockPosPayload> {
    public static final Text TITLE = Text.translatable("block.astral_alchemy.essence_distiller");
    private final SimpleInventory inventory = new SimpleInventory(6) {
        @Override
        public void markDirty() {
            super.markDirty();
            update();
        }
    };
    private final InventoryStorage inventoryStorage = InventoryStorage.of(inventory, null);
    private int progress = 0;

    public EssenceDistillerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.ESSENCE_DISTILLER_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void tick() {
        if (this.world == null || this.world.isClient) return;

        if (this.progress == 0) {
            if (this.canDistill() && this.inventory.getStack(0).getItem() == Items.SUGAR) {
                this.progress = 1;
                this.decreaseOrRemoveStack(0);
            }
        } else {
            if (!this.canDistill()) {
                this.progress--;
                return;
            }
            this.progress++;
            if (this.progress == 80) {
                this.inventory.setStack(1, new ItemStack(Items.BUCKET));
                DistillingMap distillingMap = DistillingMap.getDistillingMapFromInput(
                        this.inventory.getStack(2).getItem());
                if (this.inventory.getStack(4) == ItemStack.EMPTY) {
                    assert distillingMap != null;
                    this.inventory.setStack(4, distillingMap.returnedOutput);
                } else this.inventory.heldStacks.set(4,
                        this.inventory.getStack(4).copyWithCount(
                                this.inventory.getStack(4).getCount() + 1));
                if (this.inventory.getStack(5) == ItemStack.EMPTY) {
                    assert distillingMap != null;
                    this.inventory.setStack(5, distillingMap.distilledElement);
                } else this.inventory.heldStacks.set(5,
                        this.inventory.getStack(5).copyWithCount(
                                this.inventory.getStack(5).getCount() + 1));
                this.decreaseOrRemoveStack(2);
                this.decreaseOrRemoveStack(3);
            }
        }
    }

    public void decreaseOrRemoveStack(int stack) {
        if (this.inventory.heldStacks.get(stack).getCount() == 1)
            this.inventory.heldStacks.set(stack, ItemStack.EMPTY);
        else this.inventory.heldStacks.set(stack,
                this.inventory.heldStacks.get(stack).copyWithCount(
                        this.inventory.heldStacks.get(stack).getCount() - 1));
    }

    private boolean canDistill() {
        DistillingMap distillingMap = DistillingMap.getDistillingMapFromInput(
                this.inventory.getStack(2).getItem());
        if (distillingMap == null) {
            return false;
        }

        boolean water = this.inventory.getStack(1).getItem() == Items.WATER_BUCKET;
        boolean input = DistillingMap.validDistillingInput(this.inventory.getStack(2).getItem());
        boolean flask = this.inventory.getStack(3).getItem() == ModItems.AMETHYST_FLASK;
        boolean output = this.inventory.getStack(4).getItem() == distillingMap.returnedOutput.getItem() ||
                this.inventory.getStack(4) == ItemStack.EMPTY;
        boolean element = this.inventory.getStack(5).getItem() == distillingMap.distilledElement.getItem() ||
                this.inventory.getStack(5) == ItemStack.EMPTY;
        return water && input && flask && output && element;
    }

    public int getProgress() {
        return this.progress;
    }

    public int getArrowSize() {
        return MathHelper.ceil(MathHelper.clampedLerp(0, 22, this.progress));
    }

    public int getFireSize() {
        return MathHelper.ceil(MathHelper.clampedLerp(0, 13, this.progress));
    }

    public int getFireSizeReverse() {
        return 13 - getFireSize();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.writeNbt(nbt, this.inventory.getHeldStacks(), registryLookup);
        var modIdData = new NbtCompound();
        modIdData.putInt("progress", this.progress);
        nbt.put(AstralAlchemy.MOD_ID, modIdData);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        if (nbt.contains(AstralAlchemy.MOD_ID, NbtElement.COMPOUND_TYPE)) {
            var modIdData = nbt.getCompound(AstralAlchemy.MOD_ID);
            this.progress = modIdData.contains("progress", NbtElement.INT_TYPE) ? modIdData.getInt("progress") : 0;
        }
        Inventories.readNbt(nbt, this.inventory.getHeldStacks(), registryLookup);
    }

    @Override
    public BlockPosPayload getScreenOpeningData(ServerPlayerEntity player) {
        return new BlockPosPayload(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return TITLE;
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new EssenceDistillerScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        var nbt = super.toInitialChunkDataNbt(registryLookup);
        writeNbt(nbt, registryLookup);
        return nbt;
    }

    private void update() {
        markDirty();
        if (world != null) world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_ALL);
    }

    public InventoryStorage getInventoryProvider(Direction direction) {
        return inventoryStorage;
    }

    public SimpleInventory getInventory() {
        return this.inventory;
    }
}
