package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBiomes;
import mod.vemerion.runeworld.init.ModConfiguredStructures;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBiomeTagsProvider extends BiomeTagsProvider {

	public ModBiomeTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider,
			ExistingFileHelper existingFileHelper) {
		super(dataGenerator, Main.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		tag(ModConfiguredStructures.HAS_BLOOD_BAT_LAIR).add(ModBiomes.BLOOD_PLAINS);
		tag(ModConfiguredStructures.HAS_FIRE_RITUAL).add(ModBiomes.FIRE_PLAINS);
		tag(ModConfiguredStructures.HAS_BLOOD_GORILLA_THRONE).add(ModBiomes.BLOOD_PLAINS);
	}
}
