package net.thestig294.tutorialmod.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
//        Don't do this: tooltip.add(Text.literal("Makes a sweet sound when right-clicked"));
//        Use Text.translatable(), to ensure text can be translated, not the end of the world in practice though...
        tooltip.add(Text.translatable("tooltip.tutorialmod.sound_block").formatted(Formatting.RED));
        tooltip.add(Text.translatable("tooltip.tutorialmod.sound_block").styled(style -> style
                .withColor(Formatting.GREEN)
                .withBold(true)
//                Minecraft has 3 in-built fonts: "default", "uniform" (Small, monospaced), and "alt" (Enchantment font)
//                .withFont(new Identifier("minecraft", "default"))));
                .withFont(new Identifier("minecraft", "uniform"))));
//                .withFont(new Identifier("minecraft", "alt"))));
        super.appendTooltip(stack, world, tooltip, options);
    }
}
