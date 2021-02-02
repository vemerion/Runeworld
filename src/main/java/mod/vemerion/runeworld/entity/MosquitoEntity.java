package mod.vemerion.runeworld.entity;

import java.util.EnumSet;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class MosquitoEntity extends CreatureEntity implements IFlyingAnimal {

	public MosquitoEntity(EntityType<? extends MosquitoEntity> type, World worldIn) {
		super(type, worldIn);
		this.moveController = new FlyingMovementController(this, 20, true);
	}

	public static AttributeModifierMap.MutableAttribute attributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 5.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D)
				.createMutableAttribute(Attributes.FLYING_SPEED, 0.4D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.4, true));
		goalSelector.addGoal(2, new WanderGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}

	@Override
	protected PathNavigator createNavigator(World world) {
		FlyingPathNavigator navigator = new FlyingPathNavigator(this, world) {
			@Override
			public boolean canEntityStandOnPos(BlockPos pos) {
				BlockState state = this.world.getBlockState(pos);
				return state.getBlock().isAir(state, world, pos) || !state.getMaterial().blocksMovement();
			}
		};
		navigator.setCanEnterDoors(false);
		navigator.setCanSwim(false);
		navigator.setCanOpenDoors(false);
		return navigator;
	}

	@Override
	protected boolean makeFlySound() {
		return true;
	}

	@Override
	public boolean onLivingFall(float fallDistance, float damageMultiplier) {
		return false;
	}

	@Override
	public boolean hasNoGravity() {
		return true;
	}

	private static class WanderGoal extends Goal {
		private MosquitoEntity mosquito;

		private WanderGoal(MosquitoEntity mosquito) {
			this.mosquito = mosquito;
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean shouldExecute() {
			return mosquito.getNavigator().noPath() && mosquito.getRNG().nextInt(4) == 0;
		}

		@Override
		public boolean shouldContinueExecuting() {
			return mosquito.getNavigator().hasPath();
		}

		@Override
		public void startExecuting() {
			Vector3d pos = wanderPos();
			if (pos != null) {
				mosquito.getNavigator().setPath(mosquito.getNavigator().getPathToPos(new BlockPos(pos), 1), 1);
			}

		}

		private Vector3d wanderPos() {
			Vector3d look = mosquito.getLook(0);
			Vector3d pos = RandomPositionGenerator.findGroundTarget(mosquito, 8, 4, -2, look, (float) Math.PI / 2);
			if (!mosquito.world.getBlockState(new BlockPos(pos).up()).getMaterial().isSolid())
				pos = pos.add(0, 1, 0);
			return pos;
		}
	}

}