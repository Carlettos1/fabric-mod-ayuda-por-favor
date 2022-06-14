package com.carlettos.fmapf.listas;

import com.carlettos.fmapf.CarlettosMod;
import com.carlettos.fmapf.biome.MagicBiome;

import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.TheEndBiomes;
import net.fabricmc.fabric.impl.biome.BiomeSourceAccess;
import net.fabricmc.fabric.mixin.event.lifecycle.WorldMixin;
import net.minecraft.client.render.DimensionEffects.Overworld;
import net.minecraft.data.report.WorldgenProvider;
import net.minecraft.datafixer.fix.BiomesFix;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.dedicated.ServerPropertiesHandler.WorldGenProperties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.OverworldBiomeCreator;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.biome.source.util.VanillaBiomeParameters;
import net.minecraft.world.biome.source.util.VanillaTerrainParametersCreator;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.WorldPresets;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGenerators;

public class Biomes {
	public static final RegistryKey<Biome> MAGIC_BIOME = RegistryKey.of(Registry.BIOME_KEY, new Identifier(CarlettosMod.ID, "magic_biome"));
	
	public static void register() {
		BuiltinRegistries.add(BuiltinRegistries.BIOME, MAGIC_BIOME, MagicBiome.createOverworld());
	}
}
