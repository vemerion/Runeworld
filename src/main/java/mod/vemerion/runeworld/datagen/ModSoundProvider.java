package mod.vemerion.runeworld.datagen;

import java.util.function.Supplier;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModSounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class ModSoundProvider extends SoundDefinitionsProvider {
	
	
	protected ModSoundProvider(DataGenerator generator, ExistingFileHelper helper) {
		super(generator, Main.MODID, helper);
	}
	
	@Override
	public void registerSounds() {
		addSimple(ModSounds.BLOOD_BAT_DEATH);
		addSimple(ModSounds.BLOOD_BAT_SNORING);
		addSimple(ModSounds.BLOOD_BAT_WINGS);
		addSimple(ModSounds.BLOOD_DROP);
		addSimple(ModSounds.MONKEY_AMBIENT);
		addSimple(ModSounds.MONKEY_DEATH);
		addSimple(ModSounds.MOSQUITO_FLYING);
		addSimple(ModSounds.MOSQUITO_SPLASH);
		addSimple(ModSounds.PORTAL);
		addSimple(ModSounds.SIZZLE);
		addSimple(ModSounds.THROWING);
	}

	private void addSimple(Supplier<SoundEvent> sound) {
		add(sound, definition().with(sound(sound.get().getLocation())));
	}
}
