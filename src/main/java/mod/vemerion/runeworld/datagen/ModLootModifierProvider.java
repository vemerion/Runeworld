package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModLootModifiers;
import mod.vemerion.runeworld.init.ModLootTables;
import mod.vemerion.runeworld.lootmodifier.FromLootTableLootModifier;
import mod.vemerion.runeworld.lootmodifier.lootcondition.BloodFishingCondition;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModLootModifierProvider extends GlobalLootModifierProvider {

	public ModLootModifierProvider(DataGenerator gen) {
		super(gen, Main.MODID);
	}

	@Override
	protected void start() {
		add("blood_fishing", ModLootModifiers.BLOOD_FISHING, new FromLootTableLootModifier(
				new LootItemCondition[] { new BloodFishingCondition() }, ModLootTables.BLOOD_FISHING));
	}

}
