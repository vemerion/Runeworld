package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
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
		simpleItem(ModItems.BLOOD_LEECH);
		simpleItem(ModItems.GUIDE);
		simpleItem(ModItems.BLOOD_CRYSTALLITE);
		simpleItem(ModItems.BLOOD_PEBBLE);
		simpleItem(ModItems.BLOOD_FLOWER, BLOCK_FOLDER);
		simpleItem(ModItems.BLOOD_CRYSTAL, BLOCK_FOLDER);
		ModEntities.getSpawnEggs().forEach(egg -> spawnEgg(egg));
		bloodDislocator();
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

	private void bloodDislocator() {
		String name = ModItems.BLOOD_DISLOCATOR.getRegistryName().getPath();
		ResourceLocation property = new ResourceLocation(Main.MODID, "dimension");
		ModelFile red = singleTexture(name + "_red", mcLoc(ITEM_FOLDER + "/generated"), "layer0",
				modLoc(ITEM_FOLDER + "/" + name + "_red"));
		ModelFile blue = singleTexture(name + "_blue", mcLoc(ITEM_FOLDER + "/generated"), "layer0",
				modLoc(ITEM_FOLDER + "/" + name + "_blue"));
		singleTexture(name, mcLoc(ITEM_FOLDER + "/generated"), "layer0", modLoc(ITEM_FOLDER + "/" + name + "_red"))
				.override().model(red).predicate(property, 0).end().override().model(blue).predicate(property, 0.75f)
				.end();
	}
}
