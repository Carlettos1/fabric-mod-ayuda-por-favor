package com.carlettos.fmapf.screen;

import com.carlettos.fmapf.CarlettosMod;
import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(value=EnvType.CLIENT)
public class MesitaScreen extends HandledScreen<MesitaScreenHandler> {
    static final Identifier TEXTURE = new Identifier(CarlettosMod.ID, "textures/gui/container/mesita.png");

	public MesitaScreen(MesitaScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
		handler.addListener(new ScreenHandlerListener() {
			
			@Override
			public void onSlotUpdate(ScreenHandler handler, int slot, ItemStack stack) {
			}
			
			@Override
			public void onPropertyUpdate(ScreenHandler handler, int property, int value) {
			}
		});
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = this.x;
        int j = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
	}
}
