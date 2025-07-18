package net.thestig294.tutorialmod.world.biome;

import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.thestig294.tutorialmod.TutorialMod;
import net.thestig294.tutorialmod.entity.ModEntities;
import net.thestig294.tutorialmod.sound.ModSounds;

// See the OverworldBiomeCreator class

public class ModBiomes {
    public static final RegistryKey<Biome> TEST_BIOME = RegistryKey.of(RegistryKeys.BIOME,
            new Identifier(TutorialMod.MOD_ID, "test_biome"));

    public static void boostrap(Registerable<Biome> context) {
        context.register(TEST_BIOME, testBiome(context));
    }

    public static void globalOverworldGeneration(GenerationSettings.LookupBackedBuilder builder) {
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addAmethystGeodes(builder);
        DefaultBiomeFeatures.addDungeons(builder);
        DefaultBiomeFeatures.addMineables(builder);
        DefaultBiomeFeatures.addSprings(builder);
        DefaultBiomeFeatures.addFrozenTopLayer(builder);
    }

    public static Biome testBiome(Registerable<Biome> context){
//        *NOTE: You must match the order of calling world gen functions, with the order vanilla biomes call them!*
//        See the OverworldBiomeCreator class for examples on world gen order!

        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
        spawnBuilder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.PORCUPINE, 2, 3, 5));

        spawnBuilder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.WOLF, 5, 4, 4));

        DefaultBiomeFeatures.addFarmAnimals(spawnBuilder);
        DefaultBiomeFeatures.addBatsAndMonsters(spawnBuilder);

        GenerationSettings.LookupBackedBuilder biomeBuilder =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        globalOverworldGeneration(biomeBuilder);
        DefaultBiomeFeatures.addMossyRocks(biomeBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeBuilder);
        DefaultBiomeFeatures.addExtraGoldOre(biomeBuilder);

//        The order of arguments using GenerationStep.Feature.XXX are also important!
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
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.TREES_PLAINS);
        DefaultBiomeFeatures.addForestFlowers(biomeBuilder);
        DefaultBiomeFeatures.addLargeFerns(biomeBuilder);

        DefaultBiomeFeatures.addDefaultMushrooms(biomeBuilder);
        DefaultBiomeFeatures.addDefaultVegetation(biomeBuilder);

//        See the Biome.Builder class for more function settings to use for your custom biome!
        return new Biome.Builder()
                .precipitation(true)
                .downfall(0.4f)
                .temperature(0.7f)
                .spawnSettings(spawnBuilder.build())
                .generationSettings(biomeBuilder.build())
                .effects(new BiomeEffects.Builder()
                        .waterColor(0xe82e3b)
                        .waterFogColor(0xbf1b26)
                        .skyColor(0x30c918)
                        .grassColor(0x7f03fc)
                        .foliageColor(0xd203fc)
                        .fogColor(0x22a1e6)
                        .moodSound(BiomeMoodSound.CAVE)
                        .music(MusicType.createIngameMusic(RegistryEntry.of(ModSounds.BAR_BRAWL))).build())
                .build();
    }
}
