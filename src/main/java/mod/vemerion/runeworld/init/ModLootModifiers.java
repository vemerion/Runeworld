package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.lootmodifier.FromLootTableLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModLootModifiers {

	public static final GlobalLootModifierSerializer<FromLootTableLootModifier> BLOOD_FISHING = null;

	@SubscribeEvent
	public static void onRegisterLootModifier(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
		event.getRegistry().register(Init.setup(new FromLootTableLootModifier.Serializer(), "blood_fishing"));
	}
}
