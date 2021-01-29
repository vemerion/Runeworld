package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.biome.BloodBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModBiomes {

	public static final Biome BLOOD_BIOME = null;

	@SubscribeEvent
	public static void onRegisterBiome(RegistryEvent.Register<Biome> event) {
		ModFeatures.init();
		
		event.getRegistry().register(Init.setup(new BloodBiome().create(), "blood_biome"));
	}
}
