package net.thestig294.tutorialmod.world.tree;

import net.minecraft.world.gen.trunk.TrunkPlacerType;
import net.thestig294.tutorialmod.TutorialMod;
import net.thestig294.tutorialmod.mixin.TrunkPlacerTypeInvoker;
import net.thestig294.tutorialmod.world.tree.custom.ChestnutTrunkPlacer;

public class ModTrunkPlacerTypes {
    public static final TrunkPlacerType<?> CHESTNUT_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("chestnut_trunk_placer", ChestnutTrunkPlacer.CODEC);

    public static void register() {
        TutorialMod.LOGGER.info("Registering Trunk Placers for " + TutorialMod.MOD_ID);
    }
}
