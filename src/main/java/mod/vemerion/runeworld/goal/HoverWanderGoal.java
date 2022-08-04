package mod.vemerion.runeworld.goal;

import java.util.EnumSet;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.phys.Vec3;

public class HoverWanderGoal extends Goal {
	private PathfinderMob entity;

	public HoverWanderGoal(PathfinderMob entity) {
		this.entity = entity;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		return entity.getNavigation().isDone() && entity.getRandom().nextInt(4) == 0;
	}

	@Override
	public boolean canContinueToUse() {
		return entity.getNavigation().isInProgress();
	}

	@Override
	public void start() {
		Vec3 pos = wanderPos();
		if (pos != null) {
			entity.getNavigation().moveTo(entity.getNavigation().createPath(new BlockPos(pos), 1), 1);
		}

	}

	private Vec3 wanderPos() {
		Vec3 look = entity.getViewVector(0);
		Vec3 pos = AirAndWaterRandomPos.getPos(entity, 8, 4, -2, look.x, look.z, (float) Math.PI / 2);
		if (pos != null && !entity.level.getBlockState(new BlockPos(pos).above()).getMaterial().isSolid())
			pos = pos.add(0, 1, 0);
		return pos;
	}
}