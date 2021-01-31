package mod.vemerion.runeworld.event;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModParticleTypes;
import mod.vemerion.runeworld.particle.DrippingBloodFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID, value = Dist.CLIENT)
public class ClientModEventSubscriber {

	@SubscribeEvent
	public static void onRegisterParticleFactory(ParticleFactoryRegisterEvent event) {
		Minecraft mc = Minecraft.getInstance();

		mc.particles.registerFactory(ModParticleTypes.DRIPPING_BLOOD, (s) -> new DrippingBloodFactory(s));

	}
	
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(ModBlocks.BLOOD_FLOWER, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BLOOD_CRYSTAL, RenderType.getCutout());
	}
}
