package net.thestig294.tutorialmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.thestig294.tutorialmod.block.ModBlocks;
import net.thestig294.tutorialmod.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
//        See the BlockStateModelGenerator class' register() function for how to add blocks with different textures
//        and shapes, e.g. a Jukebox, wood log, or a lantern
//        (You likely will need to extend or copy the properties of the vanilla class, e.g. Blocks.LANTERN)

//        The register() function is able to pull the appropriate textures, because you followed the naming convention
//        (What you defined as the classname of the block in the Identifier() object, has to match the name of the
//        texture file itself, and has to be in a .png format)

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RUBY_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RAW_RUBY_BLOCK);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RUBY_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_RUBY_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NETHERRACK_RUBY_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.END_STONE_RUBY_ORE);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SOUND_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
//        There are 10 different templates for creating an item model, the basic one is "Models.GENERATED"
//        You can find all item and block model texture templates in the "Models" class from base Minecraft!
//        GENERATED, HANDHELD, HANDHELD_ROD, GENERATED_TWO_LAYERS, GENERATED_THREE_LAYERS,
//        TEMPLATE_MUSIC_DISC, TEMPLATE_SHULKER_BOX, TEMPLATE_BED, TEMPLATE_BANNER and TEMPLATE_SKULL
//        See the Models class for example usages!

//        itemModelGenerator.register(Item, ModelTextureTemplate)
        itemModelGenerator.register(ModItems.RUBY, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_RUBY, Models.GENERATED);

        itemModelGenerator.register(ModItems.COAL_BRIQUETTE, Models.GENERATED);
        itemModelGenerator.register(ModItems.TOMATO, Models.GENERATED);
        itemModelGenerator.register(ModItems.METAL_DETECTOR, Models.GENERATED);
    }
}
