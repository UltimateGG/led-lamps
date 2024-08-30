package me.ultimate.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.block.Blocks.createLightLevelFromLitBlockState;


public class LampBlock extends BlockWithEntity {
	public static final BooleanProperty LIT = RedstoneLampBlock.LIT;
	private final DyeColor color;

	public LampBlock(DyeColor color) {
		super(AbstractBlock.Settings.create().luminance(createLightLevelFromLitBlockState(15)).sounds(BlockSoundGroup.STONE).mapColor(color).strength(0.5F));
		this.setDefaultState(this.getDefaultState().with(LIT, false));
		this.color = color;
	}

	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(LIT, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
	}

	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
		if (!world.isClient) {
			boolean bl = state.get(LIT);
			if (bl != world.isReceivingRedstonePower(pos)) {
				if (bl) {
					world.scheduleBlockTick(pos, this, 4);
				} else {
					world.setBlockState(pos, state.cycle(LIT), 2);
					this.sync(state, world, pos);
				}
			}
		}
	}

	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (state.get(LIT) && !world.isReceivingRedstonePower(pos)) {
			world.setBlockState(pos, state.cycle(LIT), 2);
			this.sync(state, world, pos);
		}
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}

	public DyeColor getColor() {
		return color;
	}

	private void sync(BlockState state, World world, BlockPos pos) {
		world.addSyncedBlockEvent(pos, state.getBlock(), 1, state.get(LIT) ? 0 : 1);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new LampBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
}
