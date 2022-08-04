package mod.vemerion.runeworld.lootmodifier.lootcondition;

import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.ModLootConditions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class BloodFishingCondition implements LootItemCondition {
	private static final BloodFishingCondition INSTANCE = new BloodFishingCondition();

	public BloodFishingCondition() {
	}

	@Override
	public boolean test(LootContext t) {
		for (LootContextParam<?> p : LootContextParamSets.FISHING.getRequired())
			if (!t.hasParam(p))
				return false;
		Set<LootContextParam<?>> allowed = LootContextParamSets.FISHING.getAllowed();
		for (LootContextParam<?> p : LootContextParamSets.ALL_PARAMS.getAllowed())
			if (t.hasParam(p) && !allowed.contains(p))
				return false;

		if (!(t.getParamOrNull(LootContextParams.TOOL).getItem() instanceof FishingRodItem))
			return false;

		if (!t.getLevel().getFluidState(new BlockPos(t.getParamOrNull(LootContextParams.ORIGIN))).getType()
				.isSame(ModFluids.BLOOD.get()))
			return false;

		return true;
	}

	@Override
	public LootItemConditionType getType() {
		return ModLootConditions.BLOOD_FISHING;
	}

	public static class ConditionSerializer implements Serializer<BloodFishingCondition> {
		public void serialize(JsonObject json, BloodFishingCondition instance, JsonSerializationContext p_230424_3_) {
		}

		public BloodFishingCondition deserialize(JsonObject json, JsonDeserializationContext p_230423_2_) {
			return INSTANCE;
		}
	}

}
