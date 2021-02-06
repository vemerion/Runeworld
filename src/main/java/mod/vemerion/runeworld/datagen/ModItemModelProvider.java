package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Main.MODID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		simpleItem(ModItems.BLOOD_BUCKET);
		simpleItem(ModItems.BLOOD_PUDDING);
		simpleItem(ModItems.MOSQUITO_EGGS);
		simpleItem(ModItems.BLOOD_BAT_TOOTH);
		simpleItem(ModItems.BLOOD_FLOWER, BLOCK_FOLDER);
		simpleItem(ModItems.BLOOD_CRYSTAL, BLOCK_FOLDER);
		spawnEgg(ModEntities.MOSQUITO_SPAWN_EGG);
		spawnEgg(ModEntities.BLOOD_BAT_SPAWN_EGG);
	}

	private void spawnEgg(Item item) {
		withExistingParent(item.getRegistryName().getPath(), mcLoc(ITEM_FOLDER + "/template_spawn_egg"));
	}

	private void simpleItem(Item item) {
		simpleItem(item, ITEM_FOLDER);
	}

	private void simpleItem(Item item, String folder) {
		String name = item.getRegistryName().getPath();
		singleTexture(name, mcLoc(ITEM_FOLDER + "/generated"), "layer0", modLoc(folder + "/" + name));
	}
}
