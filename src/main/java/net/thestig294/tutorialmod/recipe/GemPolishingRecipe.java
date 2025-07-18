package net.thestig294.tutorialmod.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.List;

public class GemPolishingRecipe implements Recipe<SimpleInventory> {
    private final ItemStack output;
    private final List<Ingredient> recipeItems;

    public GemPolishingRecipe(List<Ingredient> ingredients, ItemStack itemStack) {
        this.output = itemStack;
        this.recipeItems = ingredients;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if (world.isClient()) {
            return false;
        }

//        Simply checking if the first item listed in the ingredient of the recipe,
//        matches the item in the input slot of the machine block!
//        (Because our custom machine block only takes 1 item as an input!)
        return recipeItems.get(0).test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
        list.addAll(recipeItems);
        return list;
    }

//    Hooray for defiantly not thread-safe, not-really-singletons!
    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<GemPolishingRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "gem_polishing";
    }

//    A "Codec" essentially reads JSON files and creates objects on the fly!
    public static class Serializer implements RecipeSerializer<GemPolishingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "gem_polishing";

//        This mess here is unfortunately what you have to do whenever adding a custom recipe...
        public static final Codec<GemPolishingRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
//                Reads in a max of 9 ingredients, and the JSON field is called "ingredients"
                validateAmount(Ingredient.DISALLOW_EMPTY_CODEC, 9).fieldOf("ingredients").forGetter(GemPolishingRecipe::getIngredients),
//                The output of the recipe is read from the "output" JSON field, and GemPolishingRecipe's "output" data member is set to it
                ItemStack.RECIPE_RESULT_CODEC.fieldOf("output").forGetter(r -> r.output)
//                And finally, a new GemPolishingRecipe object is created, with this data
        ).apply(in, GemPolishingRecipe::new));

        @SuppressWarnings("SameParameterValue")
        private static Codec<List<Ingredient>> validateAmount(Codec<Ingredient> delegate, int max) {
            return Codecs.validate(Codecs.validate(
                    delegate.listOf(), list -> list.size() > max ? DataResult.error(() -> "Recipe has too many ingredients!") : DataResult.success(list)
            ), list -> list.isEmpty() ? DataResult.error(() -> "Recipe has no ingredients!") : DataResult.success(list));
        }

        @Override
        public Codec<GemPolishingRecipe> codec() {
            return CODEC;
        }

//        These last 2 functions are for synchronizing data between the client and server
//        similar to net.Receive() and net.Broadcast() from Gmod!
//        (See the "PacketByteBuf" class for more info!)
        @Override
        public GemPolishingRecipe read(PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

//            Ignoring this error as this is more readable for my baby Java brain...
//            (We can get fancy with streams later...)
            //noinspection Java8ListReplaceAll
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }

            ItemStack output = buf.readItemStack();
            return new GemPolishingRecipe(inputs, output);
        }

        @Override
        public void write(PacketByteBuf buf, GemPolishingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buf);
            }

            buf.writeItemStack(recipe.getResult(null));
        }
    }
}
