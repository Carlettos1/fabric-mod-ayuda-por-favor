package com.carlettos.fmapf.listas;

import com.carlettos.fmapf.CarlettosMod;
import com.carlettos.fmapf.recipe.MesitaRecipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RecipeSerializers {
	public static RecipeSerializer<MesitaRecipe> MESITA;
	
	public static void register() {
		MESITA = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(CarlettosMod.ID, "mesiteo"), new MesitaRecipe.Serializer());
	}
}
