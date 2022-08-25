package mod.vemerion.runeworld.entity;

import java.util.EnumSet;

import mod.vemerion.runeworld.goal.HoverWanderGoal;
import mod.vemerion.runeworld.init.ModEffects;
import mod.vemerion.runeworld.init.ModParticles;
import mod.vemerion.runeworld.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BloodBatEntity extends PathfinderMob implements FlyingAnimal {

	private static final EntityDataAccessor<Boolean> HANGING = SynchedEntityData.defineId(BloodBatEntity.class,
			EntityDataSerializers.BOOLEAN);

	private Vec3 hangingPos;

	public BloodBatEntity(EntityType<? extends BloodBatEntity> type, Level worldIn) {
		super(type, worldIn);
		this.moveControl = new FlyingMoveControl(this, 20, true);
		this.xpReward = 5;
	}

	public static AttributeSupplier.Builder attributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.MOVEMENT_SPEED, 0.2D)
				.add(Attributes.FOLLOW_RANGE, 24).add(Attributes.FLYING_SPEED, 0.4D).add(Attributes.ATTACK_DAMAGE, 3);
	}

	@Override
	public void tick() {
		super.tick();
		updateSwingTime();
		if (isHanging() && !level.isClientSide) {
			setPos(hangingPos.x, hangingPos.y, hangingPos.z);

			if (!level.getBlockState(blockPosition().above(2)).canOcclude())
				stopHanging();
		}

		dripBlood();
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return isHanging() ? ModSounds.BLOOD_BAT_SNORING.get() : ModSounds.BLOOD_BAT_WINGS.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.BLOOD_BAT_DEATH.get();
	}

	@Override
	public int getAmbientSoundInterval() {
		return isHanging() ? 80 : 20;
	}

	private void dripBlood() {
		if (!level.isClientSide || !isHanging())
			return;

		if (getRandom().nextDouble() < 0.02) {
			Vec3 offset = new Vec3((getRandom().nextDouble() - 0.5) * 0.2, 0.15,
					(getRandom().nextDouble() - 0.5) * 0.2);
			Vec3 pos = position().add(Vec3.directionFromRotation(0, getYRot()).scale(0.7)).add(offset);
			level.addParticle(ModParticles.DRIPPING_BLOOD.get(), pos.x, pos.y, pos.z, 0, 0, 0);
		}
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		FlyingPathNavigation navigator = new FlyingPathNavigation(this, world) {
			@Override
			public boolean isStableDestination(BlockPos pos) {
				BlockState state = this.level.getBlockState(pos);
				return state.isAir() || !state.getMaterial().blocksMotion();
			}
		};
		navigator.setCanPassDoors(false);
		navigator.setCanFloat(false);
		navigator.setCanOpenDoors(false);
		return navigator;
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.65, true) {
			@Override
			public boolean canUse() {
				return super.canUse() && !isHanging();
			}
		});
		goalSelector.addGoal(2, new FindLedgeGoal(this));
		goalSelector.addGoal(3, new HoverWanderGoal(this) {
			@Override
			public boolean canUse() {
				return super.canUse() && !isHanging();
			}
		});
		goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F) {
			@Override
			public boolean canUse() {
				return super.canUse() && !isHanging();
			}
		});
		goalSelector.addGoal(5, new RandomLookAroundGoal(this) {
			@Override
			public boolean canUse() {
				return super.canUse() && !isHanging();
			}
		});
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<Player>(this, Player.class, true) {
			@Override
			public boolean canUse() {
				return super.canUse() && !isHanging();
			}
		});
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	protected boolean shouldDespawnInPeaceful() {
		return true;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(HANGING, false);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("hanging") && compound.getBoolean("hanging")) {
			startHanging(new Vec3(compound.getDouble("hangingX"), compound.getDouble("hangingY"),
					compound.getDouble("hangingZ")));
		}
	}

	@Override
	protected Vec3 limitPistonMovement(Vec3 pos) {
		if (isHanging())
			stopHanging();
		return super.limitPistonMovement(pos);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
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
		return entityData.get(HANGING);
	}

	public void startHanging(Vec3 pos) {
		hangingPos = pos;
		entityData.set(HANGING, true);
	}

	public void stopHanging() {
		entityData.set(HANGING, false);
	}

	public float getAnimationHeight(float partialTicks) {
		return Mth.cos((tickCount + partialTicks) / 5);
	}

	@Override
	public boolean doHurtTarget(Entity entityIn) {
		if (super.doHurtTarget(entityIn)) {
			if (random.nextDouble() < 0.15 && entityIn instanceof Player)
				((Player) entityIn).addEffect(new MobEffectInstance(ModEffects.BLOOD_DRAINED.get(), 20 * 60));
			return true;
		}
		return false;
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource pSource) {
		return false;
	}

	@Override
	public boolean isNoGravity() {
		return true;
	}

	@Override
	protected MovementEmission getMovementEmission() {
		return isHanging() ? MovementEmission.NONE : MovementEmission.ALL;
	}

	@Override
	protected boolean isFlapping() {
		return isFlying() && tickCount % 20 == 0;
	}

	@Override
	public boolean isFlying() {
		return !this.onGround;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (super.hurt(source, amount)) {
			if (isHanging()) {
				stopHanging();
				if (source.getEntity() instanceof Player)
					setTarget((Player) source.getEntity());
			}
			return true;
		}
		return false;
	}

	public static boolean isValidLedgePos(LevelAccessor world, BlockPos pos, BloodBatEntity bat) {
		return pos != null && world.getBlockState(pos).canOcclude() && world.isEmptyBlock(pos.below())
				&& world.isEmptyBlock(pos.below(2))
				&& world.getEntities(bat, new AABB(pos).expandTowards(0, -2, 0)).isEmpty();
	}

	private static class FindLedgeGoal extends Goal {
		private BloodBatEntity bat;
		private BlockPos ledgePos;

		private FindLedgeGoal(BloodBatEntity mosquito) {
			this.bat = mosquito;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			return bat.getNavigation().isDone() && bat.getRandom().nextInt(2) == 0 && !bat.isHanging();
		}

		@Override
		public boolean canContinueToUse() {
			return bat.getNavigation().isInProgress();
		}

		@Override
		public void start() {
			updateLedgePos();
		}

		@Override
		public void tick() {
			if (!isValidLedgePos(bat.level, ledgePos, bat))
				updateLedgePos();

			if (ledgePos != null) {
				Vec3 target = Vec3.atBottomCenterOf(ledgePos).add(0, -1.75, 0);
				if (target.distanceToSqr(bat.position()) < 3) {
					bat.getNavigation().stop();
					bat.setPos(target.x, target.y, target.z);
					bat.startHanging(target);
				}
			}
		}

		private BlockPos ledgePos() {
			Level world = bat.level;
			BlockPos batPos = bat.blockPosition();
			for (int x = -6; x < 7; x++) {
				for (int y = -6; y < 7; y++) {
					for (int z = -6; z < 7; z++) {
						BlockPos pos = batPos.offset(x, y, z);
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
				bat.getNavigation().moveTo(bat.getNavigation().createPath(pos.below(2), 1), 1);
			}
		}
	}
}
