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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.NetworkHooks;
import net.minecraft.world.level.Level;

public class BloodPebbleEntity extends ThrowableItemProjectile {

	private boolean prevInWater;
	private float breakChance = 0.5f;
	private float returnChance = 0;
	private int damage = 1;

	public BloodPebbleEntity(LivingEntity livingEntityIn, Level worldIn) {
		super(ModEntities.BLOOD_PEBBLE.get(), livingEntityIn, worldIn);
	}

	public BloodPebbleEntity(EntityType<? extends BloodPebbleEntity> type, Level world) {
		super(type, world);
	}

	public BloodPebbleEntity setDamage(int damage) {
		this.damage = damage;
		return this;
	}

	public BloodPebbleEntity setBreakChance(float breakChance) {
		this.breakChance = breakChance;
		return this;
	}

	public BloodPebbleEntity setReturnChance(float returnChance) {
		this.returnChance = returnChance;
		return this;
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
			target.hurt(DamageSource.thrown(this, getOwner()), damage);
		}
	}

	private void drop() {
		if (!level.isClientSide) {
			discard();
			if (random.nextDouble() > breakChance) {
				if (random.nextDouble() < returnChance && getOwner() instanceof Player player) {
					ItemHandlerHelper.giveItemToPlayer(player, getItem());
				} else {
					spawnAtLocation(getItem());
				}
			}
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putFloat("breakChance", breakChance);
		pCompound.putFloat("returnChance", returnChance);
		pCompound.putInt("damage", damage);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		if (pCompound.contains("breakChance"))
			breakChance = pCompound.getFloat("breakChance");
		if (pCompound.contains("returnChance"))
			returnChance = pCompound.getFloat("returnChance");
		if (pCompound.contains("damage"))
			damage = pCompound.getInt("damage");

	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
