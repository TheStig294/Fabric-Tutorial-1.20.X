package net.thestig294.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SoundBlock extends Block {
    public SoundBlock(Settings settings) {
        super(settings);
    }

//    Tutorial gets us to use a deprecated method, need look into how other mods use the onUse method...
//    (Maybe there's a documented replacement somewhere in the Fabric docs?)
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos,
                              PlayerEntity player, Hand hand, BlockHitResult hit) {

//        world.playSound = sound.Play() from Lua, with an optional player argument
//        (This is being played in a *shared* context! So everyone can hear the sound, not just the player initiating it)
        world.playSound(player, pos, SoundEvents.BLOCK_NOTE_BLOCK_XYLOPHONE.value(), SoundCategory.BLOCKS,
                1f, 1f);

        return ActionResult.SUCCESS;
    }
}
