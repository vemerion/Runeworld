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
		simpleItem(ModBlocks.BLOOD_LEECH.get());
		simpleItem(ModItems.GUIDE.get());
		simpleItem(ModItems.BLOOD_CRYSTALLITE.get());
		simpleItem(ModItems.BLOOD_PEBBLE.get());
		simpleItem(ModItems.GRILLED_BLOOD_LEECH.get());
		simpleItem(ModItems.FIRE_HEART.get());
		simpleItem(ModItems.FIRE_ROOT.get());
		simpleItem(ModItems.BLOOD_CROWN.get());
		simpleItem(ModBlocks.BLOOD_FLOWER.get(), BLOCK_FOLDER);
		simpleItem(ModBlocks.BLOOD_CRYSTAL.get(), BLOCK_FOLDER);
		simpleItem(ModBlocks.FLESH_EATING_PLANT_FLOWER.get());
		simpleItem(ModItems.MONKEY_PAW.get());
		simpleItem(ModItems.TOPAZ_GEM.get());
		simpleItem(ModItems.TOPAZ_SHARD.get());
		simpleItem(ModBlocks.CAIRN.get());
		simpleItem(ModBlocks.CHALICE.get());
		dislocator(ModItems.BLOOD_DISLOCATOR.get());
		dislocator(ModItems.FIRE_DISLOCATOR.get());
		handMirror();

		ModItems.ITEMS.getEntries().forEach(item -> {
			if (item.get() instanceof SpawnEggItem egg)
				spawnEgg(egg);
		});

		slingshot();

		// Complex
		stoneMaterial(ModBlocks.SPARKSTONE_MATERIAL);
		stoneMaterial(ModBlocks.CHARRED_STONE_MATERIAL);
		stoneMaterial(ModBlocks.BLOOD_ROCK_MATERIAL);
		stoneMaterial(ModBlocks.BLOOD_ROCK_BRICKS_MATERIAL);
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

	private void handMirror() {
		var mirror = ModItems.HAND_MIRROR.get();
		var name = mirror.asItem().getRegistryName().getPath();
		withExistingParent(name, mcLoc(ITEM_FOLDER + "/generated"))
				.texture("layer0", modLoc(folder + "/" + name + "_handle"))
				.texture("layer1", modLoc(folder + "/" + name + "_glass"));
	}

	private void dislocator(ItemLike dislocator) {
		String name = dislocator.asItem().getRegistryName().getPath();
		ResourceLocation property = new ResourceLocation(Main.MODID, "dimension");
		ModelFile dim = singleTexture(name + "_dimension", mcLoc(ITEM_FOLDER + "/generated"), "layer0",
				modLoc(ITEM_FOLDER + "/" + name + "_dimension"));
		ModelFile overworld = singleTexture(name + "_overworld", mcLoc(ITEM_FOLDER + "/generated"), "layer0",
				modLoc(ITEM_FOLDER + "/" + name + "_overworld"));
		singleTexture(name, mcLoc(ITEM_FOLDER + "/generated"), "layer0",
				modLoc(ITEM_FOLDER + "/" + name + "_dimension")).override().model(dim).predicate(property, 0).end()
				.override().model(overworld).predicate(property, 0.75f).end();
	}

	private void slingshot() {
		var name = ModItems.SLINGSHOT.get().getRegistryName().getPath();

		var property = new ResourceLocation(Main.MODID, "shooting");
		var shooting = withExistingParent(name + "_shooting", mcLoc(ITEM_FOLDER + "/generated"))
				.texture("layer0", modLoc(ITEM_FOLDER + "/" + name + "_band_shooting"))
				.texture("layer1", modLoc(ITEM_FOLDER + "/" + name + "_handle"));
		withExistingParent(name, mcLoc(ITEM_FOLDER + "/generated"))
				.texture("layer0", modLoc(ITEM_FOLDER + "/" + name + "_band"))
				.texture("layer1", modLoc(ITEM_FOLDER + "/" + name + "_handle")).override().model(shooting)
				.predicate(property, 0.5f).end();
	}

	private void stoneMaterial(StoneMaterial material) {
		wallInventory(material.wall().getRegistryName().getPath(),
				modLoc(ModelProvider.BLOCK_FOLDER + "/" + material.getName()));
	}
}
