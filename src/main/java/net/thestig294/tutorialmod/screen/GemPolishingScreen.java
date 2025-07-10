package net.thestig294.tutorialmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.thestig294.tutorialmod.TutorialMod;

public class GemPolishingScreen extends HandledScreen<GemPolishingScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(TutorialMod.MOD_ID, "textures/gui/gem_polishing_station_gui.png");

    public GemPolishingScreen(GemPolishingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
//        A super jank way of just removing the default inventory name text lol
        titleY = 1000;
        playerInventoryTitleY = 1000;
    }

//    Basically a "HUDPaintBackground" hook from Gmod!
    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f,1f,1f,1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE, x,y,0,0,backgroundWidth, backgroundHeight);

        renderProgressArrow(context, x, y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (handler.isCrafting()){
//            The 176 is the offset for the texture to draw on the texture image itself, which is where the arrow is!
//            x/y: displayed texture offset
//            u/v: grabbed texture offset from the texture file
            context.drawTexture(TEXTURE, x + 85, y + 30, 176, 0, 8, handler.getScaledProgress());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
