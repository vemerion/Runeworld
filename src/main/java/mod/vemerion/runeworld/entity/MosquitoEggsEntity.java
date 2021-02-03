package mod.vemerion.runeworld.entity;

import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MosquitoEggsEntity extends ProjectileItemEntity {

	public MosquitoEggsEntity(LivingEntity livingEntityIn, World worldIn) {
		super(ModEntities.MOSQUITO_EGGS, livingEntityIn, worldIn);
	}

	public MosquitoEggsEntity(EntityType<? extends MosquitoEggsEntity> type, World world) {
		super(type, world);
	}

	@Override
	protected Item getDefaultItem() {
		return ModItems.MOSQUITO_EGGS;
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);

		if (!world.isRemote && isAlive()) {
			Vector3d pos = result.getHitVec();
			for (int i = 0; i < rand.nextInt(2) + 2; i++) {
				MosquitoEntity mosquito = new MosquitoEntity(ModEntities.MOSQUITO, world);
				mosquito.setPositionAndRotation(pos.x + (rand.nextDouble() - 0.5) * 2, pos.y,
						pos.z + (rand.nextDouble() - 0.5) * 2, rand.nextInt(360), 0);
				world.addEntity(mosquito);
			}
			remove();
		}
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected float getGravityVelocity() {
		return 0.05f;
	}

}
