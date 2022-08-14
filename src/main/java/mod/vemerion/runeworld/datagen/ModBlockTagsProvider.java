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
		tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.BLOOD_PILLAR_LARGE.get(), ModBlocks.BLOOD_PILLAR_MEDIUM.get(), ModBlocks.BLOOD_PILLAR_SMALL.get(), ModBlocks.BLOOD_MOSS.get(), ModBlocks.BLOOD_ROCK.get(), ModBlocks.HIDEABLE_BLOOD_ROCK.get(), ModBlocks.FIRE_RITUAL_STONE.get());
		tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.CHARRED_DIRT.get(), ModBlocks.BURNT_DIRT.get());
		
		stoneMaterial(ModBlocks.CHARRED_STONE_MATERIAL);
		stoneMaterial(ModBlocks.SPARKSTONE_MATERIAL);
		stoneMaterial(ModBlocks.BLOOD_ROCK_MATERIAL);
	}
	
	private void stoneMaterial(StoneMaterial material) {
		tag(BlockTags.WALLS).add(material.wall());
		for (var block : material.getBlocks()) {
			tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
		}
	}

}
