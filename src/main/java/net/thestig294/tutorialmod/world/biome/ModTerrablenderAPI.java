package net.thestig294.tutorialmod.world.biome;

import net.minecraft.util.Identifier;
import net.thestig294.tutorialmod.TutorialMod;
import net.thestig294.tutorialmod.world.biome.surface.ModMaterialRules;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class ModTerrablenderAPI implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new ModOverworldRegion(new Identifier(TutorialMod.MOD_ID, "overworld"), 4));

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, TutorialMod.MOD_ID, ModMaterialRules.makeRules());
    }
}
