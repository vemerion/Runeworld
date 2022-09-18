package mod.vemerion.runeworld.datagen;

import java.util.ArrayList;
import java.util.List;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.CairnBlock;
import mod.vemerion.runeworld.block.FireRootBlock;
import mod.vemerion.runeworld.block.FleshEatingPlantBlock;
import mod.vemerion.runeworld.block.complex.StoneMaterial;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
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
		dropSelf(ModBlocks.BLOOD_MOSS.get());
		dropSelf(ModBlocks.BLOOD_CRYSTAL.get());
		dropSelf(ModBlocks.BLOOD_LEECH.get());
		dropSelf(ModBlocks.CHARRED_DIRT.get());
		dropSelf(ModBlocks.FIRE_RITUAL_STONE.get());
		dropSelf(ModBlocks.TOPAZ.get());
		dropSelf(ModBlocks.CHALICE.get());
		add(ModBlocks.MIRROR.get(), createSingleItemTableWithSilkTouch(ModBlocks.MIRROR.get(),
				ModItems.TOPAZ_SHARD.get(), UniformGenerator.between(1, 3)));
		add(ModBlocks.BURNT_DIRT.get(), block -> {
			return createSingleItemTableWithSilkTouch(block, ModBlocks.CHARRED_DIRT.get());
		});

		for (Block portal : ModBlocks.getRunePortals())
			add(portal, noDrop());

		stoneMaterial(ModBlocks.SPARKSTONE_MATERIAL);
		stoneMaterial(ModBlocks.CHARRED_STONE_MATERIAL);
		stoneMaterial(ModBlocks.BLOOD_ROCK_MATERIAL);
		stoneMaterial(ModBlocks.BLOOD_ROCK_BRICKS_MATERIAL);

		fireRoot();
		cairn();

		addFromBooleanProp(ModBlocks.FLESH_EATING_PLANT_STEM.get(), ModBlocks.FLESH_EATING_PLANT_FLOWER.get(),
				FleshEatingPlantBlock.BASE, true);
		addFromBooleanProp(ModBlocks.FLESH_EATING_PLANT_FLOWER.get(), ModBlocks.FLESH_EATING_PLANT_FLOWER.get(),
				FleshEatingPlantBlock.BASE, true);
	}

	private void addFromBooleanProp(Block block, ItemLike loot, BooleanProperty property, boolean value) {
		add(block, LootTable.lootTable().withPool(applyExplosionCondition(loot,
				LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(loot)
						.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(
								StatePropertiesPredicate.Builder.properties().hasProperty(property, value)))))));
	}

	private void cairn() {
		var cairn = ModBlocks.CAIRN.get();
		var pool = LootItem.lootTableItem(cairn);
		for (int value = CairnBlock.MIN_LEVEL; value <= CairnBlock.MAX_LEVEL; value++) {
			pool.apply(SetItemCountFunction.setCount(ConstantValue.exactly(value + 1))
					.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(cairn).setProperties(
							StatePropertiesPredicate.Builder.properties().hasProperty(CairnBlock.LEVEL, value))));
		}
		add(cairn, LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(applyExplosionDecay(cairn, pool))));

	}

	private void fireRoot() {
		LootItemCondition.Builder condition = LootItemBlockStatePropertyCondition
				.hasBlockStateProperties(ModBlocks.FIRE_ROOT.get())
				.setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FireRootBlock.AGE, 7));
		add(ModBlocks.FIRE_ROOT.get(),
				applyExplosionDecay(ModBlocks.FIRE_ROOT.get(), LootTable.lootTable()
						.withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModItems.FIRE_ROOT.get())))
						.withPool(LootPool.lootPool().when(condition)
								.add(LootItem.lootTableItem(ModItems.FIRE_ROOT.get()).apply(ApplyBonusCount
										.addBonusBinomialDistributionCount(Enchantments.BLOCK_FORTUNE, 0.6f, 4))))));
	}

	private void stoneMaterial(StoneMaterial material) {
		for (Block b : material.getBlocks()) {
			dropSelf(b);
		}
	}
}
