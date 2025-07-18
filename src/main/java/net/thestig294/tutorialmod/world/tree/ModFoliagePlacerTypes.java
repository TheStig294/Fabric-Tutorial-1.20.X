package net.thestig294.tutorialmod.world.tree;

import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.thestig294.tutorialmod.TutorialMod;
import net.thestig294.tutorialmod.mixin.FoliagePlacerTypeInvoker;
import net.thestig294.tutorialmod.world.tree.custom.ChestnutFoliagePlacer;

public class ModFoliagePlacerTypes {
    public static final FoliagePlacerType<?> CHESTNUT_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("chestnut_foliage_placer", ChestnutFoliagePlacer.CODEC);

    public static void register() {
        TutorialMod.LOGGER.info("Registering Foliage Placer for " + TutorialMod.MOD_ID);
    }
}
