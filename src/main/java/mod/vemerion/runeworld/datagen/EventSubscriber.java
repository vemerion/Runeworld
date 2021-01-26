package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class EventSubscriber {

	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event) {
		DataGenerator dataGenerator = event.getGenerator();

		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		if (event.includeClient()) {
			dataGenerator.addProvider(new ModLanguageProvider(dataGenerator));
			dataGenerator.addProvider(new ModBlockStateProvider(dataGenerator, existingFileHelper));
			dataGenerator.addProvider(new ModItemModelProvider(dataGenerator, existingFileHelper));
		}
		if (event.includeServer()) {
		}
	}
}
