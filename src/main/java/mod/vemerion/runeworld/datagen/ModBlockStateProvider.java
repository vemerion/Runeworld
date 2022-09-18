package mod.vemerion.runeworld.datagen;

import java.util.function.Function;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.CairnBlock;
import mod.vemerion.runeworld.block.FireRitualStoneBlock;
import mod.vemerion.runeworld.block.FireRootBlock;
import mod.vemerion.runeworld.block.FleshEatingPlantBlock;
import mod.vemerion.runeworld.block.FleshEatingPlantFlowerBlock;
import mod.vemerion.runeworld.block.RunePortalBlock;
import mod.vemerion.runeworld.block.complex.StoneMaterial;
import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Vec3i;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelBuilder.FaceRotation;
import net.minecraftforge.client.model.generators.ModelBuilder.Perspective;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder.PartBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

	public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, Main.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		simpleBlock(ModBlocks.BLOOD.get(), empty("blood").texture("particle", modLoc("block/blood_moss_top")));
		simpleBlock(ModBlocks.BLOOD_LEECH.get(),
				empty("blood_leech").texture("particle", modLoc("entity/blood_leech")));
		simpleBlock(ModBlocks.BLOOD_FLOWER.get(),
				models().singleTexture("blood_flower", mcLoc("block/cross"), "cross", modLoc("block/blood_flower")));
		directionalBlock(ModBlocks.BLOOD_CRYSTAL.get(),
				models().singleTexture("blood_crystal", mcLoc("block/cross"), "cross", modLoc("block/blood_crystal")));
		simpleBlockWithItem(ModBlocks.HIDEABLE_BLOOD_ROCK.get());
		simpleBlockWithItem(ModBlocks.HIDEABLE_BLOOD_ROCK_BRICKS.get());
		simpleBlockWithItem(ModBlocks.CHARRED_DIRT.get());
		ResourceLocation bloodPillar = modLoc("block/blood_pillar");
		pillar(ModBlocks.BLOOD_PILLAR_LARGE.get(), 12, bloodPillar, bloodPillar);
		pillar(ModBlocks.BLOOD_PILLAR_MEDIUM.get(), 8, bloodPillar, bloodPillar);
		pillar(ModBlocks.BLOOD_PILLAR_SMALL.get(), 4, bloodPillar, bloodPillar);
		grassBlock(ModBlocks.BLOOD_MOSS.get(), modLoc("block/blood_moss_side"), modLoc("block/blood_rock"),
				modLoc("block/blood_moss_top"));
		grassBlock(ModBlocks.BURNT_DIRT.get(), modLoc("block/burnt_dirt_side"), modLoc("block/charred_dirt"),
				modLoc("block/burnt_dirt_top"));
		fireRitualStone();
		fireRoot();

		fleshEatingPlantFlower();
		fleshEatingPlantStem();

		topaz();
		mirror();
		cairn();
		chalice();

		for (Block portal : ModBlocks.getRunePortals())
			runePortal(portal);

		// Complex
		stoneMaterial(ModBlocks.SPARKSTONE_MATERIAL);
		stoneMaterial(ModBlocks.CHARRED_STONE_MATERIAL);
		stoneMaterial(ModBlocks.BLOOD_ROCK_MATERIAL);
		stoneMaterial(ModBlocks.BLOOD_ROCK_BRICKS_MATERIAL);
	}

	private void chalice() {
		var chalice = ModBlocks.CHALICE.get();
		var name = chalice.getRegistryName().getPath();

		var model = models().withExistingParent(name, mcLoc("block/block")).texture("tex", modLoc("block/" + name))
				.texture("particle", "#tex").element().from(5, 0, 5).to(11, 1, 11).allFaces((direction, builder) -> {
					builder.texture("#tex");
					if (direction == Direction.UP || direction == Direction.DOWN)
						builder.uvs(0, 0, 6, 6);
					else
						builder.uvs(0, 0, 6, 1);
				}).end().element().from(7, 1, 7).to(9, 7, 9).allFaces((direction, builder) -> {
					builder.texture("#tex");
					if (direction == Direction.UP || direction == Direction.DOWN)
						builder.uvs(0, 0, 2, 2);
					else
						builder.uvs(0, 0, 2, 6);
				}).end();
		chaliceElement(model, new Vec3i(5, 0, 5), new Vec3i(11, 1, 11));
		chaliceElement(model, new Vec3i(7, 1, 7), new Vec3i(9, 7, 9));
		chaliceElement(model, new Vec3i(5, 7, 5), new Vec3i(11, 8, 11));

		chaliceElement(model, new Vec3i(4, 8, 5), new Vec3i(5, 13, 11));
		chaliceElement(model, new Vec3i(11, 8, 5), new Vec3i(12, 13, 11));
		chaliceElement(model, new Vec3i(5, 8, 4), new Vec3i(11, 13, 5));
		chaliceElement(model, new Vec3i(5, 8, 11), new Vec3i(11, 13, 12));

		simpleBlock(chalice, model);
	}

	private void chaliceElement(BlockModelBuilder model, Vec3i from, Vec3i to) {
		model.element().from(from.getX(), from.getY(), from.getZ()).to(to.getX(), to.getY(), to.getZ())
				.allFaces((direction, builder) -> {
					builder.texture("#tex");
					if (direction == Direction.UP || direction == Direction.DOWN)
						builder.uvs(0, 0, to.getX() - from.getX(), to.getZ() - from.getZ());
					else if (direction == Direction.NORTH || direction == Direction.SOUTH)
						builder.uvs(0, 0, to.getX() - from.getX(), to.getY() - from.getY());
					else
						builder.uvs(0, 0, to.getZ() - from.getZ(), to.getY() - from.getY());
				}).end();
	}

	private void cairn() {
		var cairn = ModBlocks.CAIRN.get();
		var name = cairn.getRegistryName().getPath();

		var builder = getMultipartBuilder(cairn);

		Function<Integer, Integer[]> levelCondition = level -> {
			var levels = new Integer[CairnBlock.MAX_LEVEL + 1 - level];
			for (int i = level; i <= CairnBlock.MAX_LEVEL; i++)
				levels[i - level] = i;
			return levels;
		};

		builder.part().modelFile(cairnPart(name, "0", new Vec3i(5, 0, 5), new Vec3i(11, 3, 11), 0)).addModel()
				.condition(CairnBlock.LEVEL, levelCondition.apply(0)).end();
		builder.part().modelFile(cairnPart(name, "1", new Vec3i(5, 3, 5), new Vec3i(10, 6, 10), 45)).addModel()
				.condition(CairnBlock.LEVEL, levelCondition.apply(1)).end();
		builder.part().modelFile(cairnPart(name, "2", new Vec3i(5, 6, 6), new Vec3i(10, 8, 11), 22.5f)).addModel()
				.condition(CairnBlock.LEVEL, levelCondition.apply(2)).end();
		builder.part().modelFile(cairnPart(name, "3", new Vec3i(5, 8, 5), new Vec3i(9, 10, 9), 0)).addModel()
				.condition(CairnBlock.LEVEL, levelCondition.apply(3)).end();
		builder.part().modelFile(cairnPart(name, "4", new Vec3i(6, 10, 6), new Vec3i(10, 12, 10), -45)).addModel()
				.condition(CairnBlock.LEVEL, levelCondition.apply(4)).end();
		builder.part().modelFile(cairnPart(name, "5", new Vec3i(6, 12, 6), new Vec3i(10, 13, 10), 0)).addModel()
				.condition(CairnBlock.LEVEL, levelCondition.apply(5)).end();
		builder.part().modelFile(cairnPart(name, "6", new Vec3i(6, 13, 6), new Vec3i(9, 14, 9), -22.5f)).addModel()
				.condition(CairnBlock.LEVEL, levelCondition.apply(6)).end();
		builder.part().modelFile(cairnPart(name, "7", new Vec3i(7, 14, 7), new Vec3i(9, 15, 9), 22.5f)).addModel()
				.condition(CairnBlock.LEVEL, levelCondition.apply(7)).end();
	}

	private BlockModelBuilder cairnPart(String name, String suffix, Vec3i from, Vec3i to, float rot) {
		var origin = from.offset((to.getX() - from.getX()) / 2, from.getY(), (to.getZ() - from.getZ()) / 2);
		return models().withExistingParent(name + "_" + suffix, mcLoc("block/block"))
				.texture("tex", modLoc("block/" + name)).texture("particle", "#tex").element()
				.from(from.getX(), from.getY(), from.getZ()).to(to.getX(), to.getY(), to.getZ())
				.allFaces((direction, builder) -> {
					builder.texture("#tex");
					if (direction.getAxis() == Direction.Axis.Y) {
						builder.uvs(0, 0, to.getX() - from.getX(), to.getZ() - from.getZ()).end();
					} else {
						builder.uvs(0, 0, to.getX() - from.getX(), to.getY() - from.getY()).end();
					}

				}).rotation().axis(Direction.Axis.Y).origin(origin.getX(), origin.getY(), origin.getZ()).angle(rot)
				.end().end();
	}

	private void mirror() {
		var mirror = ModBlocks.MIRROR.get();
		var name = mirror.getRegistryName().getPath();

		var model = models().withExistingParent(name, mcLoc("block/block"))
				.texture("front", modLoc("block/" + name + "_front")).texture("back", modLoc("block/" + name + "_back"))
				.texture("side", modLoc("block/" + name + "_side")).texture("particle", "#front").element()
				.from(0, 0, 15).to(16, 16, 16).allFaces((direction, builder) -> {
					if (direction == Direction.NORTH)
						builder.texture("#front").uvs(0, 0, 16, 16);
					else if (direction == Direction.SOUTH)
						builder.texture("#back").uvs(0, 0, 16, 16).tintindex(0);
					else {
						builder.texture("#side").uvs(0, 0, 16, 1).tintindex(0);
						if (direction == Direction.WEST || direction == Direction.EAST)
							builder.rotation(FaceRotation.CLOCKWISE_90);
					}
				}).end();
		horizontalBlock(mirror, model);

		model.transforms().transform(Perspective.GUI).translation(4, -2, 0).rotation(30, 225, 0)
				.scale(0.75f, 0.75f, 0.75f).end().end();

		simpleBlockItem(mirror, model);
	}

	private void topaz() {
		var topaz = ModBlocks.TOPAZ.get();
		var name = topaz.getRegistryName().getPath();

		var model = models().withExistingParent(name, mcLoc("block/block")).texture("tex", modLoc("block/" + name))
				.texture("particle", "#tex");

		addTopazElement(model, new Vec3i(7, 1, 6), new Vec3i(11, 9, 10), Direction.Axis.Z, -22.5f);
		addTopazElement(model, new Vec3i(6, 1, 5), new Vec3i(10, 9, 9), Direction.Axis.X, -22.5f);
		addTopazElement(model, new Vec3i(6, 0, 6), new Vec3i(10, 11, 10), Direction.Axis.Z, 0);
		addTopazElement(model, new Vec3i(5, 1, 6), new Vec3i(9, 9, 10), Direction.Axis.Z, 22.5f);
		addTopazElement(model, new Vec3i(6, 1, 7), new Vec3i(10, 9, 11), Direction.Axis.X, 22.5f);

		directionalBlock(topaz, model);
		itemModelFromBlock(topaz);
	}

	private void addTopazElement(BlockModelBuilder model, Vec3i from, Vec3i to, Direction.Axis axis, float angle) {
		var origin = from.offset((to.getX() - from.getX()) / 2, from.getY(), (to.getZ() - from.getZ()) / 2);
		model.element().from(from.getX(), from.getY(), from.getZ()).to(to.getX(), to.getY(), to.getZ()).rotation()
				.axis(axis).angle(angle).origin(origin.getX(), origin.getY(), origin.getZ()).end()
				.allFaces((direction, builder) -> {
					builder.texture("#tex").tintindex(0);
					if (direction == Direction.UP || direction == Direction.DOWN) {
						builder.uvs(12, 0, 16, 4);
					} else {
						builder.uvs(0, 0, 4, 8);
					}
				}).end();
	}

	private void fleshEatingPlantFlower() {
		var plant = ModBlocks.FLESH_EATING_PLANT_FLOWER.get();
		var stem = ModBlocks.FLESH_EATING_PLANT_STEM.get();

		var flowerOpen = flowerElements(22.5f, plant, "_open", stem);
		var flowerClosed = flowerElements(0, plant, "_closed", stem);

		directionalBlock(plant, state -> state.getValue(FleshEatingPlantFlowerBlock.OPEN) ? flowerOpen : flowerClosed);
	}

	private BlockModelBuilder flowerElements(float rotation, Block plant, String suffix, Block stem) {
		var name = plant.getRegistryName().getPath();
		var stemName = stem.getRegistryName().getPath();

		return models().withExistingParent(name + suffix, mcLoc("block/block"))
				.texture("side", modLoc("block/" + name + "_side")).texture("back", modLoc("block/" + name + "_back"))
				.texture("inside", modLoc("block/" + name + "_inside"))
				.texture("bottom", modLoc("block/" + name + "_bottom")).texture("particle", "#side")
				.texture("stem_side", modLoc("block/" + stemName + "_side"))
				.texture("stem_end", modLoc("block/" + stemName + "_end"))
				// Stem
				.element().from(6, 0, 6).to(10, 4, 10).allFaces((direction, builder) -> {
					if (direction == Direction.UP || direction == Direction.DOWN) {
						builder.texture("#stem_end").uvs(6, 6, 10, 10).end();
					} else {
						builder.texture("#stem_side").uvs(6, 0, 10, 4).end();
					}
				}).end()
				// Left part
				.element().from(3, 4, 3).to(13, 14, 8).rotation().origin(8, 4, 8).axis(Axis.X).angle(-rotation).end()
				.face(Direction.NORTH).texture("#back").uvs(3, 3, 13, 13).end().face(Direction.SOUTH).texture("#inside")
				.uvs(3, 3, 13, 13).end().face(Direction.UP).texture("#side").uvs(5, 3, 10, 13)
				.rotation(FaceRotation.CLOCKWISE_90).end().face(Direction.DOWN).texture("#bottom").uvs(5, 3, 10, 13)
				.rotation(FaceRotation.COUNTERCLOCKWISE_90).end().face(Direction.EAST).texture("#side")
				.uvs(5, 3, 10, 13).rotation(FaceRotation.UPSIDE_DOWN).end().face(Direction.WEST).texture("#side")
				.uvs(5, 3, 10, 13).rotation(FaceRotation.ZERO).end().end()
				// Right part
				.element().from(3, 4, 8).to(13, 14, 13).rotation().origin(8, 4, 8).axis(Axis.X).angle(rotation).end()
				.face(Direction.SOUTH).texture("#back").uvs(3, 3, 13, 13).end().face(Direction.NORTH).texture("#inside")
				.uvs(3, 3, 13, 13).end().face(Direction.UP).texture("#side").uvs(5, 3, 10, 13)
				.rotation(FaceRotation.COUNTERCLOCKWISE_90).end().face(Direction.DOWN).texture("#bottom")
				.uvs(5, 3, 10, 13).rotation(FaceRotation.CLOCKWISE_90).end().face(Direction.EAST).texture("#side")
				.uvs(5, 3, 10, 13).rotation(FaceRotation.ZERO).end().face(Direction.WEST).texture("#side")
				.uvs(5, 3, 10, 13).rotation(FaceRotation.UPSIDE_DOWN).end().end();
	}

	private void fleshEatingPlantStem() {
		var plant = ModBlocks.FLESH_EATING_PLANT_STEM.get();
		var name = plant.getRegistryName().getPath();

		var stem = models().withExistingParent(name, mcLoc("block/block"))
				.texture("side", modLoc("block/" + name + "_side")).texture("end", modLoc("block/" + name + "_end"))
				.texture("particle", "#side").element().from(6, 0, 6).to(10, 8, 10).allFaces((direction, builder) -> {
					if (direction == Direction.UP || direction == Direction.DOWN) {
						builder.texture("#end").uvs(6, 6, 10, 10).end();
					} else {
						builder.texture("#side").uvs(6, 0, 10, 8).end();
					}
				}).end();

		for (var dir : Direction.values()) {
			// facing: incoming dir
			rotationPart(plant, stem, dir).condition(FleshEatingPlantBlock.FACING, dir).end();

			// Attachment: outgoing dir
			rotationPart(plant, stem, dir).condition(FleshEatingPlantBlock.ATTACHMENT, dir).end();
		}
	}

	private PartBuilder rotationPart(Block block, ModelFile model, Direction dir) {
		return getMultipartBuilder(block).part().modelFile(model)
				.rotationX(dir == Direction.DOWN ? 180 : dir.getAxis().isHorizontal() ? 90 : 0)
				.rotationY(dir.getAxis().isVertical() ? 0 : (((int) dir.toYRot()) + 180) % 360).addModel();
	}

	private void fireRoot() {
		Block fireRoot = ModBlocks.FIRE_ROOT.get();
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
		Block block = ModBlocks.FIRE_RITUAL_STONE.get();
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
		ResourceLocation texture = modLoc(ModelProvider.BLOCK_FOLDER + "/" + material.getName());
		simpleBlock(material.block());
		itemModelFromBlock(material.block());
		stairsBlock(material.stair(), texture);
		itemModelFromBlock(material.stair());
		slabBlock(material.slab(), texture, texture);
		itemModelFromBlock(material.slab());
		wallBlock(material.wall(), texture);
	}

	private void itemModelFromBlock(Block block) {
		ModelFile model = models().getExistingFile(block.getRegistryName());
		simpleBlockItem(block, model);
	}

}
