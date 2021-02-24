package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.lootmodifier.lootcondition.BloodFishingCondition;
import net.minecraft.loot.LootConditionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModLootConditions {

	public static LootConditionType BLOOD_FISHING;

	public static void register() {
		BLOOD_FISHING = Registry.register(Registry.LOOT_CONDITION_TYPE,
				new ResourceLocation(Main.MODID, "blood_fishing"),
				new LootConditionType(new BloodFishingCondition.Serializer()));
	}
}
