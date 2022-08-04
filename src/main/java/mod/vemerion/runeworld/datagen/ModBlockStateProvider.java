package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.FireRitualStoneBlock;
import mod.vemerion.runeworld.block.FireRootBlock;
import mod.vemerion.runeworld.block.RunePortalBlock;
import mod.vemerion.runeworld.block.complex.StoneMaterial;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, Main.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		simpleBlock(ModBlocks.BLOOD, empty("blood").texture("particle", modLoc("block/blood_moss_top")));
		simpleBlock(ModBlocks.BLOOD_LEECH, empty("blood_leech").texture("particle", modLoc("entity/blood_leech")));
		simpleBlock(ModBlocks.BLOOD_FLOWER,
				models().singleTexture("blood_flower", mcLoc("block/cross"), "cross", modLoc("block/blood_flower")));
		directionalBlock(ModBlocks.BLOOD_CRYSTAL,
				models().singleTexture("blood_crystal", mcLoc("block/cross"), "cross", modLoc("block/blood_crystal")));
		simpleBlockWithItem(ModBlocks.BLOOD_ROCK);
		simpleBlockWithItem(ModBlocks.CHARRED_DIRT);
		ResourceLocation bloodPillar = modLoc("block/blood_pillar");
		pillar(ModBlocks.BLOOD_PILLAR_LARGE, 12, bloodPillar, bloodPillar);
		pillar(ModBlocks.BLOOD_PILLAR_MEDIUM, 8, bloodPillar, bloodPillar);
		pillar(ModBlocks.BLOOD_PILLAR_SMALL, 4, bloodPillar, bloodPillar);
		grassBlock(ModBlocks.BLOOD_MOSS, modLoc("block/blood_moss_side"), modLoc("block/blood_rock"),
				modLoc("block/blood_moss_top"));
		grassBlock(ModBlocks.BURNT_DIRT, modLoc("block/burnt_dirt_side"), modLoc("block/charred_dirt"),
				modLoc("block/burnt_dirt_top"));
		fireRitualStone();
		fireRoot();

		for (Block portal : ModBlocks.getRunePortals())
			runePortal(portal);

		// Complex
		stoneMaterial(ModBlocks.SPARKSTONE);
		stoneMaterial(ModBlocks.CHARRED_STONE);
	}

	private void fireRoot() {
		Block fireRoot = ModBlocks.FIRE_ROOT;
		String name = fireRoot.getRegistryName().getPath();
		ModelFile[] stages = new ModelFile[4];
		for (int i = 0; i < stages.length; i++) {
			stages[i] = models().withExistingParent(name + "_stage" + i, mcLoc("block/crop")).texture("crop",
					modLoc("block/fire_root_stage" + i));
		}

		getVariantBuilder(fireRoot).forAllStates(state -> {
			int age = state.getValue(FireRootBlock.AGE);
			ModelFile model = stages[0];
			if (age == 2 || age == 3)
				model = stages[1];
			else if (age == 4 || age == 5 || age == 6)
				model = stages[2];
			else if (age == 7)
				model = stages[3];
			return ConfiguredModel.builder().modelFile(model).build();
		});
	}

	private BlockModelBuilder empty(String name) {
		return models().getBuilder(name);
	}

	private void simpleBlockWithItem(Block block) {
		ModelFile model = cubeAll(block);
		simpleBlock(block, model);
		simpleBlockItem(block, model);
	}

	private void pillar(Block block, int length, ResourceLocation side, ResourceLocation end) {
		String name = block.getRegistryName().getPath();
		BlockModelBuilder model = models().withExistingParent(name, mcLoc("block/block")).texture("side", side)
				.texture("end", end).texture("particle", side);
		int border = (16 - length) / 2;
		model.element().from(border, 0, border).to(border + length, 16, border + length).allFaces((d, b) -> {
			b.texture(d == Direction.UP || d == Direction.DOWN ? "#end" : "#side").end();
		});
		simpleBlock(block, model);
		simpleBlockItem(block, model);
	}

	private void grassBlock(Block block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
		String name = block.getRegistryName().getPath();
		ModelFile model = models().cubeBottomTop(name, side, bottom, top);
		simpleBlock(block, model);
		simpleBlockItem(block, model);
	}

	private void fireRitualStone() {
		Block block = ModBlocks.FIRE_RITUAL_STONE;
		String name = block.getRegistryName().getPath();
		ModelFile model = models().cubeColumn(name, modLoc("block/fire_ritual_stone"), modLoc("block/sparkstone"));
		ModelFile bloodied = models().cubeColumn(name + "_bloodied", modLoc("block/fire_ritual_stone_bloodied"),
				modLoc("block/sparkstone"));
		getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder()
				.modelFile(state.getValue(FireRitualStoneBlock.BLOODIED) ? bloodied : model).build());
		simpleBlockItem(block, model);
	}

	private void runePortal(Block portal) {
		ResourceLocation texture = modLoc("block/rune_portal");
		String name = portal.getRegistryName().getPath();
		BlockModelBuilder ew = models().withExistingParent(name + "_ew", mcLoc("block/block"))
				.texture("particle", texture).element().from(6, 0, 0).to(10, 16, 16).face(Direction.EAST)
				.texture("#particle").uvs(0, 0, 16, 16).tintindex(0).end().face(Direction.WEST).texture("#particle")
				.uvs(0, 0, 16, 16).tintindex(0).end().end();
		BlockModelBuilder ns = models().withExistingParent(name + "_ns", mcLoc("block/block"))
				.texture("particle", texture).element().from(0, 0, 6).to(16, 16, 10).face(Direction.NORTH)
				.texture("#particle").uvs(0, 0, 16, 16).tintindex(0).end().face(Direction.SOUTH).texture("#particle")
				.uvs(0, 0, 16, 16).tintindex(0).end().end();
		getVariantBuilder(portal).partialState().with(RunePortalBlock.AXIS, Direction.Axis.X)
				.addModels(new ConfiguredModel(ns)).partialState().with(RunePortalBlock.AXIS, Direction.Axis.Z)
				.addModels(new ConfiguredModel(ew));
	}

	private void stoneMaterial(StoneMaterial material) {
		ResourceLocation texture = modLoc(ModelProvider.BLOCK_FOLDER + "/" + material.NAME);
		simpleBlock(material.BLOCK);
		itemModelFromBlock(material.BLOCK);
		stairsBlock(material.STAIRS, texture);
		itemModelFromBlock(material.STAIRS);
		slabBlock(material.SLAB, texture, texture);
		itemModelFromBlock(material.SLAB);
		wallBlock(material.WALL, texture);
	}

	private void itemModelFromBlock(Block block) {
		ModelFile model = models().getExistingFile(block.getRegistryName());
		simpleBlockItem(block, model);
	}

}
