package mod.vemerion.runeworld.entity;

import java.util.EnumSet;

import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BloodGorillaEntity extends Monster {

	private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(BloodGorillaEntity.class,
			EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> CHEST_BEATING = SynchedEntityData
			.defineId(BloodGorillaEntity.class, EntityDataSerializers.BOOLEAN);

	public BloodGorillaEntity(EntityType<? extends BloodGorillaEntity> type, Level level) {
		super(type, level);
	}

	public static AttributeSupplier.Builder attributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.MOVEMENT_SPEED, 0.25)
				.add(Attributes.FOLLOW_RANGE, 16).add(Attributes.ATTACK_DAMAGE, 3);
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public boolean shouldRiderSit() {
		return false;
	}

	@Override
	public void positionRider(Entity pPassenger) {
		if (this.hasPassenger(pPassenger)) {
			var direction = getForward().yRot(Helper.toRad(40)).scale(1.2);
			pPassenger.setPos(position().add(direction.x, 2.8, direction.z));
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (level.isClientSide) {
		}
	}

	public boolean isPushable() {
		return !isSitting();
	}

	protected void doPush(Entity pEntity) {
		if (!isSitting())
			super.doPush(pEntity);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(SITTING, true);
		entityData.define(CHEST_BEATING, false);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new SitGoal(this));
		goalSelector.addGoal(1, new ChargeGoal(this));
		goalSelector.addGoal(2, new ThrowMonkeyGoal(this));
		goalSelector.addGoal(6, new LookAtGoal(this));
		targetSelector.addGoal(1, new HurtByTargetGoal(this));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	@Override
	protected BodyRotationControl createBodyControl() {
		return new BodyRotationControl(this) {
			@Override
			public void clientTick() {
				if (!isSitting())
					super.clientTick();
			}
		};
	}

	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putBoolean("isSitting", isSitting());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		if (pCompound.contains("isSitting"))
			setSitting(pCompound.getBoolean("isSitting"));
	}

	public boolean isSitting() {
		return entityData.get(SITTING);
	}

	private void setSitting(boolean b) {
		entityData.set(SITTING, b);
	}

	public boolean isChestBeating() {
		return entityData.get(CHEST_BEATING);
	}

	private void setChestBeating(boolean b) {
		entityData.set(CHEST_BEATING, b);
	}

	@Override
	public boolean hurt(DamageSource pSource, float pAmount) {
		if (super.hurt(pSource, pAmount)) {
			setSitting(false);
			return true;
		}

		return false;
	}

	private static class LookAtGoal extends LookAtPlayerGoal {

		public LookAtGoal(Mob pMob) {
			super(pMob, Player.class, 8);
			this.lookAtContext.selector(e -> {
				return e.position().subtract(pMob.position()).normalize().dot(pMob.getForward()) > 0.6;
			});
		}

		@Override
		public boolean canContinueToUse() {
			return super.canContinueToUse() && mob.getTarget() != null && lookAtContext.test(mob, mob.getTarget());
		}

	}

	private static class ChargeGoal extends Goal {

		private static final int MAX_COOLDOWN = 20 * 9;
		private static final int CHEST_BEATING_TIME = 30;
		private static final int CHARGING_TIME = 20 * 2;

		private BloodGorillaEntity gorilla;
		private int cooldown;
		private int duration;

		private ChargeGoal(BloodGorillaEntity gorilla) {
			this.gorilla = gorilla;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public boolean canUse() {
			return cooldown++ > MAX_COOLDOWN && gorilla.getTarget() != null;
		}

		@Override
		public void start() {
			gorilla.setChestBeating(true);
		}

		@Override
		public void tick() {
			duration++;
			if (duration == CHEST_BEATING_TIME) {
				var target = gorilla.getTarget();
				if (target == null)
					return;

				var direction = target.position().subtract(gorilla.position()).normalize();
				var targetPos = target.position().add(direction.scale(2));
				gorilla.getNavigation().moveTo(targetPos.x, targetPos.y, targetPos.z, 2.5);
				gorilla.setChestBeating(false);
			} else if (duration > CHEST_BEATING_TIME && duration <= CHEST_BEATING_TIME + CHARGING_TIME) {
				var direction = Vec3.directionFromRotation(0, gorilla.getYRot()).reverse();
				for (var e : gorilla.level.getEntitiesOfClass(LivingEntity.class, gorilla.getBoundingBox().inflate(0.5),
						e -> e != gorilla)) {
					e.knockback(5, direction.x, direction.z);
					e.hurt(DamageSource.mobAttack(gorilla).bypassArmor(), 8);
				}
			} else if (duration > CHEST_BEATING_TIME + CHARGING_TIME) {
				cooldown = 0;
			}
		}

		@Override
		public void stop() {
			duration = 0;
			gorilla.setChestBeating(false);
		}
	}

	private static class SitGoal extends Goal {

		private BloodGorillaEntity gorilla;

		private SitGoal(BloodGorillaEntity gorilla) {
			this.gorilla = gorilla;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
		}

		@Override
		public boolean canUse() {
			return gorilla.isSitting();
		}

		@Override
		public void start() {
			gorilla.getNavigation().stop();
		}
	}

	private static class ThrowMonkeyGoal extends Goal {

		private static final int MAX_EQUIP_COOLDOWN = 20 * 5;
		private static final int MAX_THROW_COOLDOWN = 20 * 2;

		private BloodGorillaEntity gorilla;
		private int cooldown;

		private ThrowMonkeyGoal(BloodGorillaEntity gorilla) {
			this.gorilla = gorilla;
			this.setFlags(EnumSet.of(Goal.Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			var target = gorilla.getTarget();
			return target != null && target.isAlive();
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public void stop() {
			cooldown = 0;
			gorilla.getPassengers().forEach(p -> {
				if (p instanceof BloodMonkeyEntity) {
					p.stopRiding();
					p.discard();
				}
			});
		}

		@Override
		public void tick() {
			if (gorilla.getPassengers().isEmpty()) {
				if (cooldown++ == MAX_EQUIP_COOLDOWN) {
					cooldown = 0;
					var monkey = new BloodMonkeyEntity(ModEntities.BLOOD_MONKEY.get(), gorilla.level);
					if (gorilla.canAddPassenger(monkey)) {
						monkey.setPos(gorilla.position());
						monkey.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(
								gorilla.random.nextDouble() < 0.2 ? Items.DIAMOND_SWORD : Items.IRON_SWORD));
						gorilla.level.addFreshEntity(monkey);
						monkey.startRiding(gorilla);
					}
				}
			} else {
				var passenger = gorilla.getFirstPassenger();
				var target = gorilla.getTarget();
				if (cooldown++ == MAX_THROW_COOLDOWN && target != null) {
					cooldown = 0;
					var direction = target.position().subtract(gorilla.position()).normalize();
					passenger.stopRiding();
					passenger.setDeltaMovement(direction);
				}
			}
		}

	}
}
