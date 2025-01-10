package com.aznos.coffee.screen.custom;

import com.aznos.coffee.Coffee;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BrewerScreen extends HandledScreen<BrewerScreenHandler> {
    public static final Identifier GUI_TEXTURE = Identifier.of(Coffee.MOD_ID, "textures/gui/brewer/brewer_gui.png");
    public static final Identifier ARROW_TEXTURE = Identifier.of(Coffee.MOD_ID, "textures/gui/brewer/arrow_progress.png");
    public static final Identifier WATER_CAPACITY_TEXTURE = Identifier.of(Coffee.MOD_ID, "textures/gui/brewer/water_capacity.png");

    public BrewerScreen(BrewerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        titleY = 1000;
        playerInventoryTitleY = 1000;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        renderProgressArrow(context, x, y);
        renderWaterBar(context, x, y);
    }

    private void renderWaterBar(DrawContext context, int x, int y) {
        int barX = x + 151;
        int barY = y + 7;

        RenderSystem.setShaderTexture(0, WATER_CAPACITY_TEXTURE);

        // 3) Figure out how “full” the brewer is
        int water     = handler.getWaterAmount();  // e.g. 400 mB
        int capacity  = handler.getTankCapacity(); // e.g. 1000 mB
        int barHeight = 50;                        // your texture is 50 px tall

        if (capacity <= 0) {
            return; // avoid divide-by-zero if something goes wrong
        }

        // This computes how many pixels tall the fill portion should be
        // If brewer is half full, filled will be 25, etc.
        int filled = water * barHeight / capacity;

        // 4) Draw only the filled portion from the bottom up
        //
        // If 'filled' is, say, 20, we want the bottom 20 pixels of the texture.
        // That means we skip the top (50 - 20) = 30 pixels of the texture.
        // We'll also move the draw position down so it lines up at the bottom.

        if (filled > 0) {
            int textureX = 0;                    // left edge of your water_bar.png
            int textureY = barHeight - filled;   // skip the top portion
            int drawX    = barX;
            int drawY    = barY + (barHeight - filled); // move down
            int width    = 16;                   // your texture is 16 wide
            int height   = filled;               // just the bottom 'filled' segment

            // The last two params in drawTexture are the total texture size
            // (16 wide, 50 tall). This ensures the partial draw is correct.
            context.drawTexture(
                    WATER_CAPACITY_TEXTURE,
                    drawX, drawY,         // on-screen location
                    textureX, textureY,   // where to start in the .png
                    width, height,        // how much of the .png to draw
                    16, 50                // total size of the entire texture
            );
        }
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isCrafting()) {
            context.drawTexture(ARROW_TEXTURE, x + 73, y + 35, 0, 0, handler.getScaledArrowProgress(), 16, 24, 16);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
