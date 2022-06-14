package com.carlettos.fmapf.biome;

import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class MagicBiome {
    public static int getSkyColor(float temperature) {
        float f = temperature;
        f /= 3.0f;
        f = MathHelper.clamp(f, -1.0f, 1.0f);
        return MathHelper.hsvToRgb(0.62222224f - f * 0.05f, 0.5f + f * 0.1f, 1.0f);
    }
    
	public static Biome createOverworld(){
        SpawnSettings.Builder spawn = new SpawnSettings.Builder();
        GenerationSettings.Builder generation = new GenerationSettings.Builder();
        
        DefaultBiomeFeatures.addLandCarvers(generation);
        DefaultBiomeFeatures.addAmethystGeodes(generation);
        DefaultBiomeFeatures.addDungeons(generation);
        DefaultBiomeFeatures.addMineables(generation);
        DefaultBiomeFeatures.addSprings(generation);
        DefaultBiomeFeatures.addFrozenTopLayer(generation);
        
        DefaultBiomeFeatures.addPlainsMobs(spawn);
        DefaultBiomeFeatures.addPlainsTallGrass(generation);
        DefaultBiomeFeatures.addDefaultOres(generation);
        DefaultBiomeFeatures.addDefaultDisks(generation);
        DefaultBiomeFeatures.addPlainsFeatures(generation);
        DefaultBiomeFeatures.addDefaultMushrooms(generation);
        DefaultBiomeFeatures.addDefaultVegetation(generation);
        
        return new Biome.Builder()
        		.temperatureModifier(Biome.TemperatureModifier.NONE)
        		.precipitation(Biome.Precipitation.RAIN)
        		.downfall(0.8f)
        		.temperature(0.5f)
        		.effects(new BiomeEffects.Builder().waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColor(0.5f)).moodSound(BiomeMoodSound.CAVE).music(null).build())
        		.spawnSettings(spawn.build())
        		.generationSettings(generation.build())
        		.build();
	}
}
