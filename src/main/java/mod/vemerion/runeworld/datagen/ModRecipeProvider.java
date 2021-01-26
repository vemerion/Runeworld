package mod.vemerion.runeworld.datagen;

import java.util.function.Consumer;

import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;

public class ModRecipeProvider extends RecipeProvider {

	public ModRecipeProvider(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapelessRecipeBuilder.shapelessRecipe(ModItems.BLOOD_PUDDING, 4).addIngredient(Items.WHEAT)
				.addIngredient(Items.PORKCHOP).addIngredient(ModItems.BLOOD_BUCKET)
				.addCriterion("has_blood_bucket", hasItem(ModItems.BLOOD_BUCKET)).build(consumer);
	}

}
