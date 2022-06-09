package com.carlettos.fmapf.block;

import com.carlettos.fmapf.CarlettosMod;
import com.carlettos.fmapf.block.blockentity.MesaBlockEntity;
import com.carlettos.fmapf.listas.BlockEntities;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class MesaPalitos extends BlockWithEntity implements Waterloggable, BlockEntityProvider {
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final VoxelShape X_SHAPE = makeShapeX();
	public static final VoxelShape Z_SHAPE = makeShapeZ();
	
    public MesaPalitos() {
		super(FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE));
		this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
	}

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }
    
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
    	return this.getDefaultState().with(FACING, ctx.getPlayerFacing().rotateYClockwise()).with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }
    
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        if (direction.getAxis() == Direction.Axis.X) {
            return X_SHAPE;
        }
        return Z_SHAPE;
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public FluidState getFluidState(BlockState state) {
    	return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
    		WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    	if (state.get(WATERLOGGED)) {
    		world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    	}
    	return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
    		BlockHitResult hit) {
    	// TODO Auto-generated method stub
		if (world.getBlockEntity(pos) instanceof MesaBlockEntity mesa) {
	    	if (!world.isClient) {
				mesa.onServerClick();
				player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
		        //TODO: player.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
		        return ActionResult.CONSUME;
	        } else {
				mesa.onClientClick();
	        }
			CarlettosMod.LOGGER.info(mesa.createNbt().getInt("uses") + " <- uses");
		}
 
    	AnvilBlock.class.arrayType();
        return ActionResult.SUCCESS;
    }
    
    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
    	return super.createScreenHandlerFactory(state, world, pos);
    }
    
    public static VoxelShape makeShapeX(){
    	VoxelShape shape = VoxelShapes.empty();
    	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.125, 0, 0.4375, 0.25, 0.375, 0.5625));
    	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.75, 0, 0.4375, 0.875, 0.375, 0.5625));
    	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.375, 0.25, 0.9375, 0.4375, 0.75));

    	return shape;
    }
    
    public static VoxelShape makeShapeZ(){
    	VoxelShape shape = VoxelShapes.empty();
    	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0, 0.75, 0.5625, 0.375, 0.875));
    	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.4375, 0, 0.125, 0.5625, 0.375, 0.25));
    	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.25, 0.375, 0.0625, 0.75, 0.4375, 0.9375));

    	return shape;
    }

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new MesaBlockEntity(pos, state);
	}
	
	@Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
	
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, BlockEntities.MESITA_BLOCK_ENTITY, (world1, pos, state1, be) -> MesaBlockEntity.tick(world1, pos, state1, be));
    }
}
