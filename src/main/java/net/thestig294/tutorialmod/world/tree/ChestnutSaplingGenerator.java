package net.thestig294.tutorialmod.world.tree;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.thestig294.tutorialmod.world.ModConfiguredFeatures;
import org.jetbrains.annotations.Nullable;

public class ChestnutSaplingGenerator extends SaplingGenerator {
    @Override
    protected @Nullable RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return ModConfiguredFeatures.CHESTNUT_KEY;
    }
}
