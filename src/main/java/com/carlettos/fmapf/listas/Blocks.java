package com.carlettos.fmapf.listas;

import com.carlettos.fmapf.block.MesaPalitos;
import com.carlettos.fmapf.block.PalitoBlock;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class Blocks {
	public static final Block PALITO_BLOCK = new PalitoBlock();
	public static final Block MESA_PALITOS = new MesaPalitos();
	public static final Block MAGIC_ORE = new OreBlock(FabricBlockSettings.copyOf(net.minecraft.block.Blocks.IRON_ORE), UniformIntProvider.create(1, 3));
}
