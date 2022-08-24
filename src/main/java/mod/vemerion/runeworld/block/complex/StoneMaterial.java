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
	private final RegistryObject<Block> base; // Ex: Stone is base of stone bricks

	public StoneMaterial(RegistryObject<Block> block, RegistryObject<StairBlock> stair, RegistryObject<SlabBlock> slab,
			RegistryObject<WallBlock> wall) {
		this(block, stair, slab, wall, null);
	}

	public StoneMaterial(RegistryObject<Block> block, RegistryObject<StairBlock> stair, RegistryObject<SlabBlock> slab,
			RegistryObject<WallBlock> wall, RegistryObject<Block> base) {
		this.block = block;
		this.stair = stair;
		this.slab = slab;
		this.wall = wall;
		this.base = base;
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
	
	public boolean hasBase() {
		return base != null;
	}
	
	public Block base() {
		return base.get();
	}

	public String getName() {
		return block.getId().getPath();
	}

	public Block[] getBlocks() {
		return new Block[] { block.get(), stair.get(), slab.get(), wall.get() };
	}
}
