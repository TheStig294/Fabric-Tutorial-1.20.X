package net.thestig294.tutorialmod.world;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.thestig294.tutorialmod.TutorialMod;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> RUBY_ORE_PLACED_KEY = registryKey("ruby_ore_placed");
    public static final RegistryKey<PlacedFeature> NETHER_RUBY_ORE_PLACED_KEY = registryKey("nether_ruby_ore_placed");
    public static final RegistryKey<PlacedFeature> END_RUBY_ORE_PLACED_KEY = registryKey("end_ruby_ore_placed");

    public static void boostrap(Registerable<PlacedFeature> context) {
//        Used for mapping our configured feature, with the placed features we're defining below
//        A Configured Feature is a type of worldgen
//        A Placed Feature is specifically a type of worldgen that places blocks into the world
//        (Yipeee our first use of auto...)
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, RUBY_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.RUBY_ORE_KEY),
//                Veins per chunk
                ModOrePlacement.modifiersWithCount(12,
//                        Max/min Y-layer the ore can spawn at
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
//                        .trapezoid(YOffset.fixed(-80), YOffset.fixed(80))
//                        gives an ore that increases its spawn rate towards the middle (e.g. Lapis ore)
//                        .uniform() gives an ore with equal likelihood for every layer (e.g. Redstone ore)
        register(context, NETHER_RUBY_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.NETHER_RUBY_ORE_KEY),
                ModOrePlacement.modifiersWithCount(12,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
        register(context, END_RUBY_ORE_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.END_RUBY_ORE_KEY),
                ModOrePlacement.modifiersWithCount(12,
                        HeightRangePlacementModifier.uniform(YOffset.fixed(-80), YOffset.fixed(80))));
    }

    public static RegistryKey<PlacedFeature> registryKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(TutorialMod.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key,
                                 RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers){

        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
