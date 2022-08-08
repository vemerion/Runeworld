package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.complex.StoneMaterial;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
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
		simpleItem(ModItems.GRILLED_BLOOD_LEECH);
		simpleItem(ModItems.FIRE_HEART);
		simpleItem(ModItems.FIRE_ROOT);
		simpleItem(ModItems.BLOOD_FLOWER, BLOCK_FOLDER);
		simpleItem(ModItems.BLOOD_CRYSTAL, BLOCK_FOLDER);
		dislocator(ModItems.BLOOD_DISLOCATOR);
		dislocator(ModItems.FIRE_DISLOCATOR);
		
		ModItems.Deferred.ITEMS.getEntries().forEach(item -> {
			if (item.get() instanceof SpawnEggItem egg)
				spawnEgg(egg);
		});
		
		// Complex
		stoneMaterial(ModBlocks.SPARKSTONE);
		stoneMaterial(ModBlocks.CHARRED_STONE);
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

	private void dislocator(Item dislocator) {
		String name = dislocator.getRegistryName().getPath();
		ResourceLocation property = new ResourceLocation(Main.MODID, "dimension");
		ModelFile dim = singleTexture(name + "_dimension", mcLoc(ITEM_FOLDER + "/generated"), "layer0",
				modLoc(ITEM_FOLDER + "/" + name + "_dimension"));
		ModelFile overworld = singleTexture(name + "_overworld", mcLoc(ITEM_FOLDER + "/generated"), "layer0",
				modLoc(ITEM_FOLDER + "/" + name + "_overworld"));
		singleTexture(name, mcLoc(ITEM_FOLDER + "/generated"), "layer0", modLoc(ITEM_FOLDER + "/" + name + "_dimension"))
				.override().model(dim).predicate(property, 0).end().override().model(overworld).predicate(property, 0.75f)
				.end();
	}
	
	private void stoneMaterial(StoneMaterial material) {
		wallInventory(material.WALL.getRegistryName().getPath(), modLoc(ModelProvider.BLOCK_FOLDER + "/" + material.NAME));
	}
}
