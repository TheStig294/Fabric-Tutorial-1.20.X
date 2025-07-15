package net.thestig294.tutorialmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.thestig294.tutorialmod.block.ModBlocks;
import net.thestig294.tutorialmod.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
//        This is all you need to do to make a custom armour set trimmable, just add the set to the trimmable tag!
        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.RUBY_HELMET, ModItems.RUBY_CHESTPLATE, ModItems.RUBY_LEGGINGS, ModItems.RUBY_BOOTS);

//        Makes a music disk item usable!
        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS)
                .add(ModItems.BAR_BRAWL_MUSIC_DISK);

        getOrCreateTagBuilder(ItemTags.CREEPER_DROP_MUSIC_DISCS)
                .add(ModItems.BAR_BRAWL_MUSIC_DISK);

        getOrCreateTagBuilder(ItemTags.PLANKS)
                .add(ModBlocks.CHESTNUT_PLANKS.asItem());

//        Allows to use as furnace fuel
        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.CHESTNUT_LOG.asItem())
                .add(ModBlocks.CHESTNUT_WOOD.asItem())
                .add(ModBlocks.STRIPPED_CHESTNUT_LOG.asItem())
                .add(ModBlocks.STRIPPED_CHESTNUT_WOOD.asItem());
    }
}
