package com.carlettos.fmapf.listas;

import com.carlettos.fmapf.CarlettosMod;
import com.carlettos.fmapf.biome.MagicBiome;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class Biomes {
	public static final RegistryKey<Biome> MAGIC_BIOME = RegistryKey.of(Registry.BIOME_KEY, new Identifier(CarlettosMod.ID, "magic_biome"));
	
	public static void register() {
		BuiltinRegistries.add(BuiltinRegistries.BIOME, MAGIC_BIOME, MagicBiome.createOverworld());
	}
}
