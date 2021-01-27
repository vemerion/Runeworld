package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, Main.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		simpleBlock(ModBlocks.BLOOD, empty("blood"));
		simpleBlock(ModBlocks.BLOOD_FLOWER, models().singleTexture("blood_flower", mcLoc("block/cross"), "cross",
				modLoc("block/blood_flower")));
	}

	private BlockModelBuilder empty(String name) {
		return models().getBuilder(name);
	}

}
