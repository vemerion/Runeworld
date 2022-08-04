package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.lootmodifier.lootcondition.BloodFishingCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModLootConditions {

	public static LootItemConditionType BLOOD_FISHING;

	public static void register() {
		BLOOD_FISHING = Registry.register(Registry.LOOT_CONDITION_TYPE,
				new ResourceLocation(Main.MODID, "blood_fishing"),
				new LootItemConditionType(new BloodFishingCondition.ConditionSerializer()));
	}
}
