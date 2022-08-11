package mod.vemerion.runeworld.structure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.mojang.serialization.Codec;

import mod.vemerion.runeworld.block.BloodCrystalBlock;
import mod.vemerion.runeworld.entity.BloodBatEntity;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModLootTables;
import mod.vemerion.runeworld.init.ModStructurePieces;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.phys.Vec3;

public class BloodBatLairStructure extends StructureFeature<NoneFeatureConfiguration> {

	private static final int MAX_RADIUS = 10;
	private static final int MIN_RADIUS = 6;

	public BloodBatLairStructure(Codec<NoneFeatureConfiguration> codec) {
		super(codec, PieceGeneratorSupplier.simple(c -> c.validBiomeOnTop(Heightmap.Types.WORLD_SURFACE_WG), BloodBatLairStructure::generatePieces));
	}

	private static void generatePieces(StructurePiecesBuilder builder,
			PieceGenerator.Context<NoneFeatureConfiguration> context) {
		int x = context.chunkPos().getMinBlockX();
		int z = context.chunkPos().getMinBlockZ();
		int height = context.chunkGenerator().getBaseHeight(x, z, Types.WORLD_SURFACE_WG, context.heightAccessor());

		var rand = context.random();

		builder.addPiece(new Piece(new BlockPos(x, height, z), rand.nextInt(MIN_RADIUS, MAX_RADIUS),
				rand.nextInt(MIN_RADIUS, MAX_RADIUS), rand.nextInt()));
	}

	@Override
	public Decoration step() {
		return Decoration.SURFACE_STRUCTURES;
	}

	public static class Piece extends StructurePiece {

		private static final BlockState ROCK = ModBlocks.BLOOD_ROCK.defaultBlockState();
		private static final BlockState MOSS = ModBlocks.BLOOD_MOSS.defaultBlockState();
		private static final BlockState CRYSTAL = ModBlocks.BLOOD_CRYSTAL.defaultBlockState();
		private static final BlockState AIR = Blocks.AIR.defaultBlockState();
		private static final double STEP = Math.toRadians(1);
		private static final int MAX_BASE_EXTEND_DOWN = 10;

		private BlockPos center;
		private int a, b;
		private int randSeed;

		public Piece(BlockPos center, int a, int b, int randSeed) {
			super(ModStructurePieces.BLOOD_BAT_LAIR_PIECE, 0, createBoundingBox(center, a, b));
			this.center = center;
			this.a = a;
			this.b = b;
			this.randSeed = randSeed;
		}

		public Piece(StructurePieceSerializationContext pContext, CompoundTag nbt) {
			super(ModStructurePieces.BLOOD_BAT_LAIR_PIECE, nbt);
			this.center = NbtUtils.readBlockPos(nbt.getCompound("center"));
			this.a = nbt.getInt("a");
			this.b = nbt.getInt("b");
			this.randSeed = nbt.getInt("randSeed");
			this.boundingBox = createBoundingBox(center, a, b);
		}

		private static BoundingBox createBoundingBox(BlockPos center, int a, int b) {
			return new BoundingBox(center.getX() - a, center.getY() - MAX_BASE_EXTEND_DOWN, center.getZ() - b,
					center.getX() + a, center.getY() + Math.min(a, b) * 3, center.getZ() + b);
		}

		@Override
		protected void addAdditionalSaveData(StructurePieceSerializationContext pContext, CompoundTag pTag) {
			pTag.put("center", NbtUtils.writeBlockPos(center));
			pTag.putInt("a", a);
			pTag.putInt("b", b);
			pTag.putInt("randSeed", randSeed);
		}

