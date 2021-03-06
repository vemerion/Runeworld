package mod.vemerion.runeworld.block.complex;

import mod.vemerion.runeworld.init.Init;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class StoneMaterial {

	public final String NAME;
	public final Block BLOCK;
	public final StairsBlock STAIRS;
	public final SlabBlock SLAB;
	public final WallBlock WALL;

	public StoneMaterial(String name, MaterialColor color) {
		AbstractBlock.Properties properties = AbstractBlock.Properties.create(Material.ROCK, color).setRequiresTool()
				.hardnessAndResistance(1.5f, 6);

		this.NAME = name;
		this.BLOCK = Init.setup(new Block(properties), NAME);
		this.STAIRS = Init.setup(new StairsBlock(() -> BLOCK.getDefaultState(), AbstractBlock.Properties.from(BLOCK)),
				NAME + "_stairs");
		this.SLAB = Init.setup(new SlabBlock(AbstractBlock.Properties.from(BLOCK)), NAME + "_slab");
		this.WALL = Init.setup(new WallBlock(AbstractBlock.Properties.from(BLOCK)), NAME + "_wall");
	}

	public Block[] getBlocks() {
		return new Block[] { BLOCK, STAIRS, SLAB, WALL };
	}
}
