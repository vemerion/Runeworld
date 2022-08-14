package mod.vemerion.runeworld.datagen;

import com.google.common.collect.ImmutableList;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemTagsProvider extends ItemTagsProvider {

	public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider,
			ExistingFileHelper existingFileHelper) {
		super(dataGenerator, blockTagProvider, Main.MODID, existingFileHelper);
	}

	@Override
	protected void addTags() {
		stoneCraftingTools();
	}

	private void stoneCraftingTools() {
		ImmutableList.of(ModBlocks.BLOOD_ROCK.get().asItem(), ModBlocks.CHARRED_STONE.get().asItem()).forEach(stone -> {
			tag(ItemTags.STONE_CRAFTING_MATERIALS).add(stone);
			tag(ItemTags.STONE_TOOL_MATERIALS).add(stone);
		});
	}

}
