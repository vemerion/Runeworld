package mod.vemerion.runeworld.goal;

import java.util.EnumSet;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class HoverWanderGoal extends Goal {
	private CreatureEntity entity;

	public HoverWanderGoal(CreatureEntity entity) {
		this.entity = entity;
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean shouldExecute() {
		return entity.getNavigator().noPath() && entity.getRNG().nextInt(4) == 0;
	}

	@Override
	public boolean shouldContinueExecuting() {
		return entity.getNavigator().hasPath();
	}

	@Override
	public void startExecuting() {
		Vector3d pos = wanderPos();
		if (pos != null) {
			entity.getNavigator().setPath(entity.getNavigator().getPathToPos(new BlockPos(pos), 1), 1);
		}

	}

	private Vector3d wanderPos() {
		Vector3d look = entity.getLook(0);
		Vector3d pos = RandomPositionGenerator.findGroundTarget(entity, 8, 4, -2, look, (float) Math.PI / 2);
		if (pos != null && !entity.world.getBlockState(new BlockPos(pos).up()).getMaterial().isSolid())
			pos = pos.add(0, 1, 0);
		return pos;
	}
}