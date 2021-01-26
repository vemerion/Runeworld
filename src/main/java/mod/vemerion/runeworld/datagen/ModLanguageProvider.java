package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

	public ModLanguageProvider(DataGenerator gen) {
		super(gen, Main.MODID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add(ModBlocks.BLOOD, "Blood");
	}

}
