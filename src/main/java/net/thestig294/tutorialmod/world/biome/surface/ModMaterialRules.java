package net.thestig294.tutorialmod.world.biome.surface;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.thestig294.tutorialmod.block.ModBlocks;
import net.thestig294.tutorialmod.world.biome.ModBiomes;

// See: VanillaSurfaceRules for examples of custom biome landscapes
// Also see: https://github.com/TheForsakenFurby/Surface-Rules-Guide-Minecraft-JE-1.18/blob/main/Guide.md
// ("Surface Rules" = Forge name for "Material Rules")
// Also see: https://github.com/Glitchfiend/TerraBlender/blob/1.20.2/Example/Fabric/src/main/java/terrablender/example/TestSurfaceRuleData.java

public class ModMaterialRules {
    private static final MaterialRules.MaterialRule DIRT = makeStateRule(Blocks.DIRT);
    private static final MaterialRules.MaterialRule GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final MaterialRules.MaterialRule RUBY = makeStateRule(ModBlocks.RUBY_BLOCK);
    private static final MaterialRules.MaterialRule RAW_RUBY = makeStateRule(ModBlocks.RAW_RUBY_BLOCK);

    public static MaterialRules.MaterialRule makeRules() {
        MaterialRules.MaterialCondition isAtOrAboveWaterLevel = MaterialRules.water(-1, 0);

        MaterialRules.MaterialRule grassSurface = MaterialRules.sequence(MaterialRules.condition(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);

        return MaterialRules.sequence(
//                If in our biome, change all stone floor and ceiling blocks to raw ruby and ruby blocks!
                MaterialRules.sequence(MaterialRules.condition(MaterialRules.biome(ModBiomes.TEST_BIOME),
                        MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, RAW_RUBY)),
                        MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, RUBY)),

//                Default to a grass and dirt surface
                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, grassSurface)
        );
    }

    private static MaterialRules.MaterialRule makeStateRule (Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
