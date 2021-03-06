package mod.vemerion.runeworld.datagen;

import java.util.ArrayList;
import java.util.List;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.complex.StoneMaterial;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockLootTables extends BlockLootTables {

	@Override
	protected Iterable<Block> getKnownBlocks() {
		List<Block> blocks = new ArrayList<>();
		for (Block block : ForgeRegistries.BLOCKS) {
			if (block != null)
				if (block.getRegistryName().getNamespace().equals(Main.MODID)) {
					blocks.add(block);
				}
		}
		return blocks;
	}

	@Override
	protected void addTables() {
		registerDropSelfLootTable(ModBlocks.BLOOD_FLOWER);
		registerDropSelfLootTable(ModBlocks.BLOOD_PILLAR_LARGE);
		registerDropSelfLootTable(ModBlocks.BLOOD_PILLAR_MEDIUM);
		registerDropSelfLootTable(ModBlocks.BLOOD_PILLAR_SMALL);
		registerDropSelfLootTable(ModBlocks.BLOOD_ROCK);
		registerDropSelfLootTable(ModBlocks.BLOOD_MOSS);
		registerDropSelfLootTable(ModBlocks.BLOOD_CRYSTAL);
		registerDropSelfLootTable(ModBlocks.BLOOD_LEECH);
		registerDropSelfLootTable(ModBlocks.CHARRED_DIRT);
		registerDropSelfLootTable(ModBlocks.FIRE_RITUAL_STONE);
		registerLootTable(ModBlocks.BURNT_DIRT, block -> {
			return droppingWithSilkTouch(block, ModBlocks.CHARRED_DIRT);
		});

		for (Block portal : ModBlocks.getRunePortals())
			registerLootTable(portal, blockNoDrop());

		stoneMaterial(ModBlocks.SPARKSTONE);
		stoneMaterial(ModBlocks.CHARRED_STONE);
	}

	private void stoneMaterial(StoneMaterial material) {
		for (Block b : material.getBlocks()) {
			registerDropSelfLootTable(b);
		}
	}
}
