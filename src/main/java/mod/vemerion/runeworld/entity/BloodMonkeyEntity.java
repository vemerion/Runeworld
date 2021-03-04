package mod.vemerion.runeworld.entity;

import mod.vemerion.runeworld.block.BloodPillarBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RangedAttackGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BloodMonkeyEntity extends MonsterEntity implements IRangedAttackMob {

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
		goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 0.8) {
			@Override
			public boolean shouldExecute() {
				return super.shouldExecute() && !isStandingOnPillar();
			}
		});
		goalSelector.addGoal(3, new LookRandomlyGoal(this));
		targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
	}

	@Override
	public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
		ProjectileItemEntity projectile = rand.nextDouble() < 0.1 ? new MosquitoEggsEntity(this, world)
				: new BloodPebbleEntity(this, world);
		double x = target.getPosX() - getPosX();
		double y = target.getPosYEye() - 1.1f - projectile.getPosY();
		double z = target.getPosZ() - getPosZ();
		double height = MathHelper.sqrt(x * x + z * z) * 0.2;
		projectile.shoot(x, y + height, z, 1f, 1f);
		world.addEntity(projectile);
	}

	private boolean isStandingOnPillar() {
		return BloodPillarBlock.isPillar(world, getPosition().down());
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
