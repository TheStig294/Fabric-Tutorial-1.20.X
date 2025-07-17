package net.thestig294.tutorialmod.world;

import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.thestig294.tutorialmod.TutorialMod;
import net.thestig294.tutorialmod.block.ModBlocks;

import java.util.List;

// "Configured Feature":
// A type world gen that has a configuration, e.g. the "ore" feature can be configured to change how a world generates ore
// All the configured feature does is tell Minecraft you want to do *something* with these replaceable blocks,
// the "what", i.e. actually placing an ore block down, comes from the ModPlacedFeatures class...
// Placing any block into the world through worldgen is called a "Placed Feature"

public class ModConfiguredFeatures {
//    NOTE: This is inconsistent with how the ore blocks themselves are named... (netherrack_/end_stone_ruby_ore)
    public static final RegistryKey<ConfiguredFeature<?, ?>> RUBY_ORE_KEY = registerKey("ruby_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NETHER_RUBY_ORE_KEY = registerKey("nether_ruby_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> END_RUBY_ORE_KEY = registerKey("end_ruby_ore");

    public static void boostrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
//        There is no tag for generating ore in The End, so we just replace end stone...
        RuleTest endReplaceables = new BlockMatchRuleTest(Blocks.END_STONE);

//        Mapping what blocks get replaced by what ore:
        List<OreFeatureConfig.Target> overworldRubyOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.RUBY_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_RUBY_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> netherRubyOres =
                List.of(OreFeatureConfig.createTarget(netherReplaceables, ModBlocks.NETHERRACK_RUBY_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> endRubyOres =
                List.of(OreFeatureConfig.createTarget(endReplaceables, ModBlocks.END_STONE_RUBY_ORE.getDefaultState()));

//        Size = rarity of ore spawning, bigger number = more ore!
//        (See the OrePlacedFeatures class for example ore rarity values)
        register(context, RUBY_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldRubyOres, 12));
        register(context, NETHER_RUBY_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherRubyOres, 12));
        register(context, END_RUBY_ORE_KEY, Feature.ORE, new OreFeatureConfig(endRubyOres, 12));
    }

    public static RegistryKey<ConfiguredFeature<?,?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(TutorialMod.MOD_ID, name));
    }

    @SuppressWarnings("SameParameterValue")
    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration){
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
