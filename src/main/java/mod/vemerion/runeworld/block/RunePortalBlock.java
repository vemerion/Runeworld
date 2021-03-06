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
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PortalInfo;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.common.util.LazyOptional;

public class RunePortalBlock extends Block {
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;

	private static final int PORTAL_MAX_SIZE = 100;
	private static Map<Item, RunePortalBlock> portalFromRune;

	protected static final VoxelShape X_AABB = Block.makeCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
	protected static final VoxelShape Z_AABB = Block.makeCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

	private RegistryKey<World> dimension;
	private Supplier<Item> rune;
	public final int red, green, blue;

	public RunePortalBlock(RegistryKey<World> dimension, Supplier<Item> rune, int red, int green, int blue) {
		super(AbstractBlock.Properties.create(Material.PORTAL).doesNotBlockMovement().hardnessAndResistance(-1.0F)
				.sound(SoundType.GLASS).setLightLevel((state) -> {
					return 11;
				}));
		this.dimension = dimension;
		this.rune = rune;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.X));
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
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		for (int i = 0; i < 5; i++) {
			Vector3d position = randVec(rand).add(Vector3d.copyCentered(pos));
			Vector3d speed = randVec(rand);
			worldIn.addParticle(new RunePortalParticleData(red / 255f, green / 255f, blue / 255f), position.x,
					position.y, position.z, speed.x, speed.y, speed.z);
		}
	}

	private Vector3d randVec(Random rand) {
		return new Vector3d(rand.nextDouble() - 0.5, rand.nextDouble() - 0.5, rand.nextDouble() - 0.5);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.get(AXIS) == Direction.Axis.Z)
			return Z_AABB;
		return X_AABB;
	}

	@Override
	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		if (rot == Rotation.COUNTERCLOCKWISE_90 || rot == Rotation.CLOCKWISE_90)
			if (state.get(AXIS) == Direction.Axis.Z)
				return state.with(AXIS, Direction.Axis.X);
			else if (state.get(AXIS) == Direction.Axis.X)
				return state.with(AXIS, Direction.Axis.Z);
		return state;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) {
		Direction.Axis facingAxis = facing.getAxis();
		Direction.Axis axis = stateIn.get(AXIS);
		if (!(axis != facingAxis && facingAxis.isHorizontal()) && !facingState.isIn(this)
				&& !isPortalBorderIntact(worldIn, currentPos, stateIn.getBlock(), axis))
			return Blocks.AIR.getDefaultState();
		return stateIn;
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (!worldIn.isRemote && !entityIn.isPassenger() && !entityIn.isBeingRidden() && entityIn.isNonBoss()) {
			LazyOptional<RuneTeleport> teleport = RuneTeleport.getRuneTeleport(entityIn);
			if (teleport.isPresent() && entityIn instanceof PlayerEntity)
				teleport.ifPresent(tp -> tp.setPortal((PlayerEntity) entityIn, worldIn, dimension));
			else
				RuneTeleport.teleport(entityIn, worldIn, dimension);
		}
	}

	private boolean isPortalBorderIntact(IWorld world, BlockPos pos, Block portal, Direction.Axis axis) {
		Set<BlockPos> checked = new HashSet<>();
		List<BlockPos> worklist = new ArrayList<>();
		Direction dir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
		BlockPos offsets[] = Helper.offsets(dir);
		worklist.add(pos);
		while (!worklist.isEmpty()) {
			pos = worklist.remove(0);
			BlockState state = world.getBlockState(pos);
			if (!(state.isIn(Tags.Blocks.OBSIDIAN) || state.getBlock() == portal)) {
				return false;
			}
			checked.add(pos);
			for (BlockPos offset : offsets) {
				pos = pos.add(offset);
				if (!checked.contains(pos))
					worklist.add(pos);
			}
		}
		return true;
	}

	public static boolean createPortal(World world, BlockPos pos, Block portal) {
		if (createPortal(world, pos, portal, Direction.Axis.X))
			return true;
		return createPortal(world, pos, portal, Direction.Axis.Z);
	}

	public static boolean createPortal(IWorld world, BlockPos pos, Block portal, Direction.Axis axis) {
		Set<BlockPos> positions = new HashSet<>();
		List<BlockPos> worklist = new ArrayList<>();
		BlockState portalState = portal.getDefaultState().with(AXIS, axis);
		Direction dir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
		BlockPos offsets[] = Helper.offsets(dir);
		BlockPos start = new BlockPos(pos);
		for (BlockPos offset : offsets) {
			pos = start.add(offset);

			positions = new HashSet<>();
			worklist = new ArrayList<>();
			worklist.add(pos);
			while (positions.size() <= PORTAL_MAX_SIZE && !worklist.isEmpty()) {
				pos = worklist.remove(0);

				if (world.getBlockState(pos).isIn(Tags.Blocks.OBSIDIAN) || positions.contains(pos))
					continue;
				if (isValidPortalState(world, portal, pos)) {
					positions.add(pos);
				} else {
					positions = new HashSet<>();
					break;
				}

				for (BlockPos o : offsets)
					worklist.add(pos.add(o));
			}

			if (positions.size() < PORTAL_MAX_SIZE && !positions.isEmpty()) {
				for (BlockPos p : positions) {
					world.setBlockState(p, portalState, 3);
				}
				return true;
			}
		}

		return false;
	}

	private static boolean isValidPortalState(IWorld world, Block portal, BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		return world.isAirBlock(pos) || block == portal;
	}

	public static class RuneTeleporter implements ITeleporter {
		private static final BlockState AIR = Blocks.AIR.getDefaultState();

		@Override
		public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw,
				Function<Boolean, Entity> repositionEntity) {
			return repositionEntity.apply(false);
		}

		@Override
		public PortalInfo getPortalInfo(Entity entity, ServerWorld destWorld,
				Function<ServerWorld, PortalInfo> defaultPortalInfo) {
			BlockPos spawn = locateSpawnPos(destWorld, entity.getPositionVec());
			if (spawn == null) {
				spawn = new BlockPos(entity.getPositionVec());
				destWorld.setBlockState(spawn, AIR);
				destWorld.setBlockState(spawn.up(), AIR);
			}
			if (destWorld.isAirBlock(spawn.down()))
				destWorld.setBlockState(spawn.down(), Blocks.OBSIDIAN.getDefaultState());

			return new PortalInfo(Vector3d.copyCenteredHorizontally(spawn), Vector3d.ZERO, entity.rotationYaw,
					entity.rotationPitch);
		}

		private BlockPos locateSpawnPos(ServerWorld world, Vector3d center) {
			BlockPos pos = world.getHeight(Heightmap.Type.WORLD_SURFACE, new BlockPos(center));
			Random rand = new Random(0);

			for (int i = 0; i < 100; i++) {
				if (isValidPos(world, pos))
					return pos;
				pos.add(rand.nextInt(20) - 10, 0, rand.nextInt(20) - 10);
				pos = world.getHeight(Heightmap.Type.WORLD_SURFACE, pos);
			}

			return null;
		}

		private boolean isValidPos(ServerWorld world, BlockPos pos) {
			return world.isAirBlock(pos) && world.isAirBlock(pos.up());
		}
	}
}
