package mod.vemerion.runeworld.entity;

import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion.Mode;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class FireElementalProjectileEntity extends ProjectileItemEntity {

	private int duration = 20 * 5;

	public FireElementalProjectileEntity(LivingEntity livingEntityIn, World worldIn) {
		super(ModEntities.FIRE_ELEMENTAL_PROJECTILE, livingEntityIn, worldIn);
		this.setNoGravity(true);
	}

	public FireElementalProjectileEntity(EntityType<? extends FireElementalProjectileEntity> type, World world) {
		super(type, world);
		this.setNoGravity(true);
	}

	public FireElementalProjectileEntity(World world) {
		this(ModEntities.FIRE_ELEMENTAL_PROJECTILE, world);
	}

	@Override
	protected Item getDefaultItem() {
		return ModBlocks.FIRE_RITUAL_STONE.asItem();
	}

	@Override
	public void tick() {
		super.tick();

		if (!world.isRemote && isAlive()) {
			if (duration-- < 0) {
				explode();
			}
		}
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("duration"))
			duration = compound.getInt("duration");
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("duration", duration);
	}
	
	private void explode() {
		if (!world.isRemote && isAlive()) {
			world.createExplosion(this, getPosX(), getPosY(), getPosZ(), 2, true, Mode.BREAK);
			remove();
		}
	}

	@Override
	protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
		super.func_230299_a_(p_230299_1_);
		explode();
	}
	
	@Override
	protected void onEntityHit(EntityRayTraceResult result) {
		if (result.getEntity() instanceof FireElementalEntity)
			return;
		explode();
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

}
