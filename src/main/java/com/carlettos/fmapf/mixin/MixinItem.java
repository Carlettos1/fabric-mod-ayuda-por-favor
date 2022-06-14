package com.carlettos.fmapf.mixin;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.carlettos.fmapf.essence.Essence;
import com.carlettos.fmapf.interfaces.IEssenceGetter;
import com.carlettos.fmapf.listas.ItemTags;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

@Mixin(Item.class)
public class MixinItem implements IEssenceGetter {
	@Override
	public Optional<Essence> getEssence() {
		ItemStack stack = new ItemStack((Item)(Object)this);
		if (stack.isIn(ItemTags.MAGIC_ESSENCE)) {
			return Essence.MAGIC.toOptional();
		} else if (stack.isIn(ItemTags.MINERAL_ESSENCE)) {
			return Essence.MINERAL.toOptional();
		} else if (stack.isIn(ItemTags.PALITO_ESSENCE)) {
			return Essence.PALITO.toOptional();
		} else {
			return Optional.empty();
		}
	}
	
	@Inject(at = @At("HEAD"), method = "appendTooltip")
	private void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo info) {
		var essence = ((Item)(Object)this).getEssence();
		if (essence.isPresent()) {
			tooltip.add(new TranslatableText("fmapf.essence." + essence.get().name().toLowerCase()).formatted(Formatting.GOLD));
		}
    }
}
