package mod.vemerion.runeworld.entity;

import java.util.List;
import java.util.Random;

import mod.vemerion.runeworld.goal.HoverWanderGoal;
import mod.vemerion.runeworld.init.ModEffects;
import mod.vemerion.runeworld.init.ModFluids;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class MosquitoEntity extends CreatureEntity implements IFlyingAnimal {

	public MosquitoEntity(EntityType<? extends MosquitoEntity> type, World worldIn) {
		super(type, worldIn);
		this.moveController = new FlyingMovementController(this, 20, true);
		this.experienceValue = 2;
	}

	public static AttributeModifierMap.MutableAttribute attributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 5.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D)
				.createMutableAttribute(Attributes.FLYING_SPEED, 0.4D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D);
	}

	public static boolean canSpawn(EntityType<MosquitoEntity> type, IServerWorld world, SpawnReason spawnReason,
			BlockPos pos, Random random) {
		if (world.getDifficulty() != Difficulty.PEACEFUL
				&& (isBlood(world, pos.down()) || isBlood(world, pos.down(2)))) {
			AxisAlignedBB box = new AxisAlignedBB(pos).grow(16);
			List<MosquitoEntity> entities = world.getEntitiesWithinAABB(MosquitoEntity.class, box, null);
			return entities.size() < 5;
		}

		return false;
	}

	private static boolean isBlood(IServerWorld world, BlockPos pos) {
		return world.getBlockState(pos).getFluidState().getFluid().isEquivalentTo(ModFluids.BLOOD);
	}

	@Override
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
			ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
		setHomePosAndDistance(getPosition(), 7);
		return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	@Override
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		setHomePosAndDistance(NBTUtil.readBlockPos(compound.getCompound("homePos")), 7);
	}

	@Override
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.put("homePos", NBTUtil.writeBlockPos(getHomePosition()));
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.4, true));
		goalSelector.addGoal(2, new HoverWanderGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}

	@Override
	protected PathNavigator createNavigator(World world) {
		FlyingPathNavigator navigator = new FlyingPathNavigator(this, world) {
			@Override
			public boolean canEntityStandOnPos(BlockPos pos) {
				BlockState state = this.world.getBlockState(pos);
				return state.getBlock().isAir(state, world, pos) || !state.getMaterial().blocksMovement();
			}
		};
		navigator.setCanEnterDoors(false);
		navigator.setCanSwim(false);
		navigator.setCanOpenDoors(false);
		return navigator;
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		if (super.attackEntityAsMob(entityIn)) {
			if (rand.nextDouble() < 0.1 && entityIn instanceof PlayerEntity)
				((PlayerEntity) entityIn).addPotionEffect(new EffectInstance(ModEffects.BLOOD_DRAINED, 20 * 45));
			return true;
		}
		return false;
	}

	@Override
	protected boolean makeFlySound() {
		return true;
	}

	@Override
	public boolean onLivingFall(float fallDistance, float damageMultiplier) {
		return false;
	}

	@Override
	public boolean hasNoGravity() {
		return true;
	}
}
