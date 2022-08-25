package mod.vemerion.runeworld.datagen;

import com.google.common.collect.ImmutableList;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.complex.StoneMaterial;
import mod.vemerion.runeworld.init.ModBiomes;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModConfiguredStructures;
import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.ModStructures;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ConfiguredStructureTagsProvider;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModTagsProviders {
	public static class ModBiomeTagsProvider extends BiomeTagsProvider {

		public ModBiomeTagsProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
			super(dataGenerator, Main.MODID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			tag(ModConfiguredStructures.hasStructure(ModStructures.BLOOD_BAT_LAIR)).add(ModBiomes.BLOOD_PLAINS.get());
			tag(ModConfiguredStructures.hasStructure(ModStructures.FIRE_RITUAL)).add(ModBiomes.FIRE_PLAINS.get());
			tag(ModConfiguredStructures.hasStructure(ModStructures.BLOOD_GORILLA_THRONE)).add(ModBiomes.BLOOD_PLAINS.get());
			tag(ModConfiguredStructures.hasStructure(ModStructures.BLOOD_MONKEY_TUNNELS)).add(ModBiomes.BLOOD_PLAINS.get());
		}
	}
	
	public static class ModItemTagsProvider extends ItemTagsProvider {

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
	
	public static class ModFluidTagsProvider extends FluidTagsProvider {

		public ModFluidTagsProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
			super(dataGenerator, Main.MODID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			tag(FluidTags.WATER).add(ModFluids.BLOOD.get(), ModFluids.BLOOD_FLOWING.get());
		}

	}
	
	public static class ModBlockTagsProvider extends BlockTagsProvider {


		public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
			super(generatorIn, Main.MODID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.BLOOD_PILLAR_LARGE.get(), ModBlocks.BLOOD_PILLAR_MEDIUM.get(), ModBlocks.BLOOD_PILLAR_SMALL.get(), ModBlocks.BLOOD_MOSS.get(), ModBlocks.BLOOD_ROCK.get(), ModBlocks.HIDEABLE_BLOOD_ROCK.get(), ModBlocks.HIDEABLE_BLOOD_ROCK_BRICKS.get(), ModBlocks.FIRE_RITUAL_STONE.get());
			tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.CHARRED_DIRT.get(), ModBlocks.BURNT_DIRT.get());
			tag(BlockTags.CLIMBABLE).add(ModBlocks.FLESH_EATING_PLANT_STEM.get(), ModBlocks.FLESH_EATING_PLANT_FLOWER.get());
			
			stoneMaterial(ModBlocks.CHARRED_STONE_MATERIAL);
			stoneMaterial(ModBlocks.SPARKSTONE_MATERIAL);
			stoneMaterial(ModBlocks.BLOOD_ROCK_MATERIAL);
			stoneMaterial(ModBlocks.BLOOD_ROCK_BRICKS_MATERIAL);
		}
		
		private void stoneMaterial(StoneMaterial material) {
			tag(BlockTags.WALLS).add(material.wall());
			for (var block : material.getBlocks()) {
				tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
			}
		}

	}
	
	public static class ModStructureTagsProvider extends ConfiguredStructureTagsProvider {


		public ModStructureTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
			super(generatorIn, Main.MODID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			tag(ModConfiguredStructures.BLOOD_MONKEY_TUNNELS_TAG).add(ModConfiguredStructures.BLOOD_MONKEY_TUNNELS.get());
		}
	}
}

