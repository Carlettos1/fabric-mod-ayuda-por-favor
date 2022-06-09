package com.carlettos.fmapf.listas;

import com.carlettos.fmapf.CarlettosMod;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemTags {
	public static TagKey<Item> PALITOS_MAGICOS;
	
	public static void register() {
		PALITOS_MAGICOS = TagKey.of(Registry.ITEM_KEY, new Identifier(CarlettosMod.ID, "palitos_magicos"));
	}
}
