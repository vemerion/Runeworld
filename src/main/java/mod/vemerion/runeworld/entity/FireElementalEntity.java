package mod.vemerion.runeworld.entity;

import java.util.EnumSet;
import java.util.Random;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.BossEvent;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerBossEvent;

public class FireElementalEntity extends Monster {

	private static final EntityDataAccessor<Boolean> RAISING_ARMS = SynchedEntityData.defineId(FireElementalEntity.class,
			EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> SPINNING_ARMS = SynchedEntityData.defineId(FireElementalEntity.class,
			EntityDataSerializers.BOOLEAN);

	private final ServerBossEvent bossInfo = new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.YELLOW,
			BossEvent.BossBarOverlay.PROGRESS);

	private float armHeight, prevArmHeight;
	private float armRotation, prevArmRotation;

	public FireElementalEntity(EntityType<? extends FireElementalEntity> type, Level worldIn) {
		super(type, worldIn);
		this.xpReward = 20;
	}

	public static AttributeSupplier.Builder attributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 250)
				.add(Attributes.MOVEMENT_SPEED, 0.25)
				.add(Attributes.FOLLOW_RANGE, 30)
				.add(Attributes.ATTACK_DAMAGE, 6);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new ShootProjectileGoal(this));
		goalSelector.addGoal(1, new ScorchedGroundGoal(this));
		goalSelector.addGoal(2, new AoEGoal(this));
		goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1));
		goalSelector.addGoal(4, new RandomLookAroundGoal(this));
		targetSelector.addGoal(1, new HurtByTargetGoal(this));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, false, false, null));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(RAISING_ARMS, false);
		entityData.define(SPINNING_ARMS, false);
	}

	private boolean isRaisingArms() {
		return entityData.get(RAISING_ARMS);
	}

	private void setRaisingArms(boolean b) {
		entityData.set(RAISING_ARMS, b);
	}

	private boolean isSpinningArms() {
		return entityData.get(SPINNING_ARMS);
	}

	private void setSpinningArms(boolean b) {
		entityData.set(SPINNING_ARMS, b);
	}

	@Override
	public void tick() {
		super.tick();

		bossInfo.setProgress(getHealthPercent());

		if (level.isClientSide) {
			prevArmHeight = armHeight;
			if (isRaisingArms())
				armHeight = Math.min(armHeight + 0.2f, 2.5f);
			else
				armHeight = Math.max(armHeight - 0.2f, 0);

			prevArmRotation = armRotation;
			if (isSpinningArms())
				armRotation += 15;
		}
	}

	public float getHealthPercent() {
		return getHealth() / getMaxHealth();
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		bossInfo.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		bossInfo.removePlayer(player);
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
		return false;
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	public boolean ignoreExplosion() {
		return true;
	}

	@Override
	public void knockback(double strength, double ratioX, double ratioZ) {
	}

	public float getArmHeight(float partialTicks) {
		return Mth.lerp(partialTicks, prevArmHeight, armHeight);
	}

	public float getArmRotation(float partialTicks) {
		return Mth.lerp(partialTicks, prevArmRotation, armRotation);
	}

	private static class ScorchedGroundGoal extends DurationGoal {

		private static final int DURATION = 20 * 3;
		private static final int COOLDOWN = 20 * 20;

		private ScorchedGroundGoal(FireElementalEntity elemental) {
			super(elemental, COOLDOWN, DURATION);
		}

		@Override
		protected void finishAttack(FireElementalEntity elemental) {
			elemental.setRaisingArms(false);
		}

		@Override
		protected void performAttack(FireElementalEntity elemental) {
			elemental.setRaisingArms(true);
			Random rand = elemental.getRandom();
			Level world = elemental.level;

			BlockPos pos = elemental.blockPosition().offset(
					new BlockPos(Vec3.directionFromRotation(0, rand.nextFloat() * 360).scale(1 + rand.nextDouble() * 5)));

			for (int i = 0; i < 10; i++) {
				for (int j = -1; j < 2; j += 2) {
					BlockPos p = pos.below(i * j);
					if (world.isEmptyBlock(p) && world.getBlockState(p.below()).canOcclude()) {
						world.setBlockAndUpdate(p, Blocks.FIRE.defaultBlockState());
						return;
					}
				}
			}
		}
	}

	private static class ShootProjectileGoal extends Goal {

		private static final int MAX_COOLDOWN = 20 * 1;

		private FireElementalEntity elemental;
		private int cooldown;

		public ShootProjectileGoal(FireElementalEntity elemental) {
			this.elemental = elemental;
		}

		@Override
		public boolean canUse() {
			return cooldown-- < 0;
		}

		@Override
		public void start() {
			cooldown = MAX_COOLDOWN;
			Random rand = elemental.getRandom();
			Level world = elemental.level;
			LivingEntity attackTarget = elemental.getTarget();
			int side = rand.nextBoolean() ? 1 : -1;

			Vec3 offset = Vec3.directionFromRotation(0, elemental.getYRot() + 90)
					.scale(side * (rand.nextDouble() * 3 + 1.5));
			Vec3 position = elemental.position().add(offset.x, 3 + rand.nextDouble() * 3, offset.z);
			Vec3 target = Vec3.directionFromRotation(rand.nextFloat() * (-180 - 60) + 30, rand.nextFloat() * 360);
			if (attackTarget != null)
				target = attackTarget.getEyePosition(0).subtract(position);

			FireElementalProjectileEntity projectile = new FireElementalProjectileEntity(world);
			projectile.setOwner(elemental);
			projectile.setPos(position.x, position.y, position.z);
			projectile.shoot(target.x, target.y, target.z, 0.5f, 1);
			world.addFreshEntity(projectile);
		}
	}

	private static class AoEGoal extends DurationGoal {

		private static final int DURATION = 20 * 2;
		private static final int COOLDOWN = 20 * 5;

		private AoEGoal(FireElementalEntity elemental) {
			super(elemental, COOLDOWN, DURATION);
		}

		@Override
		protected void finishAttack(FireElementalEntity elemental) {
			elemental.setSpinningArms(false);
		}

		@Override
		protected void performAttack(FireElementalEntity elemental) {
			elemental.setSpinningArms(true);

			for (LivingEntity e : elemental.level.getEntitiesOfClass(LivingEntity.class,
					elemental.getBoundingBox().inflate(1).inflate(4, 0, 4), e -> e != elemental)) {
				e.hurt(DamageSource.mobAttack(elemental),
						(float) elemental.getAttributeValue(Attributes.ATTACK_DAMAGE));
			}
		}
	}

	private static abstract class DurationGoal extends Goal {

		private FireElementalEntity elemental;
		private int cooldown, duration, maxCooldown, maxDuration;

		private DurationGoal(FireElementalEntity elemental, int maxCooldown, int maxDuration) {
			this.elemental = elemental;
			this.maxDuration = maxDuration;
			this.maxCooldown = maxCooldown;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
		}

		@Override
		public boolean canUse() {
			return cooldown-- < 0;
		}

		@Override
		public void start() {
			duration = maxDuration;
		}

		@Override
		public void tick() {
			if (duration-- > 0) {
				performAttack(elemental);
			} else {
				finishAttack(elemental);
				cooldown = maxCooldown;
			}
		}

		protected abstract void finishAttack(FireElementalEntity elemental);

		protected abstract void performAttack(FireElementalEntity elemental);
	}
}
