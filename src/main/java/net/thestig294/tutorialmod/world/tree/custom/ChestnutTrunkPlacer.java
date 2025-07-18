package net.thestig294.tutorialmod.world.tree.custom;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import net.thestig294.tutorialmod.world.tree.ModTrunkPlacerTypes;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ChestnutTrunkPlacer extends TrunkPlacer {
    public static final Codec<ChestnutTrunkPlacer> CODEC = RecordCodecBuilder.create(objectInstance ->
            fillTrunkPlacerFields(objectInstance).apply(objectInstance, ChestnutTrunkPlacer::new));

    public ChestnutTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> getType() {
        return ModTrunkPlacerTypes.CHESTNUT_TRUNK_PLACER;
    }

//    Look at the classes that inherit from TrunkPlacer for example .generate() functions!
    @Override
    public List<FoliagePlacer.TreeNode> generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer,
                                                 Random random, int height, BlockPos startPos, TreeFeatureConfig config) {
//        Changes the grass block (or whatever growable block) underneath the tree to a dirt block, when it grows!
        setToDirt(world, replacer, random, startPos.down(), config);
        int height_ = height + random.nextBetween(firstRandomHeight, firstRandomHeight + 2) + random.nextBetween(secondRandomHeight - 1, secondRandomHeight + 1);


        for (int i = 0; i < height_; i++) {
//            Places a line of tree trunk blocks straight up!
            getAndSetState(world, replacer, random, startPos.up(i), config);

//            Places a bunch of random branches, all the same length of 4 (x <= 4)
            if (i % 2 == 0 && random.nextBoolean()) {
//                Essentially passing an endPos for the end of the branch to grow, in each cardinal direction
//                (But you have to pass a BlockState in that position in a weird way as well for some reason...)
                if (random.nextFloat() > 0.25f) {
                    for (int x = 1; x <= 3; x++){
                        replacer.accept(startPos.up(i).offset(Direction.NORTH, x), config.trunkProvider
                                .get(random, startPos.up(i).offset(Direction.NORTH, x)).with(PillarBlock.AXIS, Direction.Axis.Z));
                    }
                }
                if (random.nextFloat() > 0.25f) {
                    for (int x = 1; x <= 4; x++){
//                        (I'm not sure if the whole (BlockState) Function.identity().apply() call is needed or not... Seems redundant...)
                        replacer.accept(startPos.up(i).offset(Direction.SOUTH, x), (BlockState) Function.identity().apply(config.trunkProvider
                                .get(random, startPos.up(i).offset(Direction.SOUTH, x)).with(PillarBlock.AXIS, Direction.Axis.Z)));
                    }
                }
                if (random.nextFloat() > 0.25f) {
                    for (int x = 1; x <= 4; x++){
                        replacer.accept(startPos.up(i).offset(Direction.EAST, x), (BlockState) Function.identity().apply(config.trunkProvider
                                .get(random, startPos.up(i).offset(Direction.EAST, x)).with(PillarBlock.AXIS, Direction.Axis.X)));
                    }
                }
                if (random.nextFloat() > 0.25f) {
                    for (int x = 1; x <= 4; x++){
                        replacer.accept(startPos.up(i).offset(Direction.WEST, x), (BlockState) Function.identity().apply(config.trunkProvider
                                .get(random, startPos.up(i).offset(Direction.WEST, x)).with(PillarBlock.AXIS, Direction.Axis.X)));
                    }
                }
            }
        }

//        List of positions where leaves are going to be placed on your tree,
//        so if you had a branching tree, you would pass multiple tree nodes, not just one.
//        E.g. An Acacia tree
        return ImmutableList.of(new FoliagePlacer.TreeNode(startPos.up(height_), 0, false));
    }
}
