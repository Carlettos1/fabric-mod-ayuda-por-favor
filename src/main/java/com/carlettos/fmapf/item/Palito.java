package com.carlettos.fmapf.item;

import java.util.List;

import com.carlettos.fmapf.CarlettosMod;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Palito extends Item {
	public Palito() {
		super(new FabricItemSettings().group(CarlettosMod.GRUPO));
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		user.sendMessage(Text.of("owowo"), false);
		CarlettosMod.LOGGER.info(user.getMainHandStack().getItem().getEssence().toString());
		return TypedActionResult.success(user.getStackInHand(hand));
	}
	
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(new TranslatableText("item.fmapf.item.palito.tooltip").formatted(Formatting.DARK_AQUA));
		super.appendTooltip(stack, world, tooltip, context);
	}
}
