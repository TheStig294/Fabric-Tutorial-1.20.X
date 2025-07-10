package net.thestig294.tutorialmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.thestig294.tutorialmod.block.ModBlocks;
import net.thestig294.tutorialmod.entity.ModEntities;
import net.thestig294.tutorialmod.entity.client.ModModelLayers;
import net.thestig294.tutorialmod.entity.client.PorcupineModel;
import net.thestig294.tutorialmod.entity.client.PorcupineRenderer;
import net.thestig294.tutorialmod.screen.GemPolishingScreen;
import net.thestig294.tutorialmod.screen.ModScreenHandlers;

public class TutorialModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
//        Makes the Ruby Door and Trapdoor have a see-through texture for the window part of the block!
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUBY_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RUBY_TRAPDOOR, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TOMATO_CROP, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORN_CROP, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DAHLIA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_DAHLIA, RenderLayer.getCutout());

//
//        Registers the model and its rendering behaviour
//

//        Have to connect the entity to the renderer...
        EntityRendererRegistry.register(ModEntities.PORCUPINE, PorcupineRenderer::new);
//        ...and the model ID to the model
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.PORCUPINE, PorcupineModel::getTexturedModelData);

        HandledScreens.register(ModScreenHandlers.GEM_POLISHING_SCREEN_HANDLER, GemPolishingScreen::new);
    }
}
