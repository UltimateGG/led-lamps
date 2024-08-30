package me.ultimate.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;


public class LampBlockEntity extends BlockEntity {
	private final DyeColor color;
	private boolean lit;

	@Override
	public boolean onSyncedBlockEvent(int type, int data) {
		if (type == 1) {
			this.lit = data == 1;
			return true;
		}

		return super.onSyncedBlockEvent(type, data);
	}

	public LampBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.LAMP_BLOCK_ENTITY, pos, state);

		LampBlock b = (LampBlock) state.getBlock();
		this.color = b.getColor();
		this.lit = state.get(LampBlock.LIT);
	}

	public DyeColor getColor() {
		return color;
	}

	public boolean isLit() {
		return lit;
	}
}
