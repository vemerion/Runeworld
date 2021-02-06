package mod.vemerion.runeworld.datagen;

import java.util.ArrayList;
import java.util.List;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.RandomChanceWithLooting;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityLootTables extends EntityLootTables {
	@Override
	protected void addTables() {
		registerLootTable(ModEntities.MOSQUITO,
				LootTable.builder()
						.addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
								.addEntry(ItemLootEntry.builder(ModItems.MOSQUITO_EGGS))
								.acceptCondition(KilledByPlayer.builder())
								.acceptCondition(RandomChanceWithLooting.builder(0.05f, 0.02f))));
		registerLootTable(ModEntities.BLOOD_BAT,
				LootTable.builder()
						.addLootPool(LootPool.builder().rolls(ConstantRange.of(1))
								.addEntry(ItemLootEntry.builder(ModItems.BLOOD_BAT_TOOTH)
										.acceptFunction(SetCount.builder(RandomValueRange.of(0, 1)))
										.acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0, 1))))));
	}

	@Override
	protected Iterable<EntityType<?>> getKnownEntities() {
		List<EntityType<?>> entities = new ArrayList<>();
		for (EntityType<?> type : ForgeRegistries.ENTITIES) {
			if (type != null && !isNonLiving(type))
				if (type.getRegistryName().getNamespace().equals(Main.MODID)) {
					entities.add(type);
				}
		}
		return entities;
	}
}
