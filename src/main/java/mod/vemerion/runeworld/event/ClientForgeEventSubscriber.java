package mod.vemerion.runeworld.event;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModFluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = Main.MODID, value = Dist.CLIENT)
public class ClientForgeEventSubscriber {

	@SubscribeEvent
	public static void redBloodFog(EntityViewRenderEvent.FogColors event) {
		if (event.getInfo().getFluidState().getFluid().isEquivalentTo(ModFluids.BLOOD)) {
			event.setBlue(0.15f);
			event.setGreen(0.15f);
			event.setRed(0.7f);
		}
	}

	@SubscribeEvent
	public static void redBloodFog(EntityViewRenderEvent.FogDensity event) {
		if (event.getInfo().getFluidState().getFluid().isEquivalentTo(ModFluids.BLOOD)) {
			event.setDensity(0.35f);
			event.setCanceled(true);
		}
	}

//	@SubscribeEvent
//	public static void loadDimensionRenderInfo(WorldEvent.Load event) {
//		if (event.getWorld() instanceof ClientWorld) {
//			ClientWorld world = (ClientWorld) event.getWorld();
//			if (world.getDimensionKey().equals(ModDimensions.BLOOD)) {
//				world.func_239132_a_().setWeatherRenderHandler(new BloodRenderer.WeatherRenderer());
//			}
//		}
//	}
//
//	@SubscribeEvent
//	public static void unloadDimensionRenderInfo(WorldEvent.Unload event) {
//		if (event.getWorld() instanceof ClientWorld) {
//			ClientWorld world = (ClientWorld) event.getWorld();
//
//			if (world.getDimensionKey().equals(ModDimensions.BLOOD)) {
//				world.func_239132_a_().setWeatherRenderHandler(null);
//			}
//		}
//	}
}
