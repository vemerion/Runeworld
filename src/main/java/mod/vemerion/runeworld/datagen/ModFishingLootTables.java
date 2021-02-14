package mod.vemerion.runeworld.datagen;

import java.util.function.BiConsumer;

import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.ModLootTables;
import mod.vemerion.runeworld.init.Runesword;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.data.loot.FishingLootTables;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.FishingPredicate;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.util.ResourceLocation;

public class ModFishingLootTables extends FishingLootTables {

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> p_accept_1_) {
		p_accept_1_.accept(ModLootTables.BLOOD_FISHING, LootTable.builder().addLootPool(LootPool.builder()
				.rolls(ConstantRange.of(1))
				.addEntry(TableLootEntry.builder(ModLootTables.BLOOD_FISHING_JUNK).weight(10).quality(-2))
				.addEntry(TableLootEntry.builder(ModLootTables.BLOOD_FISHING_TREASURE).weight(5).quality(2)
						.acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS,
								EntityPredicate.Builder.create().fishing(FishingPredicate.func_234640_a_(true)))))
				.addEntry(TableLootEntry.builder(ModLootTables.BLOOD_FISHING_FISH).weight(85).quality(-1))));

		p_accept_1_.accept(ModLootTables.BLOOD_FISHING_FISH, LootTable.builder()
				.addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(ModItems.BLOOD_LEECH))));

		p_accept_1_.accept(ModLootTables.BLOOD_FISHING_JUNK,
				LootTable.builder()
						.addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(ModItems.BLOOD_CRYSTAL))
								.addEntry(ItemLootEntry.builder(ModItems.BLOOD_FLOWER))));

		p_accept_1_.accept(ModLootTables.BLOOD_FISHING_TREASURE,
				LootTable.builder()
						.addLootPool(LootPool.builder().addEntry(ItemLootEntry.builder(Runesword.BLOOD_RUNE))
								.addEntry(ItemLootEntry.builder(ModItems.BLOOD_BAT_TOOTH))
								.addEntry(ItemLootEntry.builder(ModItems.MOSQUITO_EGGS))));

	}
}
