package com.carlettos.fmapf.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PalitoBlock extends Block {

	public PalitoBlock() {
		super(FabricBlockSettings.of(Material.WOOD).nonOpaque().suffocates((state, view, pos) -> false).blockVision((state, view, pos) -> false));
	}
 
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            player.sendMessage(Text.literal("Hello, world!"), false);
        }
 
        return ActionResult.SUCCESS;
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }
	
	@Override
	public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
		return true;
	}

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 1;
    }
}
