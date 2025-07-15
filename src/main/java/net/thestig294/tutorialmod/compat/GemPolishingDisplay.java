package net.thestig294.tutorialmod.compat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.recipe.RecipeEntry;
import net.thestig294.tutorialmod.recipe.GemPolishingRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GemPolishingDisplay extends BasicDisplay {
    public GemPolishingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public GemPolishingDisplay(RecipeEntry<GemPolishingRecipe> recipe) {
//        Since our recipe is only ever going to have 1 output, we can basically do what we did for the input, but in 1-line:
//        Basically converting the output item into ItemStack -> EntryStack -> EntryIngredient -> List<EntryIngredient>
//        (But the list only contains 1 item!)
        this(getInputList(recipe.value()), List.of(EntryIngredient.of(EntryStacks.of(recipe.value().getResult(null)))));
    }

    private static List<EntryIngredient> getInputList(GemPolishingRecipe recipe){
        if (recipe == null) return Collections.emptyList();
        List<EntryIngredient> list = new ArrayList<>();
//        Converting the Ingredient into an EntryIngredient, and returning the list
        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(0)));
//        If your recipe takes 2 ingredients, you expand like so:
//        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(1)));
        return list;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return null;
    }
}
