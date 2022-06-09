package com.carlettos.fmapf.listas;

import com.carlettos.fmapf.CarlettosMod;
import com.carlettos.fmapf.block.blockentity.MesaBlockEntity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockEntities {
	public static BlockEntityType<MesaBlockEntity> MESITA_BLOCK_ENTITY;
	
	public static void registrar() {
		MESITA_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CarlettosMod.ID, "mesita_block_entity"), FabricBlockEntityTypeBuilder.create(MesaBlockEntity::new, Blocks.MESA_PALITOS).build());
	}
}
