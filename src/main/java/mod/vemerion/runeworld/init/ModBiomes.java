package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.biome.BloodPlainsBiome;
import mod.vemerion.runeworld.biome.FirePlainsBiome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModBiomes {

	public static final Biome BLOOD_PLAINS = null;
	public static final Biome FIRE_PLAINS = null;

	@SubscribeEvent
	public static void onRegisterBiome(RegistryEvent.Register<Biome> event) {
		ModFeatures.init();
		ModStructures.init();
		
		event.getRegistry().register(Init.setup(new BloodPlainsBiome().create(), "blood_plains"));
		event.getRegistry().register(Init.setup(new FirePlainsBiome().create(), "fire_plains"));
	}
}
