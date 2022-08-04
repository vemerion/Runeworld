package mod.vemerion.runeworld.block.complex;

import mod.vemerion.runeworld.init.Init;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class StoneMaterial {

	public final String NAME;
	public final Block BLOCK;
	public final StairBlock STAIRS;
	public final SlabBlock SLAB;
	public final WallBlock WALL;

	public StoneMaterial(String name, MaterialColor color) {
		BlockBehaviour.Properties properties = BlockBehaviour.Properties.of(Material.STONE, color).requiresCorrectToolForDrops()
				.strength(1.5f, 6);

		this.NAME = name;
		this.BLOCK = Init.setup(new Block(properties), NAME);
		this.STAIRS = Init.setup(new StairBlock(() -> BLOCK.defaultBlockState(), BlockBehaviour.Properties.copy(BLOCK)),
				NAME + "_stairs");
		this.SLAB = Init.setup(new SlabBlock(BlockBehaviour.Properties.copy(BLOCK)), NAME + "_slab");
		this.WALL = Init.setup(new WallBlock(BlockBehaviour.Properties.copy(BLOCK)), NAME + "_wall");
	}

	public Block[] getBlocks() {
		return new Block[] { BLOCK, STAIRS, SLAB, WALL };
	}
}
