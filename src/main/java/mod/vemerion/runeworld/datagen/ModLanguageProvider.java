package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBiomes;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModEffects;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

	public ModLanguageProvider(DataGenerator gen) {
		super(gen, Main.MODID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add(ModBlocks.BLOOD, "Blood");
		add(ModBlocks.BLOOD_FLOWER, "Blood Flower");
		add(ModBlocks.BLOOD_PILLAR_LARGE, "Blood Pillar Large");
		add(ModBlocks.BLOOD_PILLAR_MEDIUM, "Blood Pillar Medium");
		add(ModBlocks.BLOOD_PILLAR_SMALL, "Blood Pillar Small");
		add(ModBlocks.BLOOD_ROCK, "Blood Rock");
		add(ModBlocks.BLOOD_MOSS, "Blood Moss");
		add(ModItems.BLOOD_BUCKET, "Blood Bucket");
		add(ModItems.BLOOD_PUDDING, "Blood Pudding");
		add(ModEffects.BLOOD_DRAINED, "Blood Drained");
		add(ModBiomes.BLOOD_PLAINS, "Blood Plains");
	}

	private void add(Biome key, String name) {
		add("biome." + key.getRegistryName().getNamespace() + "." + key.getRegistryName().getPath(), name);
	}
}
