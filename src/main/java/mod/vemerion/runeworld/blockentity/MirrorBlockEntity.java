package mod.vemerion.runeworld.blockentity;

import mod.vemerion.runeworld.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MirrorBlockEntity extends BlockEntity {

	private boolean redraw = true;
	private int tickCount = 0;

	public MirrorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
		super(ModBlockEntities.MIRROR.get(), pWorldPosition, pBlockState);
	}

	public void tick() {
		if (!hasLevel())
			return;

		if (level.isClientSide) {
			tickCount++;
			if (tickCount % 2 == 0)
				redraw = true;
		}
	}

	public boolean shouldRedraw() {
		var ret = redraw;
		redraw = false;
		return ret;
	}
}
