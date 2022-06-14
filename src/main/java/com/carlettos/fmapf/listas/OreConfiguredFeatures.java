package com.carlettos.fmapf.listas;

import static net.minecraft.world.gen.feature.OreConfiguredFeatures.STONE_ORE_REPLACEABLES;

import java.util.List;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.Target;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

public class OreConfiguredFeatures {
    public static List<Target> MAGIC_ORE = List.of(OreFeatureConfig.createTarget(STONE_ORE_REPLACEABLES, Blocks.MAGIC_ORE.getDefaultState()));
    public static RegistryEntry<ConfiguredFeature<OreFeatureConfig, ?>> ORE_MAGIC_SMALL;

    public static void register() {
    	ORE_MAGIC_SMALL = ConfiguredFeatures.register("ore_magic_small", Feature.ORE, new OreFeatureConfig(MAGIC_ORE, 6));
    }
    
    public static class Placed {
    	public static RegistryEntry<PlacedFeature> MAGIC_SMALL;
    	
    	public static void register() {
    		MAGIC_SMALL = PlacedFeatures.register("magic_small", OreConfiguredFeatures.ORE_MAGIC_SMALL, Placed.modifiersWithCount(16, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-16), YOffset.fixed(112))));
    	}

        public static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
            return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
        }

        public static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
            return Placed.modifiers(CountPlacementModifier.of(count), heightModifier);
        }

        public static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
            return Placed.modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
        }
    }
}
