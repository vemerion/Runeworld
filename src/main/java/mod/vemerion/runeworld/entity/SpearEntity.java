package mod.vemerion.runeworld.entity;

import mod.vemerion.runeworld.init.ModEntities;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

public class SpearEntity extends AbstractArrow {

	public SpearEntity(EntityType<? extends SpearEntity> entityTypeIn, Level level) {
		super(entityTypeIn, level);
		this.pickup = Pickup.DISALLOWED;
	}

	public SpearEntity(double x, double y, double z, Level level) {
		super(ModEntities.SPEAR.get(), x, y, z, level);
		this.pickup = Pickup.DISALLOWED;
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		if (!level.isClientSide) {
			var target = result.getEntity();

			if (target instanceof BloodKnightEntity)
				return;

			target.hurt(DamageSource.thrown(this, getOwner()), (float) getBaseDamage());

			if (target instanceof LivingEntity living) {
				var direction = getDeltaMovement().multiply(1, 0, 1).normalize().scale(2);
				living.push(direction.x, 0.1, direction.z);
			}
			discard();
		}
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
