package net.thestig294.tutorialmod.item;

//import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

// See vanilla materials from the ToolMaterial class vvv
public enum ModToolMaterial implements ToolMaterial {
//    SAPPHIRE(5, 650,4.5f,3.5f,26,
//            () -> Ingredient.ofItems(ModItems.RUBY)),
//    RUBY(MiningLevels.STONE), 650,4.5f,3.5f,26,
//        () -> Ingredient.ofItems(ModItems.RUBY),
    RUBY(5, 650,10.5f,10.5f,26,
        () -> Ingredient.ofItems(ModItems.RUBY));

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
//        I guess Java Suppliers are sorta like smart pointers in C++...
        return this.repairIngredient.get();
    }
}
