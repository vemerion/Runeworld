package mod.vemerion.runeworld.entity;

import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraft.world.level.Level;

public class BloodPebbleEntity extends ThrowableItemProjectile {

	private boolean prevInWater;

	public BloodPebbleEntity(LivingEntity livingEntityIn, Level worldIn) {
		super(ModEntities.BLOOD_PEBBLE.get(), livingEntityIn, worldIn);
	}

	public BloodPebbleEntity(EntityType<? extends BloodPebbleEntity> type, Level world) {
		super(type, world);
	}

	@Override
	protected Item getDefaultItem() {
		return ModItems.BLOOD_PEBBLE.get();
	}

	@Override
	public void tick() {
		super.tick();

		if (isInWater() && !prevInWater) {
			Vec3 motion = getDeltaMovement();
			setDeltaMovement(new Vec3(motion.x, -motion.y + 0.1, motion.z));
			if (!level.isClientSide && random.nextBoolean())
				drop();
		}
		prevInWater = isInWater();
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		drop();
	}

	@Override
	protected void onHitBlock(BlockHitResult result) {
		super.onHitBlock(result);

		if (level.isClientSide) {
			for (int i = 0; i < 10; i++) {
				Vec3 particlePos = result.getLocation().add(randCoord(), randCoord(), randCoord());
				level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, getItem()), particlePos.x, particlePos.y,
						particlePos.z, 0, 0, 0);
			}
		}
	}

	private double randCoord() {
		return (random.nextDouble() - 0.5) * 0.5;
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		Entity target = result.getEntity();
		if (!level.isClientSide) {
			int damage = 4;
			if (getOwner() instanceof Player)
				damage = 1;
			target.hurt(DamageSource.thrown(this, getOwner()), damage);
		}
	}

	private void drop() {
		if (!level.isClientSide) {
			discard();
			if (random.nextDouble() < 0.9 && getOwner() instanceof Player) {
				spawnAtLocation(getItem());
			}
		}
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
