package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
	
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Main.MODID);

	public static final RegistryObject<SoundEvent> BLOOD_BAT_DEATH = SOUNDS.register("blood_bat_death", () -> new SoundEvent(new ResourceLocation(Main.MODID, "blood_bat_death")));
	public static final RegistryObject<SoundEvent> BLOOD_BAT_SNORING = SOUNDS.register("blood_bat_snoring", () -> new SoundEvent(new ResourceLocation(Main.MODID, "blood_bat_snoring")));
	public static final RegistryObject<SoundEvent> BLOOD_BAT_WINGS = SOUNDS.register("blood_bat_wings", () -> new SoundEvent(new ResourceLocation(Main.MODID, "blood_bat_wings")));
	public static final RegistryObject<SoundEvent> MOSQUITO_FLYING = SOUNDS.register("mosquito_flying", () -> new SoundEvent(new ResourceLocation(Main.MODID, "mosquito_flying")));
	public static final RegistryObject<SoundEvent> MOSQUITO_SPLASH = SOUNDS.register("mosquito_splash", () -> new SoundEvent(new ResourceLocation(Main.MODID, "mosquito_splash")));
	public static final RegistryObject<SoundEvent> PORTAL = SOUNDS.register("portal", () -> new SoundEvent(new ResourceLocation(Main.MODID, "portal")));
	public static final RegistryObject<SoundEvent> MONKEY_AMBIENT = SOUNDS.register("monkey_ambient", () -> new SoundEvent(new ResourceLocation(Main.MODID, "monkey_ambient")));
	public static final RegistryObject<SoundEvent> MONKEY_DEATH = SOUNDS.register("monkey_death", () -> new SoundEvent(new ResourceLocation(Main.MODID, "monkey_death")));
	public static final RegistryObject<SoundEvent> THROWING = SOUNDS.register("throwing", () -> new SoundEvent(new ResourceLocation(Main.MODID, "throwing")));
	public static final RegistryObject<SoundEvent> SIZZLE = SOUNDS.register("sizzle", () -> new SoundEvent(new ResourceLocation(Main.MODID, "sizzle")));
	public static final RegistryObject<SoundEvent> BLOOD_DROP = SOUNDS.register("blood_drop", () -> new SoundEvent(new ResourceLocation(Main.MODID, "blood_drop")));
}
