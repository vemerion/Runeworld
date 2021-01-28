package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, Main.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		simpleBlock(ModBlocks.BLOOD, empty("blood"));
		simpleBlock(ModBlocks.BLOOD_FLOWER,
				models().singleTexture("blood_flower", mcLoc("block/cross"), "cross", modLoc("block/blood_flower")));
		ResourceLocation bloodPillar = modLoc("block/blood_pillar");
		pillar(ModBlocks.BLOOD_PILLAR_LARGE, 12, bloodPillar, bloodPillar);
		pillar(ModBlocks.BLOOD_PILLAR_MEDIUM, 8, bloodPillar, bloodPillar);
		pillar(ModBlocks.BLOOD_PILLAR_SMALL, 4, bloodPillar, bloodPillar);
	}

	private BlockModelBuilder empty(String name) {
		return models().getBuilder(name);
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

}