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
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public class ModRecipeProvider extends RecipeProvider {

	public ModRecipeProvider(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		ShapelessRecipeBuilder.shapeless(ModItems.BLOOD_PUDDING.get(), 4).requires(Items.WHEAT)
				.requires(Items.PORKCHOP).requires(ModItems.BLOOD_BUCKET.get())
				.unlockedBy("has_blood_bucket", has(ModItems.BLOOD_BUCKET.get())).save(consumer);
		ShapelessRecipeBuilder.shapeless(Items.RED_DYE).requires(ModBlocks.BLOOD_FLOWER.get())
				.unlockedBy("has_blood_dye", has(ModBlocks.BLOOD_FLOWER.get()))
				.save(consumer, new ResourceLocation(Main.MODID, "blood_flower_to_dye"));
		pillar(ModBlocks.BLOOD_PILLAR_LARGE.get(), ModBlocks.BLOOD_PILLAR_MEDIUM.get(), consumer);
		pillar(ModBlocks.BLOOD_PILLAR_MEDIUM.get(), ModBlocks.BLOOD_PILLAR_SMALL.get(), consumer);
		
		var runes = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Runesword.MODID, "runes"));

		ShapelessRecipeBuilder.shapeless(ModItems.GUIDE.get()).requires(Tags.Items.OBSIDIAN).requires(runes)
				.unlockedBy("has_rune", has(runes)).unlockedBy("has_obsidian", has(Tags.Items.OBSIDIAN))
				.save(consumer);
		dislocator(ModItems.BLOOD_DISLOCATOR.get(), ModItems.BLOOD_CRYSTALLITE.get()).save(consumer);
		dislocator(ModItems.FIRE_DISLOCATOR.get(), ModItems.FIRE_HEART.get()).save(consumer);

		// Complex
		stoneMaterial(ModBlocks.SPARKSTONE_MATERIAL, consumer);
		stoneMaterial(ModBlocks.CHARRED_STONE_MATERIAL, consumer);
		stoneMaterial(ModBlocks.BLOOD_ROCK_MATERIAL, consumer);
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
		ShapelessRecipeBuilder.shapeless(small, 2).requires(large)
				.unlockedBy("has_" + largeName, has(large))
				.save(consumer, new ResourceLocation(Main.MODID, largeName + "_to_" + smallName));
	}

	private void stoneMaterial(StoneMaterial material, Consumer<FinishedRecipe> consumer) {
		stairs(material.stair(), material.block(), consumer);
		slab(material.slab(), material.block(), consumer);
		wall(material.wall(), material.block(), consumer);
	}

	private void stairs(Block stairs, Block block, Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(stairs, 4).define('#', block).pattern("#  ").pattern("## ")
				.pattern("###").unlockedBy("has_" + block.getRegistryName().getPath(), has(block))
				.save(consumer);
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(block), stairs)
				.unlockedBy("has_" + block.getRegistryName().getPath(), has(block))
				.save(consumer, stairs.getRegistryName().getPath() + "_from_" + block.getRegistryName().getPath()
						+ "_stonecutting");

	}

	private void slab(Block slab, Block block, Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(slab, 6).define('#', block).pattern("###")
				.unlockedBy("has_" + block.getRegistryName().getPath(), has(block)).save(consumer);
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(block), slab, 2)
				.unlockedBy("has_" + block.getRegistryName().getPath(), has(block))
				.save(consumer, slab.getRegistryName().getPath() + "_from_" + block.getRegistryName().getPath()
						+ "_stonecutting");
	}

	private void wall(Block wall, Block block, Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(wall, 6).define('#', block).pattern("###").pattern("###")
				.unlockedBy("has_" + block.getRegistryName().getPath(), has(block)).save(consumer);
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(block), wall)
				.unlockedBy("has_" + block.getRegistryName().getPath(), has(block))
				.save(consumer, wall.getRegistryName().getPath() + "_from_" + block.getRegistryName().getPath()
						+ "_stonecutting");
	}

}
