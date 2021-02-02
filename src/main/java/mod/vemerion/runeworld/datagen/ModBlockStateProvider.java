package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, Main.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		simpleBlock(ModBlocks.BLOOD, empty("blood").texture("particle", modLoc("block/blood_moss_top")));
		simpleBlock(ModBlocks.BLOOD_FLOWER,
				models().singleTexture("blood_flower", mcLoc("block/cross"), "cross", modLoc("block/blood_flower")));
		directionalBlock(ModBlocks.BLOOD_CRYSTAL,
				models().singleTexture("blood_crystal", mcLoc("block/cross"), "cross", modLoc("block/blood_crystal")));
		simpleBlockWithItem(ModBlocks.BLOOD_ROCK);
		ResourceLocation bloodPillar = modLoc("block/blood_pillar");
		pillar(ModBlocks.BLOOD_PILLAR_LARGE, 12, bloodPillar, bloodPillar);
		pillar(ModBlocks.BLOOD_PILLAR_MEDIUM, 8, bloodPillar, bloodPillar);
		pillar(ModBlocks.BLOOD_PILLAR_SMALL, 4, bloodPillar, bloodPillar);
		bloodMoss();
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

	private void bloodMoss() {
		Block bloodMoss = ModBlocks.BLOOD_MOSS;
		String name = bloodMoss.getRegistryName().getPath();
		ModelFile model = models().cubeBottomTop(name, modLoc("block/blood_moss_side"), modLoc("block/blood_rock"),
				modLoc("block/blood_moss_top"));
		simpleBlock(bloodMoss, model);
		simpleBlockItem(bloodMoss, model);
	}

}
