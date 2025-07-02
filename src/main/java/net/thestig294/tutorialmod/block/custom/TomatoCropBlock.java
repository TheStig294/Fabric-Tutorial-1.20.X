package net.thestig294.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.thestig294.tutorialmod.item.ModItems;

public class TomatoCropBlock extends CropBlock {
    public static final int MAX_AGE = 5;

//    *Non-static variables in registered classes (like blocks and items) don't work!*
//    Instead, you have to use "properties", *FOR BLOCKS ONLY*, which are essentially instanced attributes of blocks in the world.
//    (Attributes that aren't shared between all blocks, else you could just use a public static class data member as above)

//    You can either create your own properties for world blocks as below:
//    public static final IntProperty AGE = IntProperty.of("age", 0, 5);

//    OR you can use one of the pre-built properties from the Properties class:
    public static final IntProperty AGE = Properties.AGE_5;

//    Our Tomato crop has 6 stages of growth, which is why we picked an int property that ranges from 0 to 5!

    public TomatoCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModItems.TOMATO_SEEDS;
    }

    @Override
    protected IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

//    You then need to override the "appendProperties" function to actually add the property to the block, and start using it!
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
