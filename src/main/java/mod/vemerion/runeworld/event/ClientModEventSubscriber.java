package mod.vemerion.runeworld.event;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModParticleTypes;
import mod.vemerion.runeworld.particle.DrippingBloodFactory;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID, value = Dist.CLIENT)
public class ClientModEventSubscriber {

	@SubscribeEvent
	public static void RegisterParticleFactory(ParticleFactoryRegisterEvent event) {
		Minecraft mc = Minecraft.getInstance();

		mc.particles.registerFactory(ModParticleTypes.DRIPPING_BLOOD, (s) -> new DrippingBloodFactory(s));

	}
}
