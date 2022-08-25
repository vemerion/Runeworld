package mod.vemerion.runeworld.entity;

import java.util.List;
import java.util.Random;

import mod.vemerion.runeworld.goal.HoverWanderGoal;
import mod.vemerion.runeworld.init.ModEffects;
import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.ModSounds;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;

public class MosquitoEntity extends PathfinderMob implements FlyingAnimal {

	public MosquitoEntity(EntityType<? extends MosquitoEntity> type, Level worldIn) {
		super(type, worldIn);
		this.moveControl = new FlyingMoveControl(this, 20, true);
		this.xpReward = 2;
	}

	public static AttributeSupplier.Builder attributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 5.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.2D)
				.add(Attributes.FOLLOW_RANGE, 16.0D)
				.add(Attributes.FLYING_SPEED, 0.4D)
				.add(Attributes.ATTACK_DAMAGE, 1.0D);
	}

	public static boolean canSpawn(EntityType<MosquitoEntity> type, ServerLevelAccessor world, MobSpawnType spawnReason,
			BlockPos pos, Random random) {
		if (world.getDifficulty() != Difficulty.PEACEFUL
				&& (isBlood(world, pos.below()) || isBlood(world, pos.below(2)))) {
			AABB box = new AABB(pos).inflate(16);
			List<MosquitoEntity> entities = world.getEntitiesOfClass(MosquitoEntity.class, box);
			return entities.size() < 5;
		}

		return false;
	}
	
	@Override
	public int getAmbientSoundInterval() {
		return 20;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.MOSQUITO_FLYING.get();
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.MOSQUITO_SPLASH.get();
	}
	
	@Override
	public boolean isFlying() {
		return !this.onGround;
	}
	
	private static boolean isBlood(ServerLevelAccessor world, BlockPos pos) {
		return world.getBlockState(pos).getFluidState().getType().isSame(ModFluids.BLOOD.get());
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason,
			SpawnGroupData spawnDataIn, CompoundTag dataTag) {
		restrictTo(blockPosition(), 7);
		return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		restrictTo(NbtUtils.readBlockPos(compound.getCompound("homePos")), 7);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.put("homePos", NbtUtils.writeBlockPos(getRestrictCenter()));
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.4, true));
		goalSelector.addGoal(2, new HoverWanderGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	@Override
	protected PathNavigation createNavigation(Level world) {
		FlyingPathNavigation navigator = new FlyingPathNavigation(this, world) {
			@Override
			public boolean isStableDestination(BlockPos pos) {
				BlockState state = this.level.getBlockState(pos);
				return state.isAir() || !state.getMaterial().blocksMotion();
			}
		};
		navigator.setCanPassDoors(false);
		navigator.setCanFloat(false);
		navigator.setCanOpenDoors(false);
		return navigator;
	}

	@Override
	public boolean doHurtTarget(Entity entityIn) {
		if (super.doHurtTarget(entityIn)) {
			if (random.nextDouble() < 0.1 && entityIn instanceof Player)
				((Player) entityIn).addEffect(new MobEffectInstance(ModEffects.BLOOD_DRAINED.get(), 20 * 45));
			return true;
		}
		return false;
	}

	@Override
	public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource pSource) {
		return false;
	}

	@Override
	public boolean isNoGravity() {
		return true;
	}
}
