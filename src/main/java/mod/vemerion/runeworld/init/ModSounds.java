package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModSounds {

	public static final SoundEvent BLOOD_BAT_DEATH = null;
	public static final SoundEvent BLOOD_BAT_SNORING = null;
	public static final SoundEvent BLOOD_BAT_WINGS = null;
	public static final SoundEvent MOSQUITO_FLYING = null;
	public static final SoundEvent MOSQUITO_SPLASH = null;
	public static final SoundEvent PORTAL = null;
	public static final SoundEvent MONKEY_AMBIENT = null;
	public static final SoundEvent MONKEY_DEATH = null;
	public static final SoundEvent THROWING = null;
	public static final SoundEvent SIZZLE = null;
	public static final SoundEvent BLOOD_DROP = null;
                                                

	@SubscribeEvent
	public static void onRegisterSound(RegistryEvent.Register<SoundEvent> event) {
		SoundEvent blood_bat_death = new SoundEvent(new ResourceLocation(Main.MODID, "blood_bat_death"));
		event.getRegistry().register(Init.setup(blood_bat_death, "blood_bat_death"));
		SoundEvent blood_bat_snoring = new SoundEvent(new ResourceLocation(Main.MODID, "blood_bat_snoring"));
		event.getRegistry().register(Init.setup(blood_bat_snoring, "blood_bat_snoring"));
		SoundEvent blood_bat_wings = new SoundEvent(new ResourceLocation(Main.MODID, "blood_bat_wings"));
		event.getRegistry().register(Init.setup(blood_bat_wings, "blood_bat_wings"));
		SoundEvent mosquito_flying = new SoundEvent(new ResourceLocation(Main.MODID, "mosquito_flying"));
		event.getRegistry().register(Init.setup(mosquito_flying, "mosquito_flying"));
		SoundEvent mosquito_splash = new SoundEvent(new ResourceLocation(Main.MODID, "mosquito_splash"));
		event.getRegistry().register(Init.setup(mosquito_splash, "mosquito_splash"));
		SoundEvent portal = new SoundEvent(new ResourceLocation(Main.MODID, "portal"));
		event.getRegistry().register(Init.setup(portal, "portal"));  
		SoundEvent monkey_ambient = new SoundEvent(new ResourceLocation(Main.MODID, "monkey_ambient"));
		event.getRegistry().register(Init.setup(monkey_ambient, "monkey_ambient"));
		SoundEvent monkey_death = new SoundEvent(new ResourceLocation(Main.MODID, "monkey_death"));
		event.getRegistry().register(Init.setup(monkey_death, "monkey_death"));
		SoundEvent throwing = new SoundEvent(new ResourceLocation(Main.MODID, "throwing"));
		event.getRegistry().register(Init.setup(throwing, "throwing")); 
		SoundEvent sizzle = new SoundEvent(new ResourceLocation(Main.MODID, "sizzle"));
		event.getRegistry().register(Init.setup(sizzle, "sizzle"));
		SoundEvent blood_drop = new SoundEvent(new ResourceLocation(Main.MODID, "blood_drop"));
		event.getRegistry().register(Init.setup(blood_drop, "blood_drop"));
	}
}
