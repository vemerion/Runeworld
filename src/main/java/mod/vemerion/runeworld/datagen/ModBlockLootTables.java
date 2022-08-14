package mod.vemerion.runeworld.datagen;

import java.util.ArrayList;
import java.util.List;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.FireRootBlock;
import mod.vemerion.runeworld.block.complex.StoneMaterial;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockLootTables extends BlockLoot {

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
		dropSelf(ModBlocks.BLOOD_FLOWER.get());
		dropSelf(ModBlocks.BLOOD_PILLAR_LARGE.get());
		dropSelf(ModBlocks.BLOOD_PILLAR_MEDIUM.get());
		dropSelf(ModBlocks.BLOOD_PILLAR_SMALL.get());
		dropSelf(ModBlocks.BLOOD_ROCK.get());
		dropSelf(ModBlocks.BLOOD_MOSS.get());
		dropSelf(ModBlocks.BLOOD_CRYSTAL.get());
		dropSelf(ModBlocks.BLOOD_LEECH.get());
		dropSelf(ModBlocks.CHARRED_DIRT.get());
		dropSelf(ModBlocks.FIRE_RITUAL_STONE.get());
		add(ModBlocks.BURNT_DIRT.get(), block -> {
			return createSingleItemTableWithSilkTouch(block, ModBlocks.CHARRED_DIRT.get());
		});

		for (Block portal : ModBlocks.getRunePortals())
			add(portal, noDrop());

		stoneMaterial(ModBlocks.SPARKSTONE_MATERIAL);
		stoneMaterial(ModBlocks.CHARRED_STONE_MATERIAL);

		fireRoot();
	}

	private void fireRoot() {
		LootItemCondition.Builder condition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.FIRE_ROOT.get())
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FireRootBlock.AGE, 7));
		add(ModBlocks.FIRE_ROOT.get(), applyExplosionDecay(ModBlocks.FIRE_ROOT.get(),
				LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModItems.FIRE_ROOT.get())))
						.withPool(LootPool.lootPool().when(condition)
								.add(LootItem.lootTableItem(ModItems.FIRE_ROOT.get()).apply(
										ApplyBonusCount.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.6f, 4))))));
	}

	private void stoneMaterial(StoneMaterial material) {
		for (Block b : material.getBlocks()) {
			dropSelf(b);
		}
	}
}
