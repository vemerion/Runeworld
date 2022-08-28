package mod.vemerion.runeworld.entity;

import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.ModParticles;
import mod.vemerion.runeworld.init.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TickEntity extends Monster {

	private static final EntityDataAccessor<Byte> GROWTH = SynchedEntityData.defineId(TickEntity.class,
			EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> EXPLODING = SynchedEntityData.defineId(TickEntity.class,
			EntityDataSerializers.BOOLEAN);

	private static final int EXPLODE_TIME = 20;
	private static final int EXPLODE_PARTICLES_START = EXPLODE_TIME - 5;

	private int explodeTimer = 0;

	public TickEntity(EntityType<? extends TickEntity> type, Level worldIn) {
		super(type, worldIn);
		this.xpReward = XP_REWARD_MEDIUM;
	}

	public static AttributeSupplier.Builder attributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10).add(Attributes.MOVEMENT_SPEED, 0.25)
				.add(Attributes.FOLLOW_RANGE, 16).add(Attributes.ATTACK_DAMAGE, 3);
	}

	protected void registerGoals() {
		goalSelector.addGoal(0, new MeleeAttackGoal(this, 1, true));
		goalSelector.addGoal(1, new RandomStrollGoal(this, 0.7));
		goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8.0F));
		goalSelector.addGoal(1, new RandomLookAroundGoal(this));
		targetSelector.addGoal(0, new HurtByTargetGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}
	
	@Override
	public boolean canBreatheUnderwater() {
		return isEyeInFluid(ModFluids.BLOOD_TAG);
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.TICK.get();
	}
	
	protected PathNavigation createNavigation(Level level) {
		return new WallClimberNavigation(this, level);
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource pSource) {
		return false;
	}

	@Override
	public boolean onClimbable() {
		return true;
	}
	
	@Override
	public boolean isIgnoringBlockTriggers() {
		return true;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(GROWTH, (byte) 0);
		entityData.define(EXPLODING, false);
	}

	public float getSize(float partialTicks) {
		float explodeGrowth = 0;
		if (isExploding())
			explodeGrowth = (explodeTimer + partialTicks) / EXPLODE_TIME
					+ Mth.cos((tickCount + partialTicks) * 3) * 0.2f;
		return 1 + getGrowth() / 10f + explodeGrowth;
	}

	private byte getGrowth() {
		return entityData.get(GROWTH);
	}

	private void setExploding(boolean value) {
		entityData.set(EXPLODING, value);
	}

	public boolean isExploding() {
		return entityData.get(EXPLODING);
	}

	private void setGrowth(byte value) {
		refreshDimensions();
		entityData.set(GROWTH, value);
	}

	public void incGrowth(int value) {
		setGrowth((byte) (entityData.get(GROWTH) + value));
		if (random.nextDouble() < getGrowth() / 10f) {
			setExploding(true);
		}
	}

	@Override
	public EntityDimensions getDimensions(Pose pPose) {
		return super.getDimensions(pPose).scale(getSize(0));
	}

	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putByte("growth", getGrowth());
		pCompound.putBoolean("exploding", isExploding());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		if (pCompound.contains("growth"))
			setGrowth(pCompound.getByte("growth"));
		if (pCompound.contains("exploding"))
			setExploding(pCompound.getBoolean("exploding"));
	}

	@Override
	public void tick() {
		super.tick();
		if (isExploding()) {
			explodeTimer++;
			if (explodeTimer == EXPLODE_TIME && !level.isClientSide && isAlive()) {
				playSound(ModSounds.TICK_EXPLOSION.get(), getSoundVolume(), getVoicePitch());
				kill();
				for (var e : level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(3))) {
					e.hurt(DamageSource.mobAttack(this), (float) (getAttributeValue(Attributes.ATTACK_DAMAGE) * 3));
				}
			}

			if (isAlive() && level.isClientSide && explodeTimer > EXPLODE_PARTICLES_START) {
				for (int i = 0; i < 30; i++) {
					var pos = Helper.randomInEntity(this, random);
					var direction = new Vec3(1, 0, 0).xRot(random.nextFloat() * Mth.TWO_PI)
							.yRot(random.nextFloat() * Mth.TWO_PI).zRot(random.nextFloat() * Mth.TWO_PI).normalize()
							.scale(0.3);
					level.addParticle(ModParticles.BLOOD_DROP.get(), pos.x, pos.y, pos.z, direction.x, direction.y,
							direction.z);
				}
			}
		}
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
		super.onSyncedDataUpdated(pKey);

		if (pKey == GROWTH) {
			refreshDimensions();
		}
	}

	@Override
	public boolean doHurtTarget(Entity pEntity) {
		if (super.doHurtTarget(pEntity)) {
			incGrowth(2);
			return true;
		}
		return false;
	}
}
