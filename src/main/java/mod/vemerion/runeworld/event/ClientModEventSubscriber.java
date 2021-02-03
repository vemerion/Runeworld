package mod.vemerion.runeworld.event;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModParticleTypes;
import mod.vemerion.runeworld.particle.DrippingBloodFactory;
import mod.vemerion.runeworld.renderer.MosquitoRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
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

		Minecraft mc = event.getMinecraftSupplier().get();

		RenderingRegistry.registerEntityRenderingHandler(ModEntities.MOSQUITO, MosquitoRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.MOSQUITO_EGGS,
				m -> new SpriteRenderer<>(m, mc.getItemRenderer()));
	}
}