		// Note: Be sure to only generate the parts inside the given chunk (box)
		// The method will be called several times, once for each necessary chunk
		// This means that we can not used supplied Random to determine structure shape
		@Override
		public void postProcess(WorldGenLevel world, StructureFeatureManager manager, ChunkGenerator generator,
				Random rand, BoundingBox box, ChunkPos chunkPos, BlockPos pos) {
			Random random = new Random(randSeed);
			int radiusX = a;
			int radiusZ = b;
			int y = 0;
			int prevY = -MAX_BASE_EXTEND_DOWN;
			int yDiff = Mth.nextInt(random, 2, 3);
			Set<BlockPos> lair = new HashSet<>();

			while (radiusX > 0 && radiusZ > 0) {
				for (BlockPos p : getWallSlice(center, y, radiusX, radiusZ)) {
					lair.add(p);

					// Fill below
					for (int j = 0; j < y - prevY; j++) {
						if (!world.isEmptyBlock(p.below(j)))
							break;
						lair.add(p.below(j));
					}

					// Add random above
					if (random.nextDouble() < 0.1) {
						lair.add(p.above());
					}
				}

				prevY = y;
				y += yDiff;
				yDiff = random.nextDouble() < 0.75 ? Math.max(1, yDiff - 1) : yDiff;
				radiusX -= 1;
				radiusZ -= 1;
			}

			// Generate the lair
			List<BlockPos> inChunk = new ArrayList<>();
			for (BlockPos p : lair) {
				setLairWall(world, random, p, lair, box);
				if (box.isInside(p) && !lair.contains(p.below()))
					inChunk.add(p);
			}

			// From this point onwards we can use rand,
			// since we are only looking at out chunk

			// Generate stalactite with blood bat
			addStalactites(world, rand, inChunk);

			// Add holes to lair
			addHoles(world, rand, inChunk);

			// Add blood crystals
			addBloodCrystals(world, rand, inChunk);

			// Chest
			addChest(world, rand, box);
		}

		private void addChest(WorldGenLevel world, Random rand, BoundingBox box) {
			if (box.isInside(center))
				createChest(world, box, rand, center.getX(), center.getY(), center.getZ(),
						ModLootTables.CHEST_BLOOD_BAT_LAIR);
		}

		private void addBloodCrystals(WorldGenLevel world, Random rand, List<BlockPos> inChunk) {
			for (int i = 0; i < rand.nextInt(2) + 1; i++) {
				if (inChunk.isEmpty())
					return;

				BlockPos p = inChunk.remove(rand.nextInt(inChunk.size()));
				p = p.relative(Direction.DOWN);
				if (world.isEmptyBlock(p))
					world.setBlock(p, CRYSTAL.setValue(BloodCrystalBlock.FACING, Direction.DOWN), 2);
			}
		}

		private void addHoles(WorldGenLevel world, Random rand, List<BlockPos> inChunk) {
			for (int i = 0; i < 4; i++) {
				if (inChunk.isEmpty())
					return;

				BlockPos p = inChunk.remove(rand.nextInt(inChunk.size()));
				world.setBlock(p, AIR, 2);
			}
		}

		private void addStalactites(WorldGenLevel world, Random rand, List<BlockPos> inChunk) {
			for (int i = 0; i < rand.nextInt(2) + 2; i++) {
				if (inChunk.isEmpty())
					return;

				BlockPos p = inChunk.remove(rand.nextInt(inChunk.size()));
				int height = rand.nextInt(3) + 2;
				for (int j = 1; j < height; j++) {
					if (!world.isEmptyBlock(p.below(j + 2))) {
						height = j;
						break;
					}
					world.setBlock(p.below(j), ROCK, 2);
				}
				p = p.below(height - 1);

				if (BloodBatEntity.isValidLedgePos(world, p, null)) {
					BloodBatEntity bat = ModEntities.BLOOD_BAT.get().create(world.getLevel());
					Vec3 position = Vec3.atBottomCenterOf(p).add(0, -1.75, 0);
					bat.absMoveTo(position.x, position.y, position.z, rand.nextFloat() * 360, 0);
					bat.startHanging(position);
					world.addFreshEntity(bat);
				}
			}
		}

		private Set<BlockPos> getWallSlice(BlockPos pos, int y, int radiusX, int radiusZ) {
			Set<BlockPos> slice = new HashSet<>();
			for (float i = 0; i < Math.PI * 2; i += STEP) {
				BlockPos p = pos.offset(radiusX * Mth.cos(i), y, radiusZ * Mth.sin(i));
				slice.add(p);
			}
			return slice;
		}

		private void setLairWall(WorldGenLevel world, Random rand, BlockPos pos, Set<BlockPos> wall, BoundingBox box) {
			placeBlock(world, rand.nextDouble() < 0.1 || wall.contains(pos.above()) ? ROCK : MOSS, pos.getX(),
					pos.getY(), pos.getZ(), box);
		}
	}

}
