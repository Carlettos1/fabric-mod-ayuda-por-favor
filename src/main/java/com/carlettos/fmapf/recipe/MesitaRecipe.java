package com.carlettos.fmapf.recipe;

import com.carlettos.fmapf.inventory.MesitaInventory;
import com.carlettos.fmapf.listas.RecipeSerializers;
import com.carlettos.fmapf.listas.RecipeTypes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class MesitaRecipe implements Recipe<MesitaInventory> {
    private final Identifier id;
	private final int stickCount;
    private final ItemStack output;
    private final DefaultedList<Ingredient> input;
	
    public MesitaRecipe(Identifier id, int stickCount, ItemStack output, DefaultedList<Ingredient> input) {
        this.id = id;
        this.stickCount = stickCount;
        this.output = output;
        this.input = input;
    }
    
	@Override
	public RecipeType<?> getType() {
		return RecipeTypes.MESITA;
	}

    @Override
    public boolean matches(MesitaInventory inventory, World world) {
        RecipeMatcher recipeMatcher = new RecipeMatcher();
        int i = 0;
        int palitosEnMesa = 0;
        for (int j = 0; j < inventory.size(); ++j) {
            ItemStack itemStack = inventory.getStack(j);
            if (itemStack.isEmpty()) continue;
            if (j < 2) {
            	palitosEnMesa++;
            	continue;
            }
            ++i;
            recipeMatcher.addInput(itemStack, 1);
        }
        return this.stickCount == palitosEnMesa && i == this.input.size() && recipeMatcher.match(this, null);
    }

	@Override
	public ItemStack craft(MesitaInventory inventory) {
		return this.output.copy();
	}

	@Override
	public boolean fits(int w, int h) {
		return true;
	}

	@Override
	public ItemStack getOutput() {
		return this.output;
	}

	@Override
	public Identifier getId() {
		return this.id;
	}
	
	public int getStickCount() {
		return stickCount;
	}

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.input;
    }

	@Override
	public RecipeSerializer<?> getSerializer() {
		return RecipeSerializers.MESITA;
	}
	
	public static class Serializer implements RecipeSerializer<MesitaRecipe> {
		
        @Override
        public MesitaRecipe read(Identifier identifier, JsonObject jsonObject) {
            int count = JsonHelper.getInt(jsonObject, "stick_count", 0);
            DefaultedList<Ingredient> defaultedList = Serializer.getIngredients(JsonHelper.getArray(jsonObject, "ingredients"));
            if (defaultedList.isEmpty()) {
                throw new JsonParseException("No ingredients for mesita recipe");
            }
            if (defaultedList.size() > 3) {
                throw new JsonParseException("Too many ingredients for mesita recipe");
            }
            ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            return new MesitaRecipe(identifier, count, itemStack, defaultedList);
        }

        @Override
        public MesitaRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            int count = packetByteBuf.readInt();
            int i = packetByteBuf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            for (int j = 0; j < defaultedList.size(); ++j) {
                defaultedList.set(j, Ingredient.fromPacket(packetByteBuf));
            }
            ItemStack itemStack = packetByteBuf.readItemStack();
            return new MesitaRecipe(identifier, count, itemStack, defaultedList);
        }

        private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
            DefaultedList<Ingredient> defaultedList = DefaultedList.of();
            for (int i = 0; i < json.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if (ingredient.isEmpty()) continue;
                defaultedList.add(ingredient);
            }
            return defaultedList;
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, MesitaRecipe recipe) {
            packetByteBuf.writeInt(recipe.getStickCount());
            packetByteBuf.writeVarInt(recipe.input.size());
            for (Ingredient ingredient : recipe.input) {
                ingredient.write(packetByteBuf);
            }
            packetByteBuf.writeItemStack(recipe.output);
        }
	}
}
