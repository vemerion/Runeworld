package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.complex.StoneMaterial;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTagsProvider extends BlockTagsProvider {


	public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
		super(generatorIn, Main.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.BLOOD_PILLAR_LARGE, ModBlocks.BLOOD_PILLAR_MEDIUM, ModBlocks.BLOOD_PILLAR_SMALL, ModBlocks.BLOOD_MOSS, ModBlocks.BLOOD_ROCK, ModBlocks.HIDEABLE_BLOOD_ROCK, ModBlocks.FIRE_RITUAL_STONE);
		tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.CHARRED_DIRT, ModBlocks.BURNT_DIRT);
		
		stoneMaterial(ModBlocks.CHARRED_STONE);
		stoneMaterial(ModBlocks.SPARKSTONE);
	}
	
	private void stoneMaterial(StoneMaterial material) {
		tag(BlockTags.WALLS).add(material.WALL);
		for (var block : material.getBlocks()) {
			tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
		}
	}

}
