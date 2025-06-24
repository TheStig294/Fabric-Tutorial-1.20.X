package net.thestig294.tutorialmod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.thestig294.tutorialmod.TutorialMod;

public class ModBlocks {
    public static final Block RUBY_BLOCK = registerBlock("ruby_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.AMETHYST_BLOCK)));

    public static final Block RAW_RUBY_BLOCK = registerBlock("raw_ruby_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.AMETHYST_BLOCK)));

    //    To copy an existing block, use: "FabricBlockSettings.copyOf(Blocks.BLOCK_NAME)"

//    To instead create your own block, use: "FabricBlockSettings.create()" instead!
//    new Block(FabricBlockSettings.create().burnable().collidable(true).hardness(5.0f)...));
//    Look at the net.minecraft.block.Blocks file for examples!

//    If you want to make a very similar block, but override a couple of properties,
//    you can just call the builder function again!
//    E.g. new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(...));


//    Helper functions to make creating a block and its inventory item easier
    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(TutorialMod.MOD_ID, name), block);
    }

//    Block item you can throw on the ground/have in your inventory
    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registries.ITEM, new Identifier(TutorialMod.MOD_ID, name),
        new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks(){
        TutorialMod.LOGGER.info("Registering ModBlocks for " + TutorialMod.MOD_ID);
    }
}
