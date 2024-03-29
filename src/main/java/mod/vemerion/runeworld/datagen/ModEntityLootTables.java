package mod.vemerion.runeworld.datagen;

import java.util.ArrayList;
import java.util.List;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.ModLootTables;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityLootTables extends EntityLoot {
	@Override
	protected void addTables() {
		add(ModEntities.MOSQUITO.get(),
				LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(ModItems.MOSQUITO_EGGS.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05f, 0.02f))));
		add(ModEntities.BLOOD_BAT.get(),
				LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(ModItems.BLOOD_BAT_TOOTH.get()))
						.when(LootItemKilledByPlayerCondition.killedByPlayer())
						.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05f, 0.02f))));
		add(ModEntities.BLOOD_MONKEY.get(),
				LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(ModItems.BLOOD_PEBBLE.get())
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))))
						.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
								.add(LootItem.lootTableItem(ModItems.MOSQUITO_EGGS.get()))
								.when(LootItemKilledByPlayerCondition.killedByPlayer())
								.when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.04f,
										0.01f))));

		add(ModEntities.FIRE_ELEMENTAL.get(), LootTable.lootTable()
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(ModItems.FIRE_HEART.get())))
				.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(ModBlocks.FIRE_RITUAL_STONE.get())
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
								.apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))));

		add(ModEntities.BLOOD_GORILLA.get(),
				LootTable.lootTable()
						.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
								.add(LootItem.lootTableItem(ModItems.BLOOD_CROWN.get())))
						.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
								.add(LootItem.lootTableItem(ModItems.BLOOD_CRYSTALLITE.get()))));

		add(ModEntities.TICK.get(), LootTable.lootTable());

		add(ModEntities.TOPAZ_CREATURE.get(),
				LootTable.lootTable()
						.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
								.add(LootItem.lootTableItem(ModItems.TOPAZ_SHARD.get())
										.apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5))))));

		add(ModLootTables.TOPAZ_CREATURE_POOP,
				LootTable.lootTable()
						.withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
								.add(LootItem.lootTableItem(ModItems.BLOOD_PEBBLE.get()).setWeight(10))
								.add(LootItem.lootTableItem(Items.RAW_COPPER).setWeight(10))
								.add(LootItem.lootTableItem(Items.RAW_IRON).setWeight(10))
								.add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1))
								.add(LootItem.lootTableItem(Items.EMERALD).setWeight(2))));
		
		add(ModEntities.BLOOD_KNIGHT_CLUB.get(), LootTable.lootTable());
		add(ModEntities.BLOOD_KNIGHT_SPEAR.get(), LootTable.lootTable());
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
