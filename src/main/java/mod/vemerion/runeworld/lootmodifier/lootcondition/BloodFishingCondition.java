package mod.vemerion.runeworld.lootmodifier.lootcondition;

import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.ModLootConditions;
import net.minecraft.item.FishingRodItem;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.math.BlockPos;

public class BloodFishingCondition implements ILootCondition {
	private static final BloodFishingCondition INSTANCE = new BloodFishingCondition();

	public BloodFishingCondition() {
	}

	@Override
	public boolean test(LootContext t) {
		for (LootParameter<?> p : LootParameterSets.FISHING.getRequiredParameters())
			if (!t.has(p))
				return false;
		Set<LootParameter<?>> allowed = LootParameterSets.FISHING.getAllParameters();
		for (LootParameter<?> p : LootParameterSets.GENERIC.getAllParameters())
			if (t.has(p) && !allowed.contains(p))
				return false;

		if (!(t.get(LootParameters.TOOL).getItem() instanceof FishingRodItem))
			return false;

		if (!t.getWorld().getFluidState(new BlockPos(t.get(LootParameters.field_237457_g_))).getFluid()
				.isEquivalentTo(ModFluids.BLOOD))
			return false;

		return true;
	}

	@Override
	public LootConditionType func_230419_b_() {
		return ModLootConditions.BLOOD_FISHING;
	}

	public static class Serializer implements ILootSerializer<BloodFishingCondition> {
		public void serialize(JsonObject json, BloodFishingCondition instance, JsonSerializationContext p_230424_3_) {
		}

		public BloodFishingCondition deserialize(JsonObject json, JsonDeserializationContext p_230423_2_) {
			return INSTANCE;
		}
	}

}
