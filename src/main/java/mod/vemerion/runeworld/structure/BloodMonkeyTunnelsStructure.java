package mod.vemerion.runeworld.structure;

import java.util.Optional;
import java.util.Random;

import com.mojang.serialization.Codec;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModStructurePieces;
import mod.vemerion.runeworld.init.ModStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;

public class BloodMonkeyTunnelsStructure extends StructureFeature<JigsawConfiguration> {

	public BloodMonkeyTunnelsStructure(Codec<JigsawConfiguration> codec) {
		super(codec, (context) -> {
			var pos = new BlockPos(context.chunkPos().getMinBlockX(), 28, context.chunkPos().getMinBlockZ());
			var generator = JigsawPlacement.addPieces(context, PoolElementStructurePiece::new, pos, false, false);
			return BloodMonkeyTunnelsStructure.addPieces(pos.getX(), pos.getZ(), generator);
		});
	}

	public static Optional<PieceGenerator<JigsawConfiguration>> addPieces(int x, int z,
			Optional<PieceGenerator<JigsawConfiguration>> generator) {
		if (generator.isEmpty()) {
			return generator;
		} else {
			return Optional.of((builder, context) -> {
				generator.get().generatePieces(builder, context);

				var pieces = builder.build().pieces();
				var bb = pieces.get(context.random().nextInt(pieces.size())).getBoundingBox();
				var center = bb.getCenter();
				var y = context.chunkGenerator().getBaseHeight(center.getX(), center.getZ(), Types.WORLD_SURFACE_WG,
						context.heightAccessor());
				builder.addPiece(new EntrancePiece(y, new BlockPos(center.getX(), bb.maxY(), center.getZ())));
			});
		}
	}

	@Override
	public Decoration step() {
		return Decoration.UNDERGROUND_STRUCTURES;
	}

	public static String translationKey() {
		return Main.MODID + ".filled_map." + ModStructures.BLOOD_MONKEY_TUNNELS.getId().getPath();
	}

	public static class EntrancePiece extends StructurePiece {

		private static final int WIDTH = 2;
		private static final int HEIGHT = 3;

		private BlockPos target;
		private int surfaceY;

		public EntrancePiece(int surfaceY, BlockPos target) {
			super(ModStructurePieces.BLOOD_MONKEY_TUNNELS_ENTRANCE_PIECE.get(), 0, createBoundingBox(surfaceY, target));
			this.surfaceY = surfaceY;
			this.target = target;
			this.setOrientation(null);
		}

		public EntrancePiece(StructurePieceSerializationContext pContext, CompoundTag nbt) {
			super(ModStructurePieces.BLOOD_MONKEY_TUNNELS_ENTRANCE_PIECE.get(), nbt);
			this.target = NbtUtils.readBlockPos(nbt.getCompound("target"));
			this.surfaceY = nbt.getInt("surfaceY");
			this.boundingBox = createBoundingBox(surfaceY, target);
			this.setOrientation(null);
		}

		private static BoundingBox createBoundingBox(int surfaceY, BlockPos target) {
			return new BoundingBox(target.getX() - WIDTH, surfaceY, target.getZ() - WIDTH, target.getX() + WIDTH,
					surfaceY + HEIGHT, target.getZ() + WIDTH);
		}

		@Override
		protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
			pTag.put("target", NbtUtils.writeBlockPos(target));
			pTag.putInt("surfaceY", surfaceY);
		}

		@Override
		public void postProcess(WorldGenLevel level, StructureFeatureManager manager, ChunkGenerator generator,
				Random rand, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {

			var bricks = ModBlocks.BLOOD_ROCK_BRICKS.get().defaultBlockState();
			var stairs = ModBlocks.BLOOD_ROCK_BRICK_STAIRS.get().defaultBlockState();
			var vine = Blocks.VINE.defaultBlockState().setValue(VineBlock.NORTH, true);

			int minX = target.getX() - WIDTH;
			int maxX = target.getX() + WIDTH;
			int minZ = target.getZ() - WIDTH;
			int maxZ = target.getZ() + WIDTH;
			int maxY = surfaceY + HEIGHT;

			// Roof
			for (int x = minX; x <= maxX; x++) {
				for (int z = minZ; z <= maxZ; z++) {
					placeBlock(level, bricks, x, maxY, z, box);
				}
			}

			// Corner pillars
			generatePillar(level, box, minX, maxY, minZ, bricks, stairs);
			generatePillar(level, box, maxX, maxY, minZ, bricks, stairs);
			generatePillar(level, box, minX, maxY, maxZ, bricks, stairs);
			generatePillar(level, box, maxX, maxY, maxZ, bricks, stairs);

			// Vines down
			for (int y = surfaceY - 1; y >= target.getY(); y--) {
				placeBlock(level, vine, target.getX(), y, target.getZ(), box);
			}
		}

		private void generatePillar(WorldGenLevel level, BoundingBox box, int x, int startY, int z, BlockState bricks,
				BlockState stairs) {
			for (int y = startY - 1; y > startY - 10; y--) {
				if (isReplaceableByStructures(getBlock(level, x, y, z, box))) {
					placeBlock(level, bricks, x, y, z, box);
				} else {
					var pos = new BlockPos(x, y + 1, z);
					for (var dir : Direction.Plane.HORIZONTAL) {
						var adjacent = pos.relative(dir);
						placeBlock(level, stairs.setValue(StairBlock.FACING, dir.getOpposite()), adjacent.getX(),
								adjacent.getY(), adjacent.getZ(), box);
					}
					break;
				}
			}
		}
	}

}