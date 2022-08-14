package mod.vemerion.runeworld.block.complex;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.registries.RegistryObject;

public class StoneMaterial {

	private final RegistryObject<Block> block;
	private final RegistryObject<StairBlock> stair;
	private final RegistryObject<SlabBlock> slab;
	private final RegistryObject<WallBlock> wall;

	public StoneMaterial(RegistryObject<Block> block, RegistryObject<StairBlock> stair, RegistryObject<SlabBlock> slab,
			RegistryObject<WallBlock> wall) {
		this.block = block;
		this.stair = stair;
		this.slab = slab;
		this.wall = wall;
	}

	public Block block() {
		return block.get();
	}

	public StairBlock stair() {
		return stair.get();
	}

	public SlabBlock slab() {
		return slab.get();
	}

	public WallBlock wall() {
		return wall.get();
	}

	public String getName() {
		return block.getId().getPath();
	}

	public Block[] getBlocks() {
		return new Block[] { block.get(), stair.get(), slab.get(), wall.get() };
	}
}
