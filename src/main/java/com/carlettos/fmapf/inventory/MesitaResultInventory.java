package com.carlettos.fmapf.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public class MesitaResultInventory implements Inventory {
    private final DefaultedList<ItemStack> stacks = DefaultedList.ofSize(1, ItemStack.EMPTY);

	@Override
	public void clear() {
		this.stacks.clear();
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
        for (ItemStack itemStack : this.stacks) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
	}

	@Override
	public ItemStack getStack(int var1) {
        return this.stacks.get(0);
	}

	@Override
	public ItemStack removeStack(int var1, int var2) {
        return Inventories.removeStack(this.stacks, 0);
	}

	@Override
	public ItemStack removeStack(int var1) {
        return Inventories.removeStack(this.stacks, 0);
	}

	@Override
	public void setStack(int var1, ItemStack stack) {
        this.stacks.set(0, stack);
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean canPlayerUse(PlayerEntity var1) {
		return true;
	}
}
