package net.thestig294.tutorialmod.block.entity.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.thestig294.tutorialmod.block.entity.GemPolishingStationBlockEntity;

public class GemPolishingBlockEntityRenderer implements BlockEntityRenderer<GemPolishingStationBlockEntity> {
//    Not really sure what the best way to handle having to pass a constructor to a factory is,
//    if you have nothing to initialise in the constructor...
    public GemPolishingBlockEntityRenderer(BlockEntityRendererFactory.Context ignoredContext){

    }

//    *See: CampfireBlockEntityRenderer for an example!*
    @Override
    public void render(GemPolishingStationBlockEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        ItemStack stack = entity.getRenderStack();

//        Takes a copy of how the block entity looks
        matrices.push();
//        Applies some transformations
        matrices.translate(0.5f, 0.75f, 0.5f);
        matrices.scale(0.35f, 0.35f, 0.35f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(270));

        assert entity.getWorld() != null;
//        And plonks an item into the render stack
        itemRenderer.renderItem(stack, ModelTransformationMode.GUI, getLightLevel(entity.getWorld(),
                entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(),1);
//        And immediately pops it so items aren't being created every tick
        matrices.pop();
    }

    private int getLightLevel(World world, BlockPos pos){
        int bLight = world.getLightLevel(LightType.BLOCK, pos);
        int sLight = world.getLightLevel(LightType.SKY, pos);

        return LightmapTextureManager.pack(bLight, sLight);
    }
}
