package mod.vemerion.runeworld.entity;

import mod.vemerion.runeworld.block.BloodPillarBlock;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BloodMonkeyEntity extends Monster implements RangedAttackMob {

	private static final double TICK_CHANCE = 0.1;
	private static final int MAX_PASSENGER_COUNT = 3;
	private static final RiderPos[] RIDER_POSITIONS = new RiderPos[] { new RiderPos(20, 90, 0.2f, -110, 70),
			new RiderPos(-20, -90, 0.15f, 90, 90), new RiderPos(-30, 180, 0.3f, -90, 0) };

	private float bodyRot, prevBodyRot;

	public BloodMonkeyEntity(EntityType<? extends BloodMonkeyEntity> type, Level worldIn) {
		super(type, worldIn);
		this.xpReward = 5;
		this.setDropChance(EquipmentSlot.MAINHAND, 0);
	}

	public static AttributeSupplier.Builder attributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.MOVEMENT_SPEED, 0.25)
				.add(Attributes.FOLLOW_RANGE, 16).add(Attributes.ATTACK_DAMAGE, 3);
	}

	public static void spawn(ServerLevel level, Vec3 pos, MobSpawnType reason) {
		var monkey = new BloodMonkeyEntity(ModEntities.BLOOD_MONKEY.get(), level);
		monkey.setPos(pos);
		monkey.finalizeSpawn(level, level.getCurrentDifficultyAt(monkey.blockPosition()), reason, null, null);
		level.addFreshEntityWithPassengers(monkey);
	}

	@Override
	public boolean shouldRiderSit() {
		return false;
	}

	@Override
	protected boolean canAddPassenger(Entity pPassenger) {
		return getPassengers().size() < MAX_PASSENGER_COUNT && pPassenger instanceof TickEntity;
	}

	@Override
	public void positionRider(Entity pPassenger) {
		if (hasPassenger(pPassenger)) {

			var riderPos = RIDER_POSITIONS[getPassengers().indexOf(pPassenger)];
			var dir = Vec3.directionFromRotation(0, getYRot()).xRot(Helper.toRad(riderPos.posPitch))
					.yRot(Helper.toRad(riderPos.posYaw)).normalize().scale(riderPos.magnitude);
			pPassenger.setPos(position().add(0, 0.5, 0).add(dir));

			pPassenger.setYBodyRot(riderPos.yaw + getYRot());
			pPassenger.setYRot(riderPos.yaw + getYRot());
			pPassenger.setXRot(riderPos.pitch);
		}
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
			MobSpawnType pReason, SpawnGroupData pSpawnData, CompoundTag pDataTag) {

		if (pReason != MobSpawnType.STRUCTURE && random.nextDouble() < 0.1) {
			setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
		}

		var tickChance = TICK_CHANCE;
		if (pReason == MobSpawnType.STRUCTURE)
			tickChance *= 5;

		if (random.nextDouble() < tickChance) {
			for (int i = 0; i <= random.nextInt(MAX_PASSENGER_COUNT); i++) {
				var tick = new TickEntity(ModEntities.TICK.get(), level);
				tick.setPos(position());
				tick.startRiding(this);
			}
		}

		return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.MONKEY_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSounds.MONKEY_AMBIENT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.MONKEY_DEATH.get();
	}

	@Override
	public void tick() {
		super.tick();

		prevBodyRot = bodyRot;
		if (canUseRangedAttack())
			bodyRot = Mth.lerp(0.1f, bodyRot, Helper.toRad(15));
		else
			bodyRot = Mth.lerp(0.1f, bodyRot, Helper.toRad(70));
	}

	@Override
	public boolean hurt(DamageSource pSource, float pAmount) {
		if (super.hurt(pSource, pAmount)) {
			for (var p : getPassengers())
				if (p instanceof TickEntity tick)
					tick.incGrowth(1);
			return true;
		}
		return false;
	}

	public float getBodyRot(float partialTicks) {
		return Mth.lerp(partialTicks, prevBodyRot, bodyRot);
	}

	protected PathNavigation createNavigation(Level worldIn) {
		return new WallClimberNavigation(this, worldIn);
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource pSource) {
		return false;
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new RangedAttackGoal(this, 0, 45, 10) {
			@Override
			public boolean canUse() {
				return super.canUse() && canUseRangedAttack() && !isPassenger();
			}
		});
		goalSelector.addGoal(0, new RangedAttackGoal(this, 0, 10, 10) {
			@Override
			public boolean canUse() {
				return super.canUse() && canUseRangedAttack() && isPassenger();
			}
		});
		goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5, true) {
			@Override
			public boolean canUse() {
				return super.canUse() && !isStandingOnPillar();
			}
		});
		goalSelector.addGoal(2, new ClimbPillarGoal(this, 1, 10));
		goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8) {
			@Override
			public boolean canUse() {
				return super.canUse() && !isStandingOnPillar();
			}
		});
		goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	@Override
	public boolean onClimbable() {
		return super.onClimbable() || horizontalCollision;
	}

	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		swing(InteractionHand.MAIN_HAND);
		ThrowableItemProjectile projectile = random.nextDouble() < 0.1 ? new MosquitoEggsEntity(this, level)
				: new SlingshotProjectileEntity(this, level).setBreakChance(1).setDamage(4).setReturnChance(0);
		double x = target.getX() - getX();
		double y = target.getEyeY() - 1.1f - projectile.getY();
		double z = target.getZ() - getZ();
		double height = Math.sqrt(x * x + z * z) * 0.2;
		projectile.shoot(x, y + height, z, 1f, 1f);
		level.addFreshEntity(projectile);
		playSound(ModSounds.THROWING.get(), 1, Helper.soundPitch(random));
	}

	public boolean canUseRangedAttack() {
		return isStandingOnPillar() || getVehicle() instanceof BloodGorillaEntity;
	}

	public boolean isStandingOnPillar() {
		float halfWidth = getBbWidth() * 0.51f;
		return BlockPos
				.betweenClosedStream(new AABB(position().subtract(halfWidth, 0.5, halfWidth),
						position().add(halfWidth, -0.1, halfWidth)))
				.anyMatch(p -> BloodPillarBlock.isPillar(level, p) && !BloodPillarBlock.isPillar(level, p.above()));
	}

	private static class ClimbPillarGoal extends MoveToBlockGoal {

		private BloodMonkeyEntity monkey;

		private ClimbPillarGoal(BloodMonkeyEntity monkey, double speedIn, int length) {
			super(monkey, speedIn, length);
			this.monkey = monkey;
		}

		@Override
		protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
			return BloodPillarBlock.isPillar(worldIn, pos);
		}

		@Override
		public boolean canUse() {
			return super.canUse() && !monkey.isStandingOnPillar() && monkey.getMainHandItem().isEmpty();
		}

	}

	private static record RiderPos(float posPitch, float posYaw, float magnitude, float pitch, float yaw) {

	}

