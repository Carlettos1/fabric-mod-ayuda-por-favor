package com.carlettos.fmapf.listas;

import com.carlettos.fmapf.CarlettosMod;
import com.carlettos.fmapf.recipe.MesitaRecipe;

import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RecipeTypes {
	public static RecipeType<MesitaRecipe> MESITA;
	
	public static void register() {
		var mesiteo = new Identifier(CarlettosMod.ID, "mesiteo");
		MESITA = Registry.register(Registry.RECIPE_TYPE, mesiteo, new MesitaType());
	}
	
	public static class MesitaType implements RecipeType<MesitaRecipe> {}
}
