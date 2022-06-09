package com.carlettos.fmapf.listas;

import com.carlettos.fmapf.CarlettosMod;
import com.carlettos.fmapf.item.Palito;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class Items {
	public static final Item PALITO = new Palito();
	
	public static final Item PALITO_BLOCK = new BlockItem(Blocks.PALITO_BLOCK, new FabricItemSettings().group(CarlettosMod.GRUPO));
	public static final Item MESA_PALITOS = new BlockItem(Blocks.MESA_PALITOS, new FabricItemSettings().group(CarlettosMod.GRUPO));
}
