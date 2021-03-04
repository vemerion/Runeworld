package mod.vemerion.runeworld.entity;

import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BloodPebbleEntity extends ProjectileItemEntity {

	private boolean prevInWater;

	public BloodPebbleEntity(LivingEntity livingEntityIn, World worldIn) {
		super(ModEntities.BLOOD_PEBBLE, livingEntityIn, worldIn);
	}

	public BloodPebbleEntity(EntityType<? extends BloodPebbleEntity> type, World world) {
		super(type, world);
	}

	@Override
	protected Item getDefaultItem() {
		return ModItems.BLOOD_PEBBLE;
	}

	@Override
	public void tick() {
		super.tick();

		if (isInWater() && !prevInWater) {
			Vector3d motion = getMotion();
			setMotion(new Vector3d(motion.x, -motion.y + 0.1, motion.z));
			if (!world.isRemote && rand.nextBoolean())
				drop();
		}
		prevInWater = isInWater();
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		drop();
	}

	@Override
	protected void func_230299_a_(BlockRayTraceResult result) {
		super.func_230299_a_(result);

		if (world.isRemote) {
			for (int i = 0; i < 10; i++) {
				Vector3d particlePos = result.getHitVec().add(randCoord(), randCoord(), randCoord());
				world.addParticle(new ItemParticleData(ParticleTypes.ITEM, getItem()), particlePos.x, particlePos.y,
						particlePos.z, 0, 0, 0);
			}
		}
	}

	private double randCoord() {
		return (rand.nextDouble() - 0.5) * 0.5;
	}

	@Override
	protected void onEntityHit(EntityRayTraceResult result) {
		Entity target = result.getEntity();
		if (!world.isRemote) {
			target.attackEntityFrom(DamageSource.causeThrownDamage(this, func_234616_v_()), 1);
		}
	}

	private void drop() {
		if (!world.isRemote) {
			remove();
			if (rand.nextDouble() < 0.9 && func_234616_v_() instanceof PlayerEntity) {
				entityDropItem(getItem());
			}
		}
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
