package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.ModLootModifiers;
import mod.vemerion.runeworld.init.ModLootTables;
import mod.vemerion.runeworld.lootmodifier.FromLootTableLootModifier;
import net.minecraft.advancements.critereon.FluidPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModLootModifierProvider extends GlobalLootModifierProvider {

	public ModLootModifierProvider(DataGenerator gen) {
		super(gen, Main.MODID);
	}

	@Override
	protected void start() {
		add("blood_fishing", ModLootModifiers.FROM_LOOT_TABLE.get(),
				new FromLootTableLootModifier(
						new LootItemCondition[] { LocationCheck
								.checkLocation(LocationPredicate.Builder.location().setFluid(
										new FluidPredicate(null, ModFluids.BLOOD.get(), StatePropertiesPredicate.ANY)))
								.build(), LootTableIdCondition.builder(BuiltInLootTables.FISHING).build() },
						ModLootTables.BLOOD_FISHING));
	}
}
