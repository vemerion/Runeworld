package mod.vemerion.runeworld.datagen;

import java.util.function.Consumer;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

public class ModRecipeProvider extends RecipeProvider {

	public ModRecipeProvider(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapelessRecipeBuilder.shapelessRecipe(ModItems.BLOOD_PUDDING, 4).addIngredient(Items.WHEAT)
				.addIngredient(Items.PORKCHOP).addIngredient(ModItems.BLOOD_BUCKET)
				.addCriterion("has_blood_bucket", hasItem(ModItems.BLOOD_BUCKET)).build(consumer);
		ShapelessRecipeBuilder.shapelessRecipe(Items.RED_DYE).addIngredient(ModItems.BLOOD_FLOWER)
				.addCriterion("has_blood_dye", hasItem(ModItems.BLOOD_FLOWER))
				.build(consumer, new ResourceLocation(Main.MODID, "blood_flower_to_dye"));
		pillar(ModItems.BLOOD_PILLAR_LARGE, ModItems.BLOOD_PILLAR_MEDIUM, consumer);
		pillar(ModItems.BLOOD_PILLAR_MEDIUM, ModItems.BLOOD_PILLAR_SMALL, consumer);
	}

	private void pillar(Item large, Item small, Consumer<IFinishedRecipe> consumer) {
		String largeName = large.getRegistryName().getPath();
		String smallName = small.getRegistryName().getPath();
		ShapedRecipeBuilder.shapedRecipe(large).key('#', small).patternLine("#").patternLine("#")
				.addCriterion("has_" + smallName, hasItem(small))
				.build(consumer, new ResourceLocation(Main.MODID, smallName + "_to_" + largeName));
		ShapelessRecipeBuilder.shapelessRecipe(small, 2).addIngredient(large)
				.addCriterion("has_" + largeName, hasItem(large))
				.build(consumer, new ResourceLocation(Main.MODID, largeName + "_to_" + smallName));
	}

}
