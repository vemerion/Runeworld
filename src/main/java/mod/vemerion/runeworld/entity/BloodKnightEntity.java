package mod.vemerion.runeworld.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class BloodKnightEntity extends Monster {

	private static final EntityDataAccessor<Byte> RED = SynchedEntityData.defineId(BloodKnightEntity.class,
			EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> GREEN = SynchedEntityData.defineId(BloodKnightEntity.class,
			EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Byte> BLUE = SynchedEntityData.defineId(BloodKnightEntity.class,
			EntityDataSerializers.BYTE);

	public BloodKnightEntity(EntityType<? extends BloodKnightEntity> type, Level level) {
		super(type, level);
		this.xpReward = XP_REWARD_LARGE;
	}

	public static AttributeSupplier.Builder attributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10).add(Attributes.MOVEMENT_SPEED, 0.25)
				.add(Attributes.FOLLOW_RANGE, 16).add(Attributes.ATTACK_DAMAGE, 3)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1);
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2f, true));
		goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.8));
		goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8));
		goalSelector.addGoal(8, new RandomLookAroundGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(RED, (byte) random.nextInt(0, 255));
		entityData.define(GREEN, (byte) random.nextInt(0, 255));
		entityData.define(BLUE, (byte) random.nextInt(0, 255));
	}

	public byte getRed() {
		return entityData.get(RED);
	}

	private void setRed(byte value) {
		entityData.set(RED, value);
	}

	public byte getGreen() {
		return entityData.get(GREEN);
	}

	private void setGreen(byte value) {
		entityData.set(GREEN, value);
	}

	public byte getBlue() {
		return entityData.get(BLUE);
	}

	private void setBlue(byte value) {
		entityData.set(BLUE, value);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putByte("getRed", getRed());
		pCompound.putByte("getGreen", getGreen());
		pCompound.putByte("getBlue", getBlue());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		if (pCompound.contains("getRed"))
			setRed(pCompound.getByte("getRed"));
		if (pCompound.contains("getGreen"))
			setGreen(pCompound.getByte("getGreen"));
		if (pCompound.contains("getBlue"))
			setBlue(pCompound.getByte("getBlue"));
	}
}
