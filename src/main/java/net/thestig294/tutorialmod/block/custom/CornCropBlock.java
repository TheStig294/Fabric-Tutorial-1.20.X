package net.thestig294.tutorialmod.block.custom;

import net.minecraft.block.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.thestig294.tutorialmod.item.ModItems;

public class CornCropBlock extends CropBlock {
    public static final int FIRST_STAGE_MAX_AGE = 7;
//    You could modify the second stage max age so the top half of the block has to grow as well!
//    (Might require some Java-ing around though...)
    public static final int SECOND_STAGE_MAX_AGE = 1;
//    Controls the outline shape that appears around the crop when looking at a crop block
//    (Need to copy this over from CropBlock because it only has up to 7 stages, we have 8 in total!
    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
    };

    public static final IntProperty AGE = IntProperty.of("age", 0, 8);

    public CornCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[this.getAge(state)];
    }

//    Used for natural growth
//    Looks like world.setBlockState(pos, blockState, flags) is how you spawn in a block into the world!
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBaseLightLevel(pos, 0) >= 9){
            int currentAge = this.getAge(state);
            if (currentAge < this.getMaxAge()){
                float f = getAvailableMoisture(this, world, pos);
                if (random.nextInt((int)(25.0f / f) + 1) == 0){
                    if (currentAge == FIRST_STAGE_MAX_AGE) {
                        if (world.getBlockState(pos.up(1)).isOf(Blocks.AIR)){
//                            Spawn in the crop block on top
                            world.setBlockState(pos.up(1), this.withAge(currentAge + 1), Block.NOTIFY_LISTENERS);
                        }
                    } else {
//                        Set the age of the current crop block
                        world.setBlockState(pos, this.withAge(currentAge + 1), Block.NOTIFY_LISTENERS);
                    }
                }
            }
        }
    }

//    Used for bone-meal growth
    @Override
    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        int nextAge = this.getAge(state) + this.getGrowthAmount(world);
        int maxAge = this.getMaxAge();

        if (nextAge > maxAge){
            nextAge = maxAge;
        }

//        Spawning a block on top of the old crop block, with the age property set to the most mature!
//        (because of how the math checks out from this.getGrowthAmount())
        if (this.getAge(state) == FIRST_STAGE_MAX_AGE && world.getBlockState(pos.up(1)).isOf(Blocks.AIR)){
            world.setBlockState(pos.up(1), this.withAge(nextAge), Block.NOTIFY_LISTENERS);
        } else {
//            The -1 ensures the bottom block age is only ever set to a max of 7
            world.setBlockState(pos, this.withAge(nextAge - 1), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return super.canPlaceAt(state, world, pos) ||
//                2-crop block exclusive: letting the top block survive if the bottom block is also a corn crop block,
//                that is fully mature!
                (world.getBlockState(pos.down(1)).isOf(this) && world.getBlockState(pos.down(1)).get(AGE) == 7);
    }

    @Override
    public int getMaxAge() {
        return FIRST_STAGE_MAX_AGE + SECOND_STAGE_MAX_AGE;
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.CORN_SEEDS;
    }

    @Override
    protected IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
