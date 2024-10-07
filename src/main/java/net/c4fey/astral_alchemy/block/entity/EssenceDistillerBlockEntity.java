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
import org.jetbrains.annotations.Nullable;

public class EssenceDistillerBlockEntity extends BlockEntity implements TickableBlockEntity, ExtendedScreenHandlerFactory<BlockPosPayload> {
    public static final Text TITLE = Text.translatable("block.astral_alchemy.essence_distiller");
    private final SimpleInventory inventory = new SimpleInventory(4) {
        @Override
        public void markDirty() {
            super.markDirty();
            update();
        }
    };
    private final InventoryStorage inventoryStorage = InventoryStorage.of(inventory, null);
    private int progress;

    public EssenceDistillerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.ESSENCE_DISTILLER_BLOCK_ENTITY, pos, state);
    }

    @Override
    public void tick() {
        if (this.world == null || this.world.isClient) return;

        if (this.getInventory().heldStacks.get(0).getItem() == Items.SUGAR &&
                this.getInventory().heldStacks.get(1).getItem() == Items.WATER_BUCKET &&
                this.getInventory().heldStacks.get(3).getItem() == ModItems.AMETHYST_FLASK) {
            DistillingMap distillingMap = DistillingMap.getDistillingMapFromInput(
                    this.getInventory().heldStacks.get(2).getItem());
            if (distillingMap != null) {
                this.inventory.setStack(0, ItemStack.EMPTY);
                this.inventory.setStack(1, new ItemStack(Items.BUCKET));
                this.inventory.setStack(2, distillingMap.returnedOutput.copy());
                this.inventory.setStack(3, distillingMap.distilledElement.copy());
                this.markDirty();
            }
        }
    }

    public int getProgress() {
        return this.progress;
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
