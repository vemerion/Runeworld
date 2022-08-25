package mod.vemerion.runeworld.structure;

import java.util.Random;

import com.mojang.serialization.Codec;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModStructurePieces;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public class FireRitualStructure extends StructureFeature<NoneFeatureConfiguration> {

	public FireRitualStructure(Codec<NoneFeatureConfiguration> codec) {
		super(codec,
				PieceGeneratorSupplier.simple(c -> c.validBiomeOnTop(Heightmap.Types.WORLD_SURFACE_WG),
						FireRitualStructure::generatePieces),
				(level, manager, generator, rand, bb, pos, container) -> Helper.fillBelowStructure(level, manager,
						generator, rand, bb, pos, container, ModBlocks.BURNT_DIRT.get().defaultBlockState(),
						ModBlocks.CHARRED_STONE.get().defaultBlockState()));
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
			super(ModStructurePieces.FIRE_RITUAL_PIECE.get(), 0, structureManager, TEMPLATE, TEMPLATE.toString(),
					new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK), pos);
		}

		public Piece(StructurePieceSerializationContext context, CompoundTag nbt) {
			super(ModStructurePieces.FIRE_RITUAL_PIECE.get(), nbt, context.structureManager(),
					a -> new StructurePlaceSettings().addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK));
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, ServerLevelAccessor worldIn, Random rand,
				BoundingBox sbb) {

		}

	}
}
