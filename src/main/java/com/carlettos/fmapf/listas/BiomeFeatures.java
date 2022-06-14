package com.carlettos.fmapf.listas;

import com.carlettos.fmapf.CarlettosMod;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.world.gen.GenerationStep;

public class BiomeFeatures {
	public static void addOres() {
		var key = OreConfiguredFeatures.Placed.MAGIC_SMALL.getKey();
		if (key.isPresent()) {
			CarlettosMod.info("KEY NOT PRESENT AAAAAAAAAAAAAAAAAAA BiomeFeatues");
			BiomeModifications.addFeature((biome) -> true, GenerationStep.Feature.UNDERGROUND_ORES, key.get());
		}
	}
}
