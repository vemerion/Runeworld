package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.complex.StoneMaterial;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
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
		simpleItem(ModItems.BLOOD_BUCKET.get());
		simpleItem(ModItems.BLOOD_PUDDING.get());
		simpleItem(ModItems.MOSQUITO_EGGS.get());
		simpleItem(ModItems.BLOOD_BAT_TOOTH.get());
		simpleItem(ModBlocks.BLOOD_LEECH);
		simpleItem(ModItems.GUIDE.get());
		simpleItem(ModItems.BLOOD_CRYSTALLITE.get());
		simpleItem(ModItems.BLOOD_PEBBLE.get());
		simpleItem(ModItems.GRILLED_BLOOD_LEECH.get());
		simpleItem(ModItems.FIRE_HEART.get());
		simpleItem(ModItems.FIRE_ROOT.get());
		simpleItem(ModItems.BLOOD_CROWN.get());
		simpleItem(ModBlocks.BLOOD_FLOWER, BLOCK_FOLDER);
		simpleItem(ModBlocks.BLOOD_CRYSTAL, BLOCK_FOLDER);
		dislocator(ModItems.BLOOD_DISLOCATOR.get());
		dislocator(ModItems.FIRE_DISLOCATOR.get());
		
		ModItems.ITEMS.getEntries().forEach(item -> {
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

	private void simpleItem(ItemLike item) {
		simpleItem(item.asItem(), ITEM_FOLDER);
	}

	private void simpleItem(ItemLike item, String folder) {
		String name = item.asItem().getRegistryName().getPath();
		singleTexture(name, mcLoc(ITEM_FOLDER + "/generated"), "layer0", modLoc(folder + "/" + name));
	}

	private void dislocator(ItemLike dislocator) {
		String name = dislocator.asItem().getRegistryName().getPath();
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
