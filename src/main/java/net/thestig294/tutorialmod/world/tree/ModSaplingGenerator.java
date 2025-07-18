package net.thestig294.tutorialmod.world.tree;


import net.minecraft.block.SaplingGenerator;
import net.thestig294.tutorialmod.world.ModConfiguredFeatures;

import java.util.Optional;

// Press Shift-F6 to rename a class or variable name!
public class ModSaplingGenerator {
    public static final SaplingGenerator CHESTNUT =
            new SaplingGenerator("chestnut", 0f, Optional.empty(),
                    Optional.empty(),
                    Optional.of(ModConfiguredFeatures.CHESTNUT_KEY),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty());
}
