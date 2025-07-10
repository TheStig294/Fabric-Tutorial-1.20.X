package net.thestig294.tutorialmod.block.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.thestig294.tutorialmod.block.entity.GemPolishingStationBlockEntity;
import net.thestig294.tutorialmod.block.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class GemPolishingStationBlock extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape SHAPE = GemPolishingStationBlock.createCuboidShape(0,0,0,16,12,16);

    public GemPolishingStationBlock(Settings settings) {
        super(settings);
    }

//    Depreciated Minecraft methods aren't an issue, since mods need a re-write between Minecraft versions anyway...
//    (depreciated Fabric or Mixin methods might break in a future Fabric update however...)
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

//    If you don't do this, your block will be invisible...
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GemPolishingStationBlockEntity(pos, state);
    }

//    The implementation of onStateReplaced is basically going to be the same every time...
//    Basically used to drop the contents of an inventory, e.g. our machine block!
    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof GemPolishingStationBlockEntity) {
                ItemScatterer.spawn(world, pos, (GemPolishingStationBlockEntity) blockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

//    Opens the custom inventory on right-click
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = (GemPolishingStationBlockEntity) world.getBlockEntity(pos);

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.GEM_POLISHING_STATION_BLOCK_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}
