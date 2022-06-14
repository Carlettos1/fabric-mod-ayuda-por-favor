package com.carlettos.fmapf.listas;

import com.carlettos.fmapf.CarlettosMod;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemTags {
	public static TagKey<Item> PALITOS;
	public static TagKey<Item> MAGIC_ESSENCE;
	public static TagKey<Item> MINERAL_ESSENCE;
	public static TagKey<Item> PALITO_ESSENCE;
	
	public static void register() {
		PALITOS = TagKey.of(Registry.ITEM_KEY, new Identifier(CarlettosMod.ID, "palitos"));
		MAGIC_ESSENCE = TagKey.of(Registry.ITEM_KEY, new Identifier(CarlettosMod.ID, "magic_essence"));
		MINERAL_ESSENCE = TagKey.of(Registry.ITEM_KEY, new Identifier(CarlettosMod.ID, "mineral_essence"));
		PALITO_ESSENCE = TagKey.of(Registry.ITEM_KEY, new Identifier(CarlettosMod.ID, "palito_essence"));
	}
}
