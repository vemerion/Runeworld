package mod.vemerion.runeworld.entity;

import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.ModSounds;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraft.world.level.Level;

public class MosquitoEggsEntity extends ThrowableItemProjectile {

	public MosquitoEggsEntity(LivingEntity livingEntityIn, Level worldIn) {
		super(ModEntities.MOSQUITO_EGGS.get(), livingEntityIn, worldIn);
	}

	public MosquitoEggsEntity(EntityType<? extends MosquitoEggsEntity> type, Level world) {
		super(type, world);
	}

	@Override
	protected Item getDefaultItem() {
		return ModItems.MOSQUITO_EGGS;
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		
		playSound(ModSounds.MOSQUITO_SPLASH.get(), 1, Helper.soundPitch(random));

		if (!level.isClientSide && isAlive()) {
			Vec3 pos = result.getLocation();
			for (int i = 0; i < random.nextInt(2) + 2; i++) {
				MosquitoEntity mosquito = new MosquitoEntity(ModEntities.MOSQUITO.get(), level);
				mosquito.absMoveTo(pos.x + (random.nextDouble() - 0.5) * 2, pos.y,
						pos.z + (random.nextDouble() - 0.5) * 2, random.nextInt(360), 0);
				level.addFreshEntity(mosquito);
			}
			discard();
		}
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected float getGravity() {
		return 0.05f;
	}

}
