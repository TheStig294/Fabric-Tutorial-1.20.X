package net.thestig294.tutorialmod.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.thestig294.tutorialmod.block.ModBlocks;
import net.thestig294.tutorialmod.recipe.GemPolishingRecipe;
import net.thestig294.tutorialmod.screen.GemPolishingScreen;

// This whole thing doesn't work for some reason... However, it is a nice example of using someone else's mod
// Buuuuutttt... it's unlikely to work unless that mod is popular enough to have a maven repo...

public class TutorialModREIClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new GemPolishingCategory());

        registry.addWorkstations(GemPolishingCategory.GEM_POLISHING, EntryStacks.of(ModBlocks.GEM_POLISHING_STATION));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(GemPolishingRecipe.class, GemPolishingRecipe.Type.INSTANCE,
                GemPolishingDisplay::new);
    }

//    Adds a clickable area on the GemPolishingRecipe screen in REI
    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(75, 30, 20, 30), GemPolishingScreen.class,
                GemPolishingCategory.GEM_POLISHING);
    }
}
