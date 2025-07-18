package net.thestig294.tutorialmod.world.dimension;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.thestig294.tutorialmod.TutorialMod;

import java.util.OptionalLong;

// See: https://misode.github.io/dimension/ for how to generate a custom dimension .json file!
// (resources/assets/data/tutorialmod/dimension/stigdim.json)

public class ModDimensions {
//    Dimension
    @SuppressWarnings("unused")
    public static final RegistryKey<DimensionOptions> STIGDIM_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
            new Identifier(TutorialMod.MOD_ID, "stigdim"));
    @SuppressWarnings("unused")
    public static final RegistryKey<World> STIGDIM_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(TutorialMod.MOD_ID, "stigdim"));

//    Dimension type, *changed the first line of stigdim.json!
    public static final RegistryKey<DimensionType> STIG_DIM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            new Identifier(TutorialMod.MOD_ID, "stigdim_type"));

    public static void bootstrapType(Registerable<DimensionType> context) {
        context.register(STIG_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                true, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                DimensionTypes.OVERWORLD_ID, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, UniformIntProvider.create(0,0),0))
        );
    }
}
