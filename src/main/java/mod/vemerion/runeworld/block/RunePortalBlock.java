package mod.vemerion.runeworld.block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.particle.RunePortalParticleData;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class RunePortalBlock extends Block {
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;

	private static final int PORTAL_MAX_SIZE = 100;

	protected static final VoxelShape X_AABB = Block.makeCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
	protected static final VoxelShape Z_AABB = Block.makeCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);

	private RegistryKey<World> dimension;
	private int red, green, blue;

	public RunePortalBlock(RegistryKey<World> dimension, int red, int green, int blue) {
		super(AbstractBlock.Properties.create(Material.PORTAL).doesNotBlockMovement().hardnessAndResistance(-1.0F)
				.sound(SoundType.GLASS).setLightLevel((state) -> {
					return 11;
				}));
		this.dimension = dimension;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.X));
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

	public static boolean createPortal(World world, BlockPos pos, Block portal) {
		if (createPortal(world, pos, portal, Direction.Axis.X))
			return true;
		return createPortal(world, pos, portal, Direction.Axis.Z);
	}

	public static boolean createPortal(World world, BlockPos pos, Block portal, Direction.Axis axis) {
		Set<BlockPos> positions = new HashSet<>();
		List<BlockPos> worklist = new ArrayList<>();
		BlockState portalState = portal.getDefaultState().with(AXIS, axis);
		Direction dir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
		BlockPos offsets[] = new BlockPos[] { new BlockPos(0, 1, 0), new BlockPos(0, -1, 0),
				BlockPos.ZERO.offset(dir, 1), BlockPos.ZERO.offset(dir, -1) };
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
				if (isValidPortalState(world, pos)) {
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
					world.setBlockState(p, portalState);
				}
				return true;
			}
		}

		return false;
	}

	private static boolean isValidPortalState(World world, BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		return world.isAirBlock(pos) || block == Blocks.NETHER_PORTAL || block instanceof RunePortalBlock;
	}

}
