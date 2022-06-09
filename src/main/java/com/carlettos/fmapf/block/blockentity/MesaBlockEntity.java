package com.carlettos.fmapf.block.blockentity;

import com.carlettos.fmapf.listas.BlockEntities;
import com.carlettos.fmapf.screen.MesitaScreenHandler;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MesaBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
	private int uses = 0;
	public MesaBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntities.MESITA_BLOCK_ENTITY, pos, state);
	}
	
	public void onServerClick() {
		this.uses++;
	}
	
	public void onClientClick() {
		
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		this.uses = nbt.getInt("uses");
	}
	
	@Override
	protected void writeNbt(NbtCompound nbt) {
		nbt.putInt("uses", uses);
		super.readNbt(nbt);
	}
	
    public static void tick(World world, BlockPos pos, BlockState state, MesaBlockEntity be) {
    	//be.uses++;
    }
	
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}
	
	@Override
	public NbtCompound toInitialChunkDataNbt() {
	    return createNbt();
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
		return new MesitaScreenHandler(syncId, inventory, ScreenHandlerContext.create(this.world, this.pos));
	}

	@Override
	public Text getDisplayName() {
		return new TranslatableText("fmapf.container.mesita");
	}
}
