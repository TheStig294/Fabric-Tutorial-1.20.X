package net.thestig294.tutorialmod.world;

import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

// Utility functions copied straight from the OrePlacedFeatures class, since they are private for some reason....

public class ModOrePlacement {
    public static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    public static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }

    @SuppressWarnings("unused")
    public static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }
}
