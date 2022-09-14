package mod.vemerion.runeworld.event;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.capability.RuneTeleport;
import mod.vemerion.runeworld.network.BloodKnightSpecialAttackMessage;
import mod.vemerion.runeworld.network.Network;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModEventSubscriber {

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.register(RuneTeleport.class);
	}

	@SubscribeEvent
	public static void setup(FMLCommonSetupEvent event) {
		Network.INSTANCE.registerMessage(0, BloodKnightSpecialAttackMessage.class, BloodKnightSpecialAttackMessage::encode,
				BloodKnightSpecialAttackMessage::decode, BloodKnightSpecialAttackMessage::handle);
	}
}
