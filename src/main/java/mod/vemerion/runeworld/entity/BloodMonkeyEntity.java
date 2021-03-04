package mod.vemerion.runeworld.entity;

import mod.vemerion.runeworld.block.BloodPillarBlock;
import mod.vemerion.runeworld.helpers.Helper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BloodMonkeyEntity extends MonsterEntity implements IRangedAttackMob {

	private float bodyRot, prevBodyRot;

	public BloodMonkeyEntity(EntityType<? extends BloodMonkeyEntity> type, World worldIn) {
		super(type, worldIn);
		this.experienceValue = 5;
	}

	public static AttributeModifierMap.MutableAttribute attributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 5.0D)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D);
	}

	@Override
	public void tick() {
		super.tick();

		prevBodyRot = bodyRot;
		if (isStandingOnPillar())
			bodyRot = MathHelper.lerp(0.1f, bodyRot, Helper.toRad(15));
		else
			bodyRot = MathHelper.lerp(0.1f, bodyRot, Helper.toRad(70));
	}
	
	public float getBodyRot(float partialTicks) {
		return MathHelper.lerp(partialTicks, prevBodyRot, bodyRot);
	}

	protected PathNavigator createNavigator(World worldIn) {
		return new ClimberPathNavigator(this, worldIn);
	}

	@Override
	public boolean onLivingFall(float distance, float damageMultiplier) {
		return false;
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new RangedAttackGoal(this, 0, 45, 10) {
			@Override
			public boolean shouldExecute() {
				return super.shouldExecute() && isStandingOnPillar();
			}
		});
		goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5, true) {
			@Override
			public boolean shouldExecute() {
				return super.shouldExecute() && !isStandingOnPillar();
			}
		});
		goalSelector.addGoal(2, new ClimbPillarGoal(this, 1, 10));
		goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 0.8) {
			@Override
			public boolean shouldExecute() {
				return super.shouldExecute() && !isStandingOnPillar();
			}
		});
		goalSelector.addGoal(4, new LookRandomlyGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}

	@Override
	public boolean isOnLadder() {
		return super.isOnLadder() || collidedHorizontally;
	}

	@Override
	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		swingArm(Hand.MAIN_HAND);
		ProjectileItemEntity projectile = rand.nextDouble() < 0.1 ? new MosquitoEggsEntity(this, world)
				: new BloodPebbleEntity(this, world);
		double x = target.getPosX() - getPosX();
		double y = target.getPosYEye() - projectile.getPosY();
		double z = target.getPosZ() - getPosZ();
		double height = MathHelper.sqrt(x * x + z * z) * 0.2;
		projectile.shoot(x, y + height, z, 1f, 1f);
		world.addEntity(projectile);
	}

	public boolean isStandingOnPillar() {
		float halfWidth = getWidth() * 0.51f;
		return BlockPos
				.getAllInBox(new AxisAlignedBB(getPositionVec().subtract(halfWidth, 0.5, halfWidth),
						getPositionVec().add(halfWidth, -0.1, halfWidth)))
				.anyMatch(p -> BloodPillarBlock.isPillar(world, p) && !BloodPillarBlock.isPillar(world, p.up()));
	}

	private static class ClimbPillarGoal extends MoveToBlockGoal {

		private BloodMonkeyEntity monkey;

		private ClimbPillarGoal(BloodMonkeyEntity monkey, double speedIn, int length) {
			super(monkey, speedIn, length);
			this.monkey = monkey;
		}

		@Override
		protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
			return BloodPillarBlock.isPillar(worldIn, pos);
		}

		@Override
		public boolean shouldExecute() {
			return super.shouldExecute() && !monkey.isStandingOnPillar();
		}

	}

//	private static class PillarJumpGoal extends Goal {
//
//		private static final int MIN_JUMP_COOLDOWN = 20 * 10;
//		private static final double JUMP_ANGLE = Math.toRadians(65);
//
//		private BloodMonkeyEntity monkey;
//		private int jumpCooldown;
//
//		public PillarJumpGoal(BloodMonkeyEntity monkey) {
//			this.monkey = monkey;
//			this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
//		}
//
//		@Override
//		public boolean shouldExecute() {
//			return !getNearbyPillars().isEmpty() && monkey.getRNG().nextInt(2) == 1;
//		}
//
//		private List<BlockPos> getNearbyPillars() {
//			List<BlockPos> pillars = new ArrayList<>();
//			World world = monkey.world;
//			BlockPos.getAllInBox(monkey.getBoundingBox().grow(5)).forEach(pos -> {
//				if (BloodPillarBlock.isPillar(world, pos) && !pos.up().equals(monkey.getPosition())
//						&& world.isAirBlock(pos.up()) && world.isAirBlock(pos.up(2)))
//					pillars.add(pos.toImmutable());
//			});
//			return pillars;
//		}
//
//		@Override
//		public void tick() {
//			if (jumpCooldown-- < 0) {
//				jumpCooldown = 60;
//
//				List<BlockPos> pillars = getNearbyPillars();
//				Vector3d distance = Vector3d
//						.copyCenteredHorizontally(pillars.get(monkey.getRNG().nextInt(pillars.size())).up())
//						.subtract(monkey.getPositionVec());
//				double horizontalDistance = length(distance.x, distance.z);
//				double angle = calcAngle(horizontalDistance, distance.y);
//				double velocity = calcVelocity(horizontalDistance, distance.y, angle);
//				if (!Double.isFinite(angle) || !Double.isFinite(velocity))
//					System.out.println("PROBLEMS");
//				Vector3d horizontalDir = new Vector3d(distance.x, 0, distance.z).normalize();
//				Vector3d motion = new Vector3d(horizontalDir.x * Math.cos(angle), Math.sin(angle),
//						horizontalDir.z * Math.cos(angle)).scale(velocity);
//				monkey.setMotion(motion);
//			}
//		}
//
//		private double calcVelocity(double x, double y, double angle) {
//			double g = monkey.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).getValue();
//			return Math.sqrt(x * x * g / (x * Math.sin(2 * angle) - 2 * y * Math.pow(Math.cos(angle), 2)));
//		}
//
//		private double calcAngle(double x, double y) {
//			return Math.PI / 2 - (Math.PI / 2 - Math.atan2(y, x)) / 2;
//		}
//
//		private double length(double x, double z) {
//			return Math.sqrt(x * x + z + z);
//		}
//
//	}
}
