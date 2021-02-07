package mod.vemerion.runeworld.entity;

import java.util.EnumSet;

import mod.vemerion.runeworld.goal.HoverWanderGoal;
import mod.vemerion.runeworld.init.ModEffects;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BloodBatEntity extends CreatureEntity implements IFlyingAnimal {

	private static final DataParameter<Boolean> HANGING = EntityDataManager.createKey(BloodBatEntity.class,
			DataSerializers.BOOLEAN);

	private Vector3d hangingPos;

	public BloodBatEntity(EntityType<? extends BloodBatEntity> type, World worldIn) {
		super(type, worldIn);
		this.moveController = new FlyingMovementController(this, 20, true);
		this.experienceValue = 5;
	}

	public static AttributeModifierMap.MutableAttribute attributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 24)
				.createMutableAttribute(Attributes.FLYING_SPEED, 0.4D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 3);
	}

	@Override
	public void tick() {
		super.tick();
		updateArmSwingProgress();
		if (isHanging() && !world.isRemote) {
			setPosition(hangingPos.x, hangingPos.y, hangingPos.z);

			if (!world.getBlockState(getPosition().up(2)).isSolid())
				stopHanging();
		}
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
	protected void registerGoals() {
		goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.65, true) {
			@Override
			public boolean shouldExecute() {
				return super.shouldExecute() && !isHanging();
			}
		});
		goalSelector.addGoal(2, new FindLedgeGoal(this));
		goalSelector.addGoal(3, new HoverWanderGoal(this) {
			@Override
			public boolean shouldExecute() {
				return super.shouldExecute() && !isHanging();
			}
		});
		goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6.0F) {
			@Override
			public boolean shouldExecute() {
				return super.shouldExecute() && !isHanging();
			}
		});
		goalSelector.addGoal(5, new LookRandomlyGoal(this) {
			@Override
			public boolean shouldExecute() {
				return super.shouldExecute() && !isHanging();
			}
		});
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<PlayerEntity>(this, PlayerEntity.class, true) {
			@Override
			public boolean shouldExecute() {
				return super.shouldExecute() && !isHanging();
			}
		});
	}

	@Override
	public boolean canDespawn(double distanceToClosestPlayer) {
		return false;
	}
	
	@Override
	protected boolean isDespawnPeaceful() {
		return true;
	}

	@Override
	protected void registerData() {
		super.registerData();
		dataManager.register(HANGING, false);
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("hanging") && compound.getBoolean("hanging")) {
			startHanging(new Vector3d(compound.getDouble("hangingX"), compound.getDouble("hangingY"),
					compound.getDouble("hangingZ")));
		}
	}

	@Override
	protected Vector3d handlePistonMovement(Vector3d pos) {
		if (isHanging())
			stopHanging();
		return super.handlePistonMovement(pos);
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		if (isHanging()) {
			compound.putDouble("hangingX", hangingPos.x);
			compound.putDouble("hangingY", hangingPos.y);
			compound.putDouble("hangingZ", hangingPos.z);
			compound.putBoolean("hanging", true);
		} else {
			compound.putBoolean("hanging", false);
		}
	}

	public boolean isHanging() {
		return dataManager.get(HANGING);
	}

	public void startHanging(Vector3d pos) {
		hangingPos = pos;
		dataManager.set(HANGING, true);
	}

	public void stopHanging() {
		dataManager.set(HANGING, false);
	}

	public float getAnimationHeight(float partialTicks) {
		return MathHelper.cos((ticksExisted + partialTicks) / 5);
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		if (super.attackEntityAsMob(entityIn)) {
			if (rand.nextDouble() < 0.15 && entityIn instanceof PlayerEntity)
				((PlayerEntity) entityIn).addPotionEffect(new EffectInstance(ModEffects.BLOOD_DRAINED, 20 * 60));
			return true;
		}
		return false;
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

	@Override
	protected boolean canTriggerWalking() {
		return !isHanging();
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (super.attackEntityFrom(source, amount)) {
			if (isHanging()) {
				stopHanging();
				if (source.getTrueSource() instanceof PlayerEntity)
					setAttackTarget((PlayerEntity) source.getTrueSource());
			}
			return true;
		}
		return false;
	}

	public static boolean isValidLedgePos(IWorld world, BlockPos pos, BloodBatEntity bat) {
		return pos != null && world.getBlockState(pos).isSolid() && world.isAirBlock(pos.down())
				&& world.isAirBlock(pos.down(2))
				&& world.getEntitiesWithinAABBExcludingEntity(bat, new AxisAlignedBB(pos).expand(0, -2, 0)).isEmpty();
	}

	private static class FindLedgeGoal extends Goal {
		private BloodBatEntity bat;
		private BlockPos ledgePos;

		private FindLedgeGoal(BloodBatEntity mosquito) {
			this.bat = mosquito;
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean shouldExecute() {
			return bat.getNavigator().noPath() && bat.getRNG().nextInt(2) == 0 && !bat.isHanging();
		}

		@Override
		public boolean shouldContinueExecuting() {
			return bat.getNavigator().hasPath();
		}

		@Override
		public void startExecuting() {
			updateLedgePos();
		}

		@Override
		public void tick() {
			if (!isValidLedgePos(bat.world, ledgePos, bat))
				updateLedgePos();

			if (ledgePos != null) {
				Vector3d target = Vector3d.copyCenteredHorizontally(ledgePos).add(0, -1.75, 0);
				if (target.squareDistanceTo(bat.getPositionVec()) < 3) {
					bat.getNavigator().setPath(null, 0);
					bat.setPosition(target.x, target.y, target.z);
					bat.startHanging(target);
				}
			}
		}

		private BlockPos ledgePos() {
			World world = bat.world;
			BlockPos batPos = bat.getPosition();
			for (int x = -6; x < 7; x++) {
				for (int y = -6; y < 7; y++) {
					for (int z = -6; z < 7; z++) {
						BlockPos pos = batPos.add(x, y, z);
						if (isValidLedgePos(world, pos, bat))
							return pos;
					}
				}
			}
			return null;
		}

		private void updateLedgePos() {
			BlockPos pos = ledgePos();
			if (pos != null) {
				ledgePos = pos;
				bat.getNavigator().setPath(bat.getNavigator().getPathToPos(pos.down(2), 1), 1);
			}
		}
	}
}
