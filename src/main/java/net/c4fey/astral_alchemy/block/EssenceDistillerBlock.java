package net.c4fey.astral_alchemy.block;

import com.mojang.serialization.MapCodec;
import net.c4fey.astral_alchemy.block.entity.EssenceDistillerBlockEntity;
import net.c4fey.astral_alchemy.block.entity.ModBlockEntityTypes;
import net.c4fey.astral_alchemy.util.TickableBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EssenceDistillerBlock extends Block implements BlockEntityProvider {
    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 16);

    public EssenceDistillerBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof EssenceDistillerBlockEntity essenceDistillerBlockEntity && player != null) {
                player.openHandledScreen(essenceDistillerBlockEntity);
            }
        }
        return ActionResult.success(world.isClient);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntityTypes.ESSENCE_DISTILLER_BLOCK_ENTITY.instantiate(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return TickableBlockEntity.getTicker(world);
    }
}
