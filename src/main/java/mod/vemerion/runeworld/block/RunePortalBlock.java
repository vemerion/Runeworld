package mod.vemerion.runeworld.block;

import java.util.Random;

import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.particle.RunePortalParticleData;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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

public class RunePortalBlock extends Block {
	public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
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
			worldIn.addParticle(new RunePortalParticleData(red / 255f, green / 255f, blue / 255f), position.x, position.y, position.z,
					speed.x, speed.y, speed.z);
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

}
