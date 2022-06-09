package com.carlettos.fmapf.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.carlettos.fmapf.CarlettosMod;

@Mixin(TitleScreen.class)
public class FMAPFMixin {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		CarlettosMod.LOGGER.info("This line is printed by an example mod mixin!");
	}
}
