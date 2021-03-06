package mod.vemerion.runeworld.datagen;

import com.google.common.collect.ImmutableList;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider {

	public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider,
			ExistingFileHelper existingFileHelper) {
		super(dataGenerator, blockTagProvider, Main.MODID, existingFileHelper);
	}

	@Override
	protected void registerTags() {
		stoneCraftingTools();
	}

	private void stoneCraftingTools() {
		ImmutableList.of(ModBlocks.BLOOD_ROCK.asItem(), ModBlocks.CHARRED_STONE.BLOCK.asItem()).forEach(stone -> {
			getOrCreateBuilder(ItemTags.STONE_CRAFTING_MATERIALS).add(stone);
			getOrCreateBuilder(ItemTags.STONE_TOOL_MATERIALS).add(stone);
		});
	}

}
