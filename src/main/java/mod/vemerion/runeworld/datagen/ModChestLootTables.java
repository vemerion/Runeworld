package mod.vemerion.runeworld.datagen;

import java.util.function.BiConsumer;

import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.ModLootTables;
import mod.vemerion.runeworld.init.Runesword;
import net.minecraft.data.loot.ChestLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ModChestLootTables extends ChestLoot {
	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		consumer.accept(ModLootTables.CHEST_BLOOD_BAT_LAIR,
				LootTable.lootTable()
						.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
								.add(LootItem.lootTableItem(ModItems.BLOOD_CRYSTALLITE.get())))
						.withPool(LootPool.lootPool().setRolls(UniformGenerator.between(3, 5))
								.add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(10)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
								.add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
								.add(LootItem.lootTableItem(Items.REDSTONE).setWeight(5)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 9.0F))))
								.add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(5)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 9.0F))))
								.add(LootItem.lootTableItem(Items.COAL).setWeight(10)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 8.0F))))
								.add(LootItem.lootTableItem(Runesword.BLOOD_RUNE).setWeight(4))
								.add(LootItem.lootTableItem(ModItems.BLOOD_BAT_TOOTH.get()).setWeight(5))
								.add(LootItem.lootTableItem(ModBlocks.BLOOD_LEECH.get()).setWeight(10)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4))))
								.add(LootItem.lootTableItem(ModBlocks.BLOOD_CRYSTAL.get()).setWeight(10)
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 6))))));

		consumer.accept(ModLootTables.BLOOD_MONKEY_TUNNELS_TREASURE, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.DIAMOND))));
	}
}
