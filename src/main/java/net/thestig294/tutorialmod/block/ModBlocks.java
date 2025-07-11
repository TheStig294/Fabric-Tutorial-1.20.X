package net.thestig294.tutorialmod.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.thestig294.tutorialmod.TutorialMod;
import net.thestig294.tutorialmod.block.custom.CornCropBlock;
import net.thestig294.tutorialmod.block.custom.GemPolishingStationBlock;
import net.thestig294.tutorialmod.block.custom.SoundBlock;
import net.thestig294.tutorialmod.block.custom.TomatoCropBlock;
import net.thestig294.tutorialmod.sound.ModSounds;

public class ModBlocks {
    //    To copy an existing block, use: "FabricBlockSettings.copyOf(Blocks.BLOCK_NAME)"

//    To instead create your own block, use: "FabricBlockSettings.create()" instead!
//    new Block(FabricBlockSettings.create().burnable().collidable(true).hardness(5.0f)...));
//    Look at the net.minecraft.block.Blocks file for examples!

//    If you want to make a very similar block, but override a couple of properties,
//    you can just call the builder function again!
//    E.g. new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(...));

    public static final Block RUBY_BLOCK = registerBlock("ruby_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.AMETHYST_BLOCK)));
    public static final Block RAW_RUBY_BLOCK = registerBlock("raw_ruby_block",
            new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(BlockSoundGroup.AMETHYST_BLOCK)));

    public static final Block RUBY_ORE = registerBlock("ruby_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(2f), UniformIntProvider.create(2,5)));
    public static final Block DEEPSLATE_RUBY_ORE = registerBlock("deepslate_ruby_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE).strength(4f), UniformIntProvider.create(2,5)));
    public static final Block NETHERRACK_RUBY_ORE = registerBlock("netherrack_ruby_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.NETHERRACK).strength(1.5f), UniformIntProvider.create(2,5)));
    public static final Block END_STONE_RUBY_ORE = registerBlock("end_stone_ruby_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.END_STONE).strength(3f), UniformIntProvider.create(4,7)));

    public static final Block SOUND_BLOCK = registerBlock("sound_block",
            new SoundBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(ModSounds.SOUND_BLOCK_SOUNDS)));


//    For some reason, these blocks aren't mineable... (Except for the Ruby Wall). Need to investigate...
    public static final Block RUBY_STAIRS = registerBlock("ruby_stairs",
            new StairsBlock(ModBlocks.RUBY_BLOCK.getDefaultState(), FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));
    public static final Block RUBY_SLAB = registerBlock("ruby_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));

//    The "wooden" parameter in ButtonBlock() is just for if the button can be pressed by an arrow... that's it!
//    The "BlockSetType" class just determines the sounds a multi-material block makes,
//    and if it is an "openable" block like a door, whether it can be opened by hand!
    public static final Block RUBY_BUTTON = registerBlock("ruby_button",
            new ButtonBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).collidable(false), BlockSetType.IRON, 10, true));
    public static final Block RUBY_PRESSURE_PLATE = registerBlock("ruby_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.copyOf(Blocks.IRON_BLOCK), BlockSetType.IRON));

    public static final Block RUBY_FENCE = registerBlock("ruby_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));
//    A FenceGateBlock is forced to have some kind of "WoodType" passed to the contstructor,
//    even if it's not made out of wood! This is just selecting the sound you want the fence gate to play!
    public static final Block RUBY_FENCE_GATE = registerBlock("ruby_fence_gate",
            new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK), WoodType.ACACIA));
    public static final Block RUBY_WALL = registerBlock("ruby_wall",
            new WallBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));

//    Important to add the ".nonOpaque()" call to ensure doors and trapdoors have a see-through texture!
    public static final Block RUBY_DOOR = registerBlock("ruby_door",
            new DoorBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque(), BlockSetType.IRON));
    public static final Block RUBY_TRAPDOOR = registerBlock("ruby_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque(), BlockSetType.IRON));

    public static final Block TOMATO_CROP = Registry.register(Registries.BLOCK, new Identifier(TutorialMod.MOD_ID, "tomato_crop"),
            new TomatoCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT)));

    public static final Block CORN_CROP = Registry.register(Registries.BLOCK, new Identifier(TutorialMod.MOD_ID, "corn_crop"),
            new CornCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT)));

    public static final Block DAHLIA = registerBlock("dahlia",
            new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 10, FabricBlockSettings.copyOf(Blocks.ALLIUM).nonOpaque().noCollision()));
    public static final Block POTTED_DAHLIA = Registry.register(Registries.BLOCK, new Identifier(TutorialMod.MOD_ID, "potted_dahlia"),
            new FlowerPotBlock(DAHLIA, FabricBlockSettings.copyOf(Blocks.POTTED_ALLIUM).nonOpaque()));

    public static final Block GEM_POLISHING_STATION = registerBlock("gem_polishing_station",
            new GemPolishingStationBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));


//    Helper functions to make creating a block and its inventory item easier
    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(TutorialMod.MOD_ID, name), block);
    }

//    Block item you can throw on the ground/have in your inventory
    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, new Identifier(TutorialMod.MOD_ID, name),
        new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks(){
        TutorialMod.LOGGER.info("Registering ModBlocks for " + TutorialMod.MOD_ID);
    }
}
