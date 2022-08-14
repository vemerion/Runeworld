package mod.vemerion.runeworld.entity;

import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;

public class FireElementalProjectileEntity extends ThrowableItemProjectile {

	private int duration = 20 * 5;

	public FireElementalProjectileEntity(LivingEntity livingEntityIn, Level worldIn) {
		super(ModEntities.FIRE_ELEMENTAL_PROJECTILE.get(), livingEntityIn, worldIn);
		this.setNoGravity(true);
	}

	public FireElementalProjectileEntity(EntityType<? extends FireElementalProjectileEntity> type, Level world) {
		super(type, world);
		this.setNoGravity(true);
	}

	public FireElementalProjectileEntity(Level world) {
		this(ModEntities.FIRE_ELEMENTAL_PROJECTILE.get(), world);
	}

	@Override
	protected Item getDefaultItem() {
		return ModBlocks.FIRE_RITUAL_STONE.get().asItem();
	}

	@Override
	public void tick() {
		super.tick();

		if (!level.isClientSide && isAlive()) {
			if (duration-- < 0) {
				explode();
			}
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		if (compound.contains("duration"))
			duration = compound.getInt("duration");
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("duration", duration);
	}
	
	private void explode() {
		if (!level.isClientSide && isAlive()) {
			level.explode(this, getX(), getY(), getZ(), 2, true, BlockInteraction.BREAK);
			discard();
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult p_230299_1_) {
		super.onHitBlock(p_230299_1_);
		explode();
	}
	
	@Override
	protected void onHitEntity(EntityHitResult result) {
		if (result.getEntity() instanceof FireElementalEntity)
			return;
		explode();
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
