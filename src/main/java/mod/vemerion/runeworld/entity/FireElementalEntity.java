package mod.vemerion.runeworld.entity;

import java.util.EnumSet;
import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;

public class FireElementalEntity extends MonsterEntity {

	private static final DataParameter<Boolean> RAISING_ARMS = EntityDataManager.createKey(FireElementalEntity.class,
			DataSerializers.BOOLEAN);

	private final ServerBossInfo bossInfo = new ServerBossInfo(getDisplayName(), BossInfo.Color.YELLOW,
			BossInfo.Overlay.PROGRESS);

	private float armHeight, prevArmHeight;

	public FireElementalEntity(EntityType<? extends FireElementalEntity> type, World worldIn) {
		super(type, worldIn);
		this.experienceValue = 20;
	}

	public static AttributeModifierMap.MutableAttribute attributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 16)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 3);
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new ShootProjectileGoal(this));
		goalSelector.addGoal(1, new ScorchedGroundGoal(this));
		goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1));
		goalSelector.addGoal(3, new LookRandomlyGoal(this));
		targetSelector.addGoal(1, new HurtByTargetGoal(this));
		targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MobEntity.class, 0, false, false, null));
	}

	@Override
	protected void registerData() {
		super.registerData();
		dataManager.register(RAISING_ARMS, false);
	}

	private boolean isRaisingArms() {
		return dataManager.get(RAISING_ARMS);
	}

	private void setRaisingArms(boolean b) {
		dataManager.set(RAISING_ARMS, b);
	}

	@Override
	public void tick() {
		super.tick();

		bossInfo.setPercent(getHealthPercent());

		if (world.isRemote) {
			prevArmHeight = armHeight;
			if (isRaisingArms())
				armHeight = Math.min(armHeight + 0.2f, 2.5f);
			else
				armHeight = Math.max(armHeight - 0.2f, 0);
		}
	}

	public float getHealthPercent() {
		return getHealth() / getMaxHealth();
	}

	@Override
	public void addTrackingPlayer(ServerPlayerEntity player) {
		super.addTrackingPlayer(player);
		bossInfo.addPlayer(player);
	}

	@Override
	public void removeTrackingPlayer(ServerPlayerEntity player) {
		super.removeTrackingPlayer(player);
		bossInfo.removePlayer(player);
	}

	@Override
	public boolean onLivingFall(float fallDistance, float damageMultiplier) {
		return false;
	}

	@Override
	public boolean isNonBoss() {
		return false;
	}

	@Override
	public boolean isImmuneToExplosions() {
		return true;
	}

	@Override
	public void applyKnockback(float strength, double ratioX, double ratioZ) {
	}

	public float getArmHeight(float partialTicks) {
		return MathHelper.lerp(partialTicks, prevArmHeight, armHeight);
	}

	private static class ScorchedGroundGoal extends Goal {

		private static final int DURATION = 20 * 3;
		private static final int COOLDOWN = 20 * 20;

		private FireElementalEntity elemental;
		private int cooldown, duration;

		private ScorchedGroundGoal(FireElementalEntity elemental) {
			this.elemental = elemental;
			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
		}

		@Override
		public boolean shouldExecute() {
			return cooldown-- < 0;
		}

		@Override
		public void startExecuting() {
			duration = DURATION;
		}

		@Override
		public void tick() {
			if (duration-- > 0) {
				elemental.setRaisingArms(true);
				Random rand = elemental.getRNG();
				World world = elemental.world;

				BlockPos pos = elemental.getPosition().add(new BlockPos(
						Vector3d.fromPitchYaw(0, rand.nextFloat() * 360).scale(1 + rand.nextDouble() * 5)));

				for (int i = 0; i < 10; i++) {
					for (int j = -1; j < 2; j += 2) {
						BlockPos p = pos.down(i * j);
						if (world.isAirBlock(p) && world.getBlockState(p.down()).isSolid()) {
							world.setBlockState(p, Blocks.FIRE.getDefaultState());
							return;
						}
					}
				}

			} else {
				elemental.setRaisingArms(false);
				cooldown = COOLDOWN;
			}
		}
	}

	private static class ShootProjectileGoal extends Goal {

		private static final int MAX_COOLDOWN = 20 * 1;

		private FireElementalEntity elemental;
		private int cooldown;

		public ShootProjectileGoal(FireElementalEntity elemental) {
			this.elemental = elemental;
		}

		@Override
		public boolean shouldExecute() {
			return cooldown-- < 0;
		}

		@Override
		public void startExecuting() {
			cooldown = MAX_COOLDOWN;
			Random rand = elemental.getRNG();
			World world = elemental.world;
			int side = rand.nextBoolean() ? 1 : -1;
			Vector3d offset = Vector3d.fromPitchYaw(0, elemental.rotationYaw + 90)
					.scale(side * (rand.nextDouble() * 3 + 1.5));
			Vector3d position = elemental.getPositionVec().add(offset.x, 3 + rand.nextDouble() * 3, offset.z);
			Vector3d target = Vector3d.fromPitchYaw(rand.nextFloat() * (-180 - 60) + 30, rand.nextFloat() * 360);
			FireElementalProjectileEntity projectile = new FireElementalProjectileEntity(world);
			projectile.setShooter(elemental);
			projectile.setPosition(position.x, position.y, position.z);
			projectile.shoot(target.x, target.y, target.z, 0.5f, 1);
			world.addEntity(projectile);
		}

	}
}
