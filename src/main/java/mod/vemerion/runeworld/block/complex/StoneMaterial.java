package mod.vemerion.runeworld.block.complex;

import mod.vemerion.runeworld.init.Init;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;

public class StoneMaterial {

	public final String NAME;
	public final Block BLOCK;
	public final StairsBlock STAIRS;
	public final SlabBlock SLAB;
	public final WallBlock WALL;

	public StoneMaterial(String name) {
		this.NAME = name;
		this.BLOCK = Init.setup(new Block(AbstractBlock.Properties.from(Blocks.STONE)), NAME);
		this.STAIRS = Init.setup(new StairsBlock(() -> BLOCK.getDefaultState(), AbstractBlock.Properties.from(BLOCK)), NAME + "_stairs");
		this.SLAB = Init.setup(new SlabBlock(AbstractBlock.Properties.from(BLOCK)), NAME + "_slab");
		this.WALL = Init.setup(new WallBlock(AbstractBlock.Properties.from(BLOCK)), NAME + "_wall");
	}

	public Block[] getBlocks() {
		return new Block[] { BLOCK, STAIRS, SLAB, WALL };
	}
}
