package mod.vemerion.runeworld.datagen;

import java.util.function.Consumer;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.complex.StoneMaterial;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.Runesword;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

public class ModRecipeProvider extends RecipeProvider {

	public ModRecipeProvider(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		ShapelessRecipeBuilder.shapeless(ModItems.BLOOD_PUDDING.get(), 4).requires(Items.WHEAT).requires(Items.PORKCHOP)
				.requires(ModItems.BLOOD_BUCKET.get()).unlockedBy("has_blood_bucket", has(ModItems.BLOOD_BUCKET.get()))
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(Items.RED_DYE).requires(ModBlocks.BLOOD_FLOWER.get())
				.unlockedBy("has_blood_dye", has(ModBlocks.BLOOD_FLOWER.get()))
				.save(consumer, new ResourceLocation(Main.MODID, "blood_flower_to_dye"));
		pillar(ModBlocks.BLOOD_PILLAR_LARGE.get(), ModBlocks.BLOOD_PILLAR_MEDIUM.get(), consumer);
		pillar(ModBlocks.BLOOD_PILLAR_MEDIUM.get(), ModBlocks.BLOOD_PILLAR_SMALL.get(), consumer);
		nineBlockStorageRecipes(consumer, ModItems.TOPAZ_SHARD.get(), ModItems.TOPAZ_GEM.get(),
				ModItems.TOPAZ_GEM.getId().toString(), null, ModItems.TOPAZ_SHARD.getId().toString(), null);
		ShapedRecipeBuilder.shaped(ModBlocks.MIRROR.get()).pattern(" # ").pattern("#G#").pattern(" # ")
				.define('#', ModItems.TOPAZ_GEM.get()).define('G', Blocks.GLASS)
				.unlockedBy("has_" + ModBlocks.TOPAZ.get().getRegistryName().getPath(), has(ModBlocks.TOPAZ.get()))
				.save(consumer);

		var runes = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Runesword.MODID, "runes"));

		ShapelessRecipeBuilder.shapeless(ModItems.GUIDE.get()).requires(Tags.Items.OBSIDIAN).requires(runes)
				.unlockedBy("has_rune", has(runes)).unlockedBy("has_obsidian", has(Tags.Items.OBSIDIAN)).save(consumer);
		dislocator(ModItems.BLOOD_DISLOCATOR.get(), ModItems.BLOOD_CRYSTALLITE.get()).save(consumer);
		dislocator(ModItems.FIRE_DISLOCATOR.get(), ModItems.FIRE_HEART.get()).save(consumer);

		// Complex
		stoneMaterial(ModBlocks.SPARKSTONE_MATERIAL, consumer);
		stoneMaterial(ModBlocks.CHARRED_STONE_MATERIAL, consumer);
		stoneMaterial(ModBlocks.BLOOD_ROCK_MATERIAL, consumer);
		stoneMaterial(ModBlocks.BLOOD_ROCK_BRICKS_MATERIAL, consumer);
		stonecutterResultFromBase(consumer, ModBlocks.BLOOD_ROCK_BRICKS.get(), ModBlocks.BLOOD_ROCK.get());
		ShapedRecipeBuilder.shaped(ModBlocks.BLOOD_ROCK_BRICKS.get(), 4).define('#', ModBlocks.BLOOD_ROCK.get())
				.pattern("##").pattern("##").unlockedBy("has_stone", has(ModBlocks.BLOOD_ROCK.get())).save(consumer);
	}

	private ShapedRecipeBuilder dislocator(Item dislocator, Item component) {
		return ShapedRecipeBuilder.shaped(dislocator).pattern(" # ").pattern("#B#").pattern(" # ")
				.define('#', Tags.Items.OBSIDIAN).define('B', component)
				.unlockedBy("has_" + component.getRegistryName().getPath(), has(component));
	}

	private void pillar(ItemLike large, ItemLike small, Consumer<FinishedRecipe> consumer) {
		String largeName = large.asItem().getRegistryName().getPath();
		String smallName = small.asItem().getRegistryName().getPath();
		ShapedRecipeBuilder.shaped(large).define('#', small).pattern("#").pattern("#")
				.unlockedBy("has_" + smallName, has(small))
				.save(consumer, new ResourceLocation(Main.MODID, smallName + "_to_" + largeName));
		ShapelessRecipeBuilder.shapeless(small, 2).requires(large).unlockedBy("has_" + largeName, has(large))
				.save(consumer, new ResourceLocation(Main.MODID, largeName + "_to_" + smallName));
	}

	private void stoneMaterial(StoneMaterial material, Consumer<FinishedRecipe> consumer) {
		stair(material.stair(), material.block(), consumer);
		slab(material.slab(), material.block(), consumer);
		wall(material.wall(), material.block(), consumer);

		if (material.hasBase()) {
			stonecutterResultFromBase(consumer, material.stair(), material.base());
			stonecutterResultFromBase(consumer, material.slab(), material.base(), 2);
			stonecutterResultFromBase(consumer, material.wall(), material.base());
		}
	}

	private void stair(Block stair, Block block, Consumer<FinishedRecipe> consumer) {
		stairBuilder(stair, Ingredient.of(block)).unlockedBy("has_" + block.getRegistryName().getPath(), has(block))
				.save(consumer);
		stonecutterResultFromBase(consumer, stair, block);

	}

	private void slab(Block slab, Block block, Consumer<FinishedRecipe> consumer) {
		slab(consumer, slab, block);
		stonecutterResultFromBase(consumer, slab, block, 2);
	}

	private void wall(Block wall, Block block, Consumer<FinishedRecipe> consumer) {
		wall(consumer, wall, block);
		stonecutterResultFromBase(consumer, wall, block);
	}

}
