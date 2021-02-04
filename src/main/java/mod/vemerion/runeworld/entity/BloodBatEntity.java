package mod.vemerion.runeworld.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BloodBatEntity extends CreatureEntity implements IFlyingAnimal {

	public BloodBatEntity(EntityType<? extends BloodBatEntity> type, World worldIn) {
		super(type, worldIn);
		this.moveController = new FlyingMovementController(this, 20, true);
	}

	public static AttributeModifierMap.MutableAttribute attributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 5.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D)
				.createMutableAttribute(Attributes.FLYING_SPEED, 0.4D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D);
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
	
	public float getAnimationHeight(float partialTicks) {
		return MathHelper.cos((ticksExisted + partialTicks) / 5);
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
