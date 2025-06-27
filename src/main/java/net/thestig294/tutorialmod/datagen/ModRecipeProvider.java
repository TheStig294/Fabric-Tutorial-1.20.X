package net.thestig294.tutorialmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;
import net.thestig294.tutorialmod.block.ModBlocks;
import net.thestig294.tutorialmod.item.ModItems;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    private static final List<ItemConvertible> RUBY_SMELTABLES = List.of(ModItems.RAW_RUBY,
            ModBlocks.RUBY_ORE, ModBlocks.DEEPSLATE_RUBY_ORE, ModBlocks.NETHERRACK_RUBY_ORE, ModBlocks.END_STONE_RUBY_ORE);

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
//        Look at the RecipeProvider class, and use the functions starting with: "offerXXX()"
//        You can use the ShapelessRecipeJsonBuilder.create() function (Using the examples in the RecipeProvider class),
//        and make shaped, shapeless, stonecutter, anvil, etc. recipes for any vanilla crafting block!

//        Furnace-like recipes
        offerSmelting(exporter, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,
                0.7f, 200, "ruby");
        offerBlasting(exporter, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,
                0.7f, 100, "ruby");

//        Block <-> 9 item recipes
//        offerReversibleCompactingRecipes(exporter, craftItemToBlockCategory, item, craftBlockToItemCategory, block)
        offerReversibleCompactingRecipes(exporter,
                RecipeCategory.BUILDING_BLOCKS, ModItems.RUBY,
                RecipeCategory.DECORATIONS, ModBlocks.RUBY_BLOCK);

//        Custom shaped recipes
//        ShapedRecipeJsonBuilder.create(recipeBookCategory, outputItem, itemCount)
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.METAL_DETECTOR, 1)
                .pattern("  R")
                .pattern(" I ")
                .pattern("I  ")
                .input('R', ModItems.RUBY)
                .input('I', Items.IRON_INGOT)
//                Make the recipe appear in your recipe book if you obtain either a ruby, or an iron ingot
                .criterion(hasItem(ModItems.RUBY), conditionsFromItem(ModItems.RUBY))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
//                *NOTE: If an item has multiple custom recipes, you'll need to make this identifier unique!*
//                E.g. Concatenate a string that describes what kind of recipe for this item it is.
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.METAL_DETECTOR)));
    }
}
