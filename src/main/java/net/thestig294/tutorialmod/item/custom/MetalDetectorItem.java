package net.thestig294.tutorialmod.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thestig294.tutorialmod.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.List;


// Instead of calling new Item(new FabricItemSettings()), for items that need custom behaviour,
// you need to subclass the Item class!
public class MetalDetectorItem extends Item {
    public MetalDetectorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!context.getWorld().isClient()){
            BlockPos.Mutable positionClicked = context.getBlockPos().mutableCopy();
            PlayerEntity player = context.getPlayer();
            positionClicked.setY(255);
            boolean foundValuableBlock = false;

            for (int i = 0; i < positionClicked.getY() + 64; i++) {
                BlockState state = context.getWorld().getBlockState(positionClicked.down(i));

                if(isValuableBlock(state)){
                    assert player != null;
                    outputValuableBlock(positionClicked.down(i), player, state.getBlock());
                    foundValuableBlock = true;
                    break;
                }
            }

            if(!foundValuableBlock){
                assert player != null;
                player.sendMessage(Text.literal("No Valuables Found!"));
            }
        }
//        Remember this? This is just a "comsumer" object, which is just (in this case) a callback function being passed
//        (Just like bullet.Callback in Lua!)
//        fruits.stream()
//                .filter(fruit -> fruit.startsWith("O"))
//                .forEach(System.out::println);

        assert context.getPlayer() != null;
        context.getStack().damage(1, context.getPlayer(),
                playerEntity -> playerEntity.sendToolBreakStatus(playerEntity.getActiveHand()));

//        "playerEntity.sendToolBreakStatus() is essentially the SendFullStateUpdate() of damaging an item!
//        This is pretty much the boilerplate for damaging an item's durability by 1!

        return ActionResult.SUCCESS;
    }

    private void outputValuableBlock(BlockPos blockPos, PlayerEntity player, Block block) {
//        Basically ply:PrintMessage() from Lua
//        the "overlay" bool controls whether it is a centre-screen or chat message
        player.sendMessage(Text.literal("Found " + block.asItem().getName().getString() + " at " +
                "(" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + ")"), false);
    }

    private boolean isValuableBlock(BlockState state) {
        return state.isIn(ModTags.Blocks.METAL_DETECTOR_DETECTABLE_BLOCKS);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
//         Don't do this: tooltip.add(Text.literal("Makes a sweet sound when right-clicked"));
//         Use Text.translatable(), to ensure text can be translated, not the end of the world in practice though...
        tooltip.add(Text.translatable("tooltip.tutorialmod.metal_detector"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
