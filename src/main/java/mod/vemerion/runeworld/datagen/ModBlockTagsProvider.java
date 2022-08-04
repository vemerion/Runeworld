package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {


	public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
		super(generatorIn, Main.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		tag(BlockTags.WALLS).add(ModBlocks.SPARKSTONE.WALL);
		tag(BlockTags.WALLS).add(ModBlocks.CHARRED_STONE.WALL);
	}

}