//	private static class PillarJumpGoal extends Goal {
//
//		private static final int MIN_JUMP_COOLDOWN = 20 * 10;
//		private static final double JUMP_ANGLE = Math.toRadians(65);
//
//		private BloodMonkeyEntity monkey;
//		private int jumpCooldown;
//
//		public PillarJumpGoal(BloodMonkeyEntity monkey) {
//			this.monkey = monkey;
//			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
//		}
//
//		@Override
//		public boolean shouldExecute() {
//			return !getNearbyPillars().isEmpty() && monkey.getRNG().nextInt(2) == 1;
//		}
//
//		private List<BlockPos> getNearbyPillars() {
//			List<BlockPos> pillars = new ArrayList<>();
//			World world = monkey.world;
//			BlockPos.getAllInBox(monkey.getBoundingBox().grow(5)).forEach(pos -> {
//				if (BloodPillarBlock.isPillar(world, pos) && !pos.up().equals(monkey.getPosition())
//						&& world.isAirBlock(pos.up()) && world.isAirBlock(pos.up(2)))
//					pillars.add(pos.toImmutable());
//			});
//			return pillars;
//		}
//
//		@Override
//		public void tick() {
//			if (jumpCooldown-- < 0) {
//				jumpCooldown = 60;
//
//				List<BlockPos> pillars = getNearbyPillars();
//				Vector3d distance = Vector3d
//						.copyCenteredHorizontally(pillars.get(monkey.getRNG().nextInt(pillars.size())).up())
//						.subtract(monkey.getPositionVec());
//				double horizontalDistance = length(distance.x, distance.z);
//				double angle = calcAngle(horizontalDistance, distance.y);
//				double velocity = calcVelocity(horizontalDistance, distance.y, angle);
//				if (!Double.isFinite(angle) || !Double.isFinite(velocity))
//					System.out.println("PROBLEMS");
//				Vector3d horizontalDir = new Vector3d(distance.x, 0, distance.z).normalize();
//				Vector3d motion = new Vector3d(horizontalDir.x * Math.cos(angle), Math.sin(angle),
//						horizontalDir.z * Math.cos(angle)).scale(velocity);
//				monkey.setMotion(motion);
//			}
//		}
//
//		private double calcVelocity(double x, double y, double angle) {
//			double g = monkey.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).getValue();
//			return Math.sqrt(x * x * g / (x * Math.sin(2 * angle) - 2 * y * Math.pow(Math.cos(angle), 2)));
//		}
//
//		private double calcAngle(double x, double y) {
//			return Math.PI / 2 - (Math.PI / 2 - Math.atan2(y, x)) / 2;
//		}
//
//		private double length(double x, double z) {
//			return Math.sqrt(x * x + z + z);
//		}
//
//	}
}
