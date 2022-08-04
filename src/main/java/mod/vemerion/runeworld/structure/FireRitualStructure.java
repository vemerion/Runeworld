package mod.vemerion.runeworld.structure;

import java.util.Random;

import com.mojang.serialization.Codec;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModStructurePieces;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public class FireRitualStructure extends StructureFeature<NoneFeatureConfiguration> {

	public FireRitualStructure(Codec<NoneFeatureConfiguration> codec) {
		super(codec, PieceGeneratorSupplier.simple(c -> c.validBiomeOnTop(Heightmap.Types.WORLD_SURFACE_WG),
				FireRitualStructure::generatePieces), FireRitualStructure::afterPlace);
	}

	@Override
	public Decoration step() {
		return Decoration.SURFACE_STRUCTURES;
	}

	private static void generatePieces(StructurePiecesBuilder builder,
			PieceGenerator.Context<NoneFeatureConfiguration> context) {
		int x = context.chunkPos().getMinBlockX();
		int z = context.chunkPos().getMinBlockZ();
		int height = context.chunkGenerator().getBaseHeight(x, z, Types.WORLD_SURFACE_WG, context.heightAccessor());

		builder.addPiece(new Piece(context.structureManager(), new BlockPos(x, height, z)));
	}

	public static class Piece extends TemplateStructurePiece {

		private static final ResourceLocation TEMPLATE = new ResourceLocation(Main.MODID, "fire_ritual");

		public Piece(StructureManager structureManager, BlockPos pos) {
			super(ModStructurePieces.FIRE_RITUAL_PIECE, 0, structureManager, TEMPLATE, TEMPLATE.toString(),
					new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK), pos);
		}

		public Piece(StructurePieceSerializationContext context, CompoundTag nbt) {
			super(ModStructurePieces.FIRE_RITUAL_PIECE, nbt, context.structureManager(),
					a -> new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK));
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor worldIn, Random rand,
				BoundingBox sbb) {

		}

	}

	private static void afterPlace(WorldGenLevel level, StructureFeatureManager manager, ChunkGenerator generator,
			Random rand, BoundingBox box, ChunkPos chunkPos, PiecesContainer pieces) {
		var pos = new BlockPos.MutableBlockPos();
		BoundingBox boundingbox = pieces.calculateBoundingBox();
		int yStart = boundingbox.minY() - 1;

		for (int x = box.minX(); x <= box.maxX(); x++) {
			for (int z = box.minZ(); z <= box.maxZ(); z++) {
				pos.set(x, yStart + 1, z);
				if (!level.isEmptyBlock(pos) && boundingbox.isInside(pos) && pieces.isInsidePiece(pos)) {
					for (int y = yStart; y > level.getMinBuildHeight(); y--) {
						pos.setY(y);
						if (!level.isEmptyBlock(pos))
							break;

						level.setBlock(pos, y == yStart ? ModBlocks.BURNT_DIRT.defaultBlockState()
								: ModBlocks.CHARRED_STONE.BLOCK.defaultBlockState(), 2);
					}
				}
			}
		}

	}

}
