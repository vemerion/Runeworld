package mod.vemerion.runeworld.datagen;

import java.util.function.BiConsumer;

import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.ModLootTables;
import mod.vemerion.runeworld.init.Runesword;
import net.minecraft.data.loot.ChestLootTables;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;

public class ModChestLootTables extends ChestLootTables {
	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> consumer) {
		consumer.accept(ModLootTables.CHEST_BLOOD_BAT_LAIR,
				LootTable.builder()
						.addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
								.addEntry(ItemLootEntry.builder(ModItems.BLOOD_CRYSTALLITE)))
						.addLootPool(LootPool.builder().rolls(RandomValueRange.of(3, 5))
								.addEntry(ItemLootEntry.builder(Items.IRON_INGOT).weight(10)
										.acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 5.0F))))
								.addEntry(ItemLootEntry.builder(Items.GOLD_INGOT).weight(5)
										.acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F))))
								.addEntry(ItemLootEntry.builder(Items.REDSTONE).weight(5)
										.acceptFunction(SetCount.builder(RandomValueRange.of(4.0F, 9.0F))))
								.addEntry(ItemLootEntry.builder(Items.LAPIS_LAZULI).weight(5)
										.acceptFunction(SetCount.builder(RandomValueRange.of(4.0F, 9.0F))))
								.addEntry(ItemLootEntry.builder(Items.COAL).weight(10)
										.acceptFunction(SetCount.builder(RandomValueRange.of(3.0F, 8.0F))))
								.addEntry(ItemLootEntry.builder(Runesword.BLOOD_RUNE).weight(4))
								.addEntry(ItemLootEntry.builder(ModItems.BLOOD_BAT_TOOTH).weight(5))
								.addEntry(ItemLootEntry.builder(ModItems.BLOOD_LEECH).weight(10)
										.acceptFunction(SetCount.builder(RandomValueRange.of(2, 4))))
								.addEntry(ItemLootEntry.builder(ModItems.BLOOD_CRYSTAL).weight(10)
										.acceptFunction(SetCount.builder(RandomValueRange.of(4, 6))))));
	}
}
