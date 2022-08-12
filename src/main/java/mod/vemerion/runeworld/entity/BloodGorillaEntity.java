package mod.vemerion.runeworld.entity;

import java.util.EnumSet;

import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class BloodGorillaEntity extends Monster {

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
	protected void registerGoals() {
		goalSelector.addGoal(0, new ThrowMonkeyGoal(this));
		goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		targetSelector.addGoal(1, new HurtByTargetGoal(this));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
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
