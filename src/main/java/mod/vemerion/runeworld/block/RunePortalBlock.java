package mod.vemerion.runeworld.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import mod.vemerion.runeworld.capability.RuneTeleport;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.particle.RunePortalParticleData;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.common.util.LazyOptional;

public class RunePortalBlock extends Block {
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;

	private static final int PORTAL_MAX_SIZE = 100;
	private static Map<Item, RunePortalBlock> portalFromRune;

	protected static final VoxelShape X_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
	protected static final VoxelShape Z_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

	private ResourceKey<Level> dimension;
	private Supplier<Item> rune;
	public final int red, green, blue;

	public RunePortalBlock(ResourceKey<Level> dimension, Supplier<Item> rune, int red, int green, int blue) {
		super(BlockBehaviour.Properties.of(Material.PORTAL).noCollission().strength(-1.0F)
				.sound(SoundType.GLASS).lightLevel((state) -> {
					return 11;
				}));
		this.dimension = dimension;
		this.rune = rune;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X));
	}

	public static Block getPortalFromRune(Item item) {
		if (portalFromRune == null) {
			portalFromRune = new HashMap<>();
			ModBlocks.getRunePortals().forEach(portal -> portalFromRune.put(portal.rune.get(), portal));
		}
		return portalFromRune.get(item);
	}

	public int getColor() {
		return Helper.color(red, green, blue, 1);
	}

	@Override
	public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
		for (int i = 0; i < 5; i++) {
			Vec3 position = randVec(rand).add(Vec3.atCenterOf(pos));
			Vec3 speed = randVec(rand);
			worldIn.addParticle(new RunePortalParticleData(red / 255f, green / 255f, blue / 255f), position.x,
					position.y, position.z, speed.x, speed.y, speed.z);
		}
	}

	private Vec3 randVec(Random rand) {
		return new Vec3(rand.nextDouble() - 0.5, rand.nextDouble() - 0.5, rand.nextDouble() - 0.5);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		if (state.getValue(AXIS) == Direction.Axis.Z)
			return Z_AABB;
		return X_AABB;
	}

	@Override
	public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		if (rot == Rotation.COUNTERCLOCKWISE_90 || rot == Rotation.CLOCKWISE_90)
			if (state.getValue(AXIS) == Direction.Axis.Z)
				return state.setValue(AXIS, Direction.Axis.X);
			else if (state.getValue(AXIS) == Direction.Axis.X)
				return state.setValue(AXIS, Direction.Axis.Z);
		return state;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn,
			BlockPos currentPos, BlockPos facingPos) {
		Direction.Axis facingAxis = facing.getAxis();
		Direction.Axis axis = stateIn.getValue(AXIS);
		if (!(axis != facingAxis && facingAxis.isHorizontal()) && !facingState.is(this)
				&& !isPortalBorderIntact(worldIn, currentPos, stateIn.getBlock(), axis))
			return Blocks.AIR.defaultBlockState();
		return stateIn;
	}

	@Override
	public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
		if (!worldIn.isClientSide && !entityIn.isPassenger() && !entityIn.isVehicle() && entityIn.canChangeDimensions()) {
			LazyOptional<RuneTeleport> teleport = RuneTeleport.getRuneTeleport(entityIn);
			if (teleport.isPresent() && entityIn instanceof Player)
				teleport.ifPresent(tp -> tp.setPortal((Player) entityIn, worldIn, dimension));
			else
				RuneTeleport.teleport(entityIn, worldIn, dimension);
		}
	}

	private boolean isPortalBorderIntact(LevelAccessor world, BlockPos pos, Block portal, Direction.Axis axis) {
		Set<BlockPos> checked = new HashSet<>();
		List<BlockPos> worklist = new ArrayList<>();
		Direction dir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
		BlockPos offsets[] = Helper.offsets(dir);
		worklist.add(pos);
		while (!worklist.isEmpty()) {
			pos = worklist.remove(0);
			BlockState state = world.getBlockState(pos);
			if (!(state.is(Tags.Blocks.OBSIDIAN) || state.getBlock() == portal)) {
				return false;
			}
			checked.add(pos);
			for (BlockPos offset : offsets) {
				pos = pos.offset(offset);
				if (!checked.contains(pos))
					worklist.add(pos);
			}
		}
		return true;
	}

	public static boolean createPortal(Level world, BlockPos pos, Block portal) {
		if (portal == null)
			return false;
		
		if (createPortal(world, pos, portal, Direction.Axis.X))
			return true;
		return createPortal(world, pos, portal, Direction.Axis.Z);
	}

	public static boolean createPortal(LevelAccessor world, BlockPos pos, Block portal, Direction.Axis axis) {
		Set<BlockPos> positions = new HashSet<>();
		List<BlockPos> worklist = new ArrayList<>();
		BlockState portalState = portal.defaultBlockState().setValue(AXIS, axis);
		Direction dir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
		BlockPos offsets[] = Helper.offsets(dir);
		BlockPos start = new BlockPos(pos);
		for (BlockPos offset : offsets) {
			pos = start.offset(offset);

			positions = new HashSet<>();
			worklist = new ArrayList<>();
			worklist.add(pos);
			while (positions.size() <= PORTAL_MAX_SIZE && !worklist.isEmpty()) {
				pos = worklist.remove(0);

				if (world.getBlockState(pos).is(Tags.Blocks.OBSIDIAN) || positions.contains(pos))
					continue;
				if (isValidPortalState(world, portal, pos)) {
					positions.add(pos);
				} else {
					positions = new HashSet<>();
					break;
				}

				for (BlockPos o : offsets)
					worklist.add(pos.offset(o));
			}

			if (positions.size() < PORTAL_MAX_SIZE && !positions.isEmpty()) {
				for (BlockPos p : positions) {
					world.setBlock(p, portalState, 3);
				}
				return true;
			}
		}

		return false;
	}

	private static boolean isValidPortalState(LevelAccessor world, Block portal, BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		return world.isEmptyBlock(pos) || block == portal;
	}

	public static class RuneTeleporter implements ITeleporter {
		private static final BlockState AIR = Blocks.AIR.defaultBlockState();

		@Override
		public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw,
				Function<Boolean, Entity> repositionEntity) {
			return repositionEntity.apply(false);
		}

		@Override
		public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld,
				Function<ServerLevel, PortalInfo> defaultPortalInfo) {
			BlockPos spawn = locateSpawnPos(destWorld, entity.position());
			if (spawn == null) {
				spawn = new BlockPos(entity.position());
				destWorld.setBlockAndUpdate(spawn, AIR);
				destWorld.setBlockAndUpdate(spawn.above(), AIR);
			}
			if (destWorld.isEmptyBlock(spawn.below()))
				destWorld.setBlockAndUpdate(spawn.below(), Blocks.OBSIDIAN.defaultBlockState());

			return new PortalInfo(Vec3.atBottomCenterOf(spawn), Vec3.ZERO, entity.getYRot(),
					entity.getXRot());
		}

		private BlockPos locateSpawnPos(ServerLevel world, Vec3 center) {
			BlockPos pos = world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, new BlockPos(center));
			Random rand = new Random(0);

			for (int i = 0; i < 100; i++) {
				if (isValidPos(world, pos))
					return pos;
				pos.offset(rand.nextInt(20) - 10, 0, rand.nextInt(20) - 10);
				pos = world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, pos);
			}

			return null;
		}

		private boolean isValidPos(ServerLevel world, BlockPos pos) {
			return world.isEmptyBlock(pos) && world.isEmptyBlock(pos.above());
		}
	}
}
