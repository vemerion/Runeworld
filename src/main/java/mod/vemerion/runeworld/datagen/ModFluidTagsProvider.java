package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModFluids;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModFluidTagsProvider extends FluidTagsProvider {

	public ModFluidTagsProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
		super(dataGenerator, Main.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		tag(FluidTags.WATER).add(ModFluids.BLOOD.get(), ModFluids.BLOOD_FLOWING.get());
	}

}
