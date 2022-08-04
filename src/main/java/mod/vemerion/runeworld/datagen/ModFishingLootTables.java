package mod.vemerion.runeworld.datagen;

import java.util.function.BiConsumer;

import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.ModLootTables;
import mod.vemerion.runeworld.init.Runesword;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.FishingHookPredicate;
import net.minecraft.data.loot.FishingLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class ModFishingLootTables extends FishingLoot {

	@Override
	public void accept(BiConsumer<ResourceLocation, Builder> p_accept_1_) {
		p_accept_1_.accept(ModLootTables.BLOOD_FISHING, LootTable.lootTable().withPool(LootPool.lootPool()
				.setRolls(ConstantValue.exactly(1))
				.add(LootTableReference.lootTableReference(ModLootTables.BLOOD_FISHING_JUNK).setWeight(10).setQuality(-2))
				.add(LootTableReference.lootTableReference(ModLootTables.BLOOD_FISHING_TREASURE).setWeight(5).setQuality(2)
						.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
								EntityPredicate.Builder.entity().fishingHook(FishingHookPredicate.inOpenWater(true)))))
				.add(LootTableReference.lootTableReference(ModLootTables.BLOOD_FISHING_FISH).setWeight(85).setQuality(-1))));

		p_accept_1_.accept(ModLootTables.BLOOD_FISHING_FISH, LootTable.lootTable()
				.withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModItems.BLOOD_LEECH))));

		p_accept_1_.accept(ModLootTables.BLOOD_FISHING_JUNK,
				LootTable.lootTable()
						.withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModItems.BLOOD_CRYSTAL))
								.add(LootItem.lootTableItem(ModItems.BLOOD_FLOWER))));

		p_accept_1_.accept(ModLootTables.BLOOD_FISHING_TREASURE,
				LootTable.lootTable()
						.withPool(LootPool.lootPool().add(LootItem.lootTableItem(Runesword.BLOOD_RUNE))
								.add(LootItem.lootTableItem(ModItems.BLOOD_BAT_TOOTH))
								.add(LootItem.lootTableItem(ModItems.MOSQUITO_EGGS))));

	}
}
