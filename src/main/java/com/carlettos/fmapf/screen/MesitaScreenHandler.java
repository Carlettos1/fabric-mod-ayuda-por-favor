package com.carlettos.fmapf.screen;

import java.util.Optional;

import com.carlettos.fmapf.inventory.MesitaInventory;
import com.carlettos.fmapf.inventory.MesitaResultInventory;
import com.carlettos.fmapf.listas.Blocks;
import com.carlettos.fmapf.listas.ItemTags;
import com.carlettos.fmapf.listas.RecipeTypes;
import com.carlettos.fmapf.listas.ScreenHandlers;
import com.carlettos.fmapf.recipe.MesitaRecipe;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class MesitaScreenHandler extends ScreenHandler {
	private final ScreenHandlerContext context;
	private final PlayerEntity player;
	private final MesitaInventory input = new MesitaInventory(this);
	private final MesitaResultInventory output = new MesitaResultInventory();

	public MesitaScreenHandler(int syncId, PlayerInventory inventory) {
		this(syncId, inventory, ScreenHandlerContext.EMPTY);
	}
	public MesitaScreenHandler(int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
		super(ScreenHandlers.MESITA, syncId);
        this.context = context;
        this.player = inventory.player;
        var startX = 8;
        var startY = 84;
        var startY_hotbar = 142;
        var largo = 18;
        this.addSlot(new MesitaResultSlot(inventory.player, this.input, this.output, 0, 134, 34)); // 0
        this.addSlot(new PalitoSlot(input, 0, 35, 48)); // 1
        this.addSlot(new PalitoSlot(input, 1, 71, 48)); // 2
        this.addSlot(new IngredientSlot(input, 2, 17, 21)); // 3
        this.addSlot(new IngredientSlot(input, 3, 53, 21)); // 4
        this.addSlot(new IngredientSlot(input, 4, 89, 21)); // 5
        for (int k = 0; k < 3; ++k) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inventory, l + k * 9 + 9, startX + l * largo, startY + k * largo)); // 6, 32
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, startX + k * largo, startY_hotbar)); // 33, 41
        }
	}
	
	@Override
	public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.input));
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return ScreenHandler.canUse(context, player, Blocks.MESA_PALITOS);
	}

    protected static void updateResult(ScreenHandler handler, World world, PlayerEntity player, MesitaInventory input, MesitaResultInventory output) {
        MesitaRecipe mesitaRecipe;
        if (world.isClient) {
            return;
        }
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
        ItemStack itemStack = ItemStack.EMPTY;
        Optional<MesitaRecipe> optional = world.getServer().getRecipeManager().getFirstMatch(RecipeTypes.MESITA, input, world);
        if (optional.isPresent()) {
            mesitaRecipe = optional.get();
            itemStack = mesitaRecipe.craft(input);
        }
        output.setStack(0, itemStack);
        handler.setPreviousTrackedSlot(0, itemStack);
        serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, itemStack));
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.context.run((world, pos) -> MesitaScreenHandler.updateResult(this, world, this.player, this.input, this.output));
    }
    
    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 0) { //resultado
                this.context.run((world, pos) -> itemStack2.getItem().onCraft(itemStack2, (World)world, player));
                if (!this.insertItem(itemStack2, 6, 42, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (index >= 6 && index < 42 ? !this.insertItem(itemStack2, 1, 6, false) && (index < 33 ? !this.insertItem(itemStack2, 33, 42, false) : !this.insertItem(itemStack2, 6, 33, false)) : !this.insertItem(itemStack2, 6, 42, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, itemStack2);
            if (index == 0) {
                player.dropItem(itemStack2, false);
            }
        }
        return itemStack;
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
    }
	
	private class IngredientSlot extends Slot {
		public IngredientSlot(Inventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}
	}
	
	private class PalitoSlot extends Slot {
		public PalitoSlot(Inventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}
		
		@Override
		public boolean canInsert(ItemStack stack) {
			return stack.isIn(ItemTags.PALITOS);
		}
	}
	
	private class MesitaResultSlot extends Slot {
		private final PlayerEntity player;
		private final MesitaInventory input;
		private int amount;
		
		public MesitaResultSlot(PlayerEntity player, MesitaInventory input, Inventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
			this.player = player;
			this.input = input;
		}
		
		@Override
		public boolean canInsert(ItemStack stack) {
			return false;
		}
		
		@Override
		public ItemStack takeStack(int amount) {
	        if (this.hasStack()) {
	            this.amount += Math.min(amount, this.getStack().getCount());
	        }
	        return super.takeStack(amount);
		}

	    @Override
	    protected void onCrafted(ItemStack stack, int amount) {
	        this.amount += amount;
	        this.onCrafted(stack);
	    }

	    @Override
	    protected void onTake(int amount) {
	        this.amount += amount;
	    }

	    @Override
	    protected void onCrafted(ItemStack stack) {
	        if (this.amount > 0) {
	            stack.onCraft(this.player.world, this.player, this.amount);
	        }
	        this.amount = 0;
	    }

	    @Override
	    public void onTakeItem(PlayerEntity player, ItemStack stack) {
	        this.onCrafted(stack);
	        DefaultedList<ItemStack> defaultedList = player.world.getRecipeManager().getRemainingStacks(RecipeTypes.MESITA, this.input, player.world);
	        for (int i = 0; i < defaultedList.size(); ++i) {
	            ItemStack itemStack = this.input.getStack(i);
	            ItemStack itemStack2 = defaultedList.get(i);
	            if (!itemStack.isEmpty()) {
	                this.input.removeStack(i, 1);
	                itemStack = this.input.getStack(i);
	            }
	            if (itemStack2.isEmpty()) continue;
	            if (itemStack.isEmpty()) {
	                this.input.setStack(i, itemStack2);
	                continue;
	            }
	            if (ItemStack.areItemsEqualIgnoreDamage(itemStack, itemStack2) && ItemStack.areNbtEqual(itemStack, itemStack2)) {
	                itemStack2.increment(itemStack.getCount());
	                this.input.setStack(i, itemStack2);
	                continue;
	            }
	            if (this.player.getInventory().insertStack(itemStack2)) continue;
	            this.player.dropItem(itemStack2, false);
	        }
	    }
	}
}
