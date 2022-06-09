package com.carlettos.fmapf.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;

public class MesitaInventory implements Inventory { //RecipeInputProvider
    private final DefaultedList<ItemStack> stacks;
    private final ScreenHandler handler;
	
	public MesitaInventory(ScreenHandler handler) {
		this.stacks = DefaultedList.ofSize(5, ItemStack.EMPTY);
		this.handler = handler;
	}
	
	@Override
	public boolean isValid(int slot, ItemStack stack) {
		return Inventory.super.isValid(slot, stack);
	}

	@Override
	public void clear() {
		this.stacks.clear();
	}

	@Override
	public int size() {
		return this.stacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemStack : stacks) {
			if (!itemStack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStack(int slot) {
        if (slot >= this.size()) {
            return ItemStack.EMPTY;
        }
		return this.stacks.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.stacks, slot, amount);
        if (!itemStack.isEmpty()) {
            this.handler.onContentChanged(this);
        }
        return itemStack;
	}

	@Override
	public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.stacks, slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
        this.stacks.set(slot, stack);
        this.handler.onContentChanged(this);
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean canPlayerUse(PlayerEntity var1) {
		return true;
	}

	/*
	    @Override
	    public void provideRecipeInputs(RecipeMatcher finder) {
	        for (ItemStack itemStack : this.stacks) {
	            finder.addUnenchantedInput(itemStack);
	        }
	    }
	 */
}
