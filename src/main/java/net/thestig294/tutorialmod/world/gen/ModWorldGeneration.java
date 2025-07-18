package net.thestig294.tutorialmod.world.gen;

public class ModWorldGeneration {
    public static void generateModWorldGen(){
//        *NOTE: YOU MUST GENERATE WORLD GEN IN THE CORRECT ORDER!!!*
//        See: GenerationStep.Feature for the correct order:
//        1. RAW_GENERATION
//		  2. LAKES
//		  3. LOCAL_MODIFICATIONS
//		  4. UNDERGROUND_STRUCTURES
//		  5. SURFACE_STRUCTURES
//		  6. STRONGHOLDS
//		  7. UNDERGROUND_ORES
//		  8. UNDERGROUND_DECORATION
//		  9. FLUID_SPRINGS
//		  10. VEGETAL_DECORATION
//		  11. TOP_LAYER_MODIFICATION
        ModOreGeneration.generateOres(); // Uses: GenerationStep.Feature.UNDERGROUND_ORES in the call
        ModTreeGeneration.generateTrees(); // Uses: GenerationStep.Feature.VEGETAL_DECORATION in the call
    }
}
