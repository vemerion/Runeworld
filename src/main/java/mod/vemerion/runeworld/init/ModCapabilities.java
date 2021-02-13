package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.capability.RuneTeleport;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModCapabilities {

	@SubscribeEvent
	public static void setup(FMLCommonSetupEvent event) {
		CapabilityManager.INSTANCE.register(RuneTeleport.class, new RuneTeleport.Storage(), RuneTeleport::new);
	}
}
