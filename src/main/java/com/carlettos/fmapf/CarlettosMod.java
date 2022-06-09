package com.carlettos.fmapf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.carlettos.fmapf.listas.BlockEntities;
import com.carlettos.fmapf.listas.Blocks;
import com.carlettos.fmapf.listas.ItemTags;
import com.carlettos.fmapf.listas.Items;
import com.carlettos.fmapf.listas.RecipeSerializers;
import com.carlettos.fmapf.listas.RecipeTypes;
import com.carlettos.fmapf.listas.ScreenHandlers;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CarlettosMod implements ModInitializer {
	public static final String ID = "fmapf";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);
	public static final ItemGroup GRUPO = FabricItemGroupBuilder.build(new Identifier(ID, "general"), () -> new ItemStack(net.minecraft.item.Items.PODZOL));

	@Override
	public void onInitialize() {
		LOGGER.info("Registro:");
		BlockEntities.registrar();
		ScreenHandlers.registrar();
		ItemTags.register();
		RecipeTypes.register();
		RecipeSerializers.register();
		
		Registry.register(Registry.ITEM, new Identifier(ID, "palito"), Items.PALITO);
		FuelRegistry.INSTANCE.add(Items.PALITO, 450);
		CompostingChanceRegistry.INSTANCE.add(Items.PALITO, 0.5F);
		
		Registry.register(Registry.BLOCK, new Identifier(ID, "palito_block"), Blocks.PALITO_BLOCK);
		Registry.register(Registry.ITEM, new Identifier(ID, "palito_block"), Items.PALITO_BLOCK);
		
		Registry.register(Registry.BLOCK, new Identifier(ID, "mesa_palitos"), Blocks.MESA_PALITOS);
		Registry.register(Registry.ITEM, new Identifier(ID, "mesa_palitos"), Items.MESA_PALITOS);
		
		BlockRenderLayerMap.INSTANCE.putBlock(Blocks.PALITO_BLOCK, RenderLayer.getCutoutMipped());
	}
}
