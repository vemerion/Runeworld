package mod.vemerion.runeworld.event;

import mod.vemerion.runeworld.Main;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = Main.MODID)
public class ForgeEventSubscriber {
	
	// For testing world gen features
//	@SubscribeEvent
//	public static void onBiomeLoad(BiomeLoadingEvent event) {
//		event.getGeneration().withFeature(GenerationStage.Decoration.LAKES, ModConfiguredFeatures.BLOOD_POOL);
//	}
}
