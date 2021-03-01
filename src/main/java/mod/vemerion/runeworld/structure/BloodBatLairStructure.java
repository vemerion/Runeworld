package mod.vemerion.runeworld.structure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.mojang.serialization.Codec;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.BloodCrystalBlock;
import mod.vemerion.runeworld.entity.BloodBatEntity;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModLootTables;
import mod.vemerion.runeworld.init.ModStructurePieces;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class BloodBatLairStructure extends Structure<NoFeatureConfig> {

	public BloodBatLairStructure(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public Decoration getDecorationStage() {
		return Decoration.SURFACE_STRUCTURES;
	}

	@Override
	public String getStructureName() {
		return Main.MODID + ":blood_bat_lair";
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() {
		return Start::new;
	}

	private static class Start extends StructureStart<NoFeatureConfig> {

		private static final int MAX_RADIUS = 10;
		private static final int MIN_RADIUS = 6;

		public Start(Structure<NoFeatureConfig> structure, int chunkX, int chunkZ, MutableBoundingBox bounds,
				int references, long seed) {
			super(structure, chunkX, chunkZ, bounds, references, seed);
		}

		@Override
		public void func_230364_a_(DynamicRegistries registry, ChunkGenerator generator, TemplateManager manager,
				int chunkX, int chunkZ, Biome biome, NoFeatureConfig config) {
			int x = chunkX * 16;
			int z = chunkZ * 16;
			int height = generator.getHeight(x, z, Type.WORLD_SURFACE_WG);
			components.add(new Piece(new BlockPos(x, height, z), randLength(), randLength(), rand.nextInt()));
			recalculateStructureSize();
		}

		private int randLength() {
			return MathHelper.nextInt(rand, MIN_RADIUS, MAX_RADIUS);
		}

	}

	public static class Piece extends StructurePiece {

		private static final BlockState ROCK = ModBlocks.BLOOD_ROCK.getDefaultState();
		private static final BlockState MOSS = ModBlocks.BLOOD_MOSS.getDefaultState();
		private static final BlockState CRYSTAL = ModBlocks.BLOOD_CRYSTAL.getDefaultState();
		private static final BlockState AIR = Blocks.AIR.getDefaultState();
		private static final double STEP = Math.toRadians(1);
		private static final int MAX_BASE_EXTEND_DOWN = 10;

		private BlockPos center;
		private int a, b;
		private int randSeed;

		public Piece(BlockPos center, int a, int b, int randSeed) {
			super(ModStructurePieces.BLOOD_BAT_LAIR_PIECE, 0);
			this.center = center;
			this.a = a;
			this.b = b;
			this.randSeed = randSeed;
			this.updateBoundingBox();
		}

		public Piece(TemplateManager manager, CompoundNBT nbt) {
			super(ModStructurePieces.BLOOD_BAT_LAIR_PIECE, nbt);
			this.center = NBTUtil.readBlockPos(nbt.getCompound("center"));
			this.a = nbt.getInt("a");
			this.b = nbt.getInt("b");
			this.randSeed = nbt.getInt("randSeed");
			this.updateBoundingBox();
		}

		private void updateBoundingBox() {
			boundingBox = new MutableBoundingBox(center.getX() - a, center.getY() - MAX_BASE_EXTEND_DOWN,
					center.getZ() - b, center.getX() + a, center.getY() + Math.min(a, b) * 3, center.getZ() + b);
		}

		@Override
		protected void readAdditional(CompoundNBT tagCompound) {
			tagCompound.put("center", NBTUtil.writeBlockPos(center));
			tagCompound.putInt("a", a);
			tagCompound.putInt("b", b);
			tagCompound.putInt("randSeed", randSeed);
		}

		// TODO: Prevent other world gen features from generating inside lair

		// Note: Be sure to only generate the parts inside the given chunk (box)
		// The method will be called several times, once for each necessary chunk
		// This means that we can not used supplied Random to determine structure shape
		@Override
		public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator,
				Random rand, MutableBoundingBox box, ChunkPos chunkPos, BlockPos pos) {
			Random random = new Random(randSeed);
			int radiusX = a;
			int radiusZ = b;
			int y = 0;
			int prevY = -MAX_BASE_EXTEND_DOWN;
			int yDiff = MathHelper.nextInt(random, 2, 3);
			Set<BlockPos> lair = new HashSet<>();

			while (radiusX > 0 && radiusZ > 0) {
				for (BlockPos p : getWallSlice(center, y, radiusX, radiusZ)) {
					lair.add(p);

					// Fill below
					for (int j = 0; j < y - prevY; j++) {
						if (!world.isAirBlock(p.down(j)))
							break;
						lair.add(p.down(j));
					}

					// Add random above
					if (random.nextDouble() < 0.1) {
						lair.add(p.up());
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
				if (box.isVecInside(p) && !lair.contains(p.down()))
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

			return true;
		}

		private void addChest(ISeedReader world, Random rand, MutableBoundingBox box) {
			if (box.isVecInside(center))
				generateChest(world, box, rand, center.getX(), center.getY(), center.getZ(),
						ModLootTables.CHEST_BLOOD_BAT_LAIR);
		}

		private void addBloodCrystals(ISeedReader world, Random rand, List<BlockPos> inChunk) {
			for (int i = 0; i < rand.nextInt(2) + 1; i++) {
				if (inChunk.isEmpty())
					return;

				BlockPos p = inChunk.remove(rand.nextInt(inChunk.size()));
				p = p.offset(Direction.DOWN);
				if (world.isAirBlock(p))
					world.setBlockState(p, CRYSTAL.with(BloodCrystalBlock.FACING, Direction.DOWN), 2);
			}
		}

		private void addHoles(ISeedReader world, Random rand, List<BlockPos> inChunk) {
			for (int i = 0; i < 4; i++) {
				if (inChunk.isEmpty())
					return;

				BlockPos p = inChunk.remove(rand.nextInt(inChunk.size()));
				world.setBlockState(p, AIR, 2);
			}
		}

		private void addStalactites(ISeedReader world, Random rand, List<BlockPos> inChunk) {
			for (int i = 0; i < rand.nextInt(2) + 2; i++) {
				if (inChunk.isEmpty())
					return;

				BlockPos p = inChunk.remove(rand.nextInt(inChunk.size()));
				int height = rand.nextInt(3) + 2;
				for (int j = 1; j < height; j++) {
					if (!world.isAirBlock(p.down(j + 2))) {
						height = j;
						break;
					}
					world.setBlockState(p.down(j), ROCK, 2);
				}
				p = p.down(height - 1);

				if (BloodBatEntity.isValidLedgePos(world, p, null)) {
					BloodBatEntity bat = ModEntities.BLOOD_BAT.create(world.getWorld());
					Vector3d position = Vector3d.copyCenteredHorizontally(p).add(0, -1.75, 0);
					bat.setPositionAndRotation(position.x, position.y, position.z, rand.nextFloat() * 360, 0);
					bat.startHanging(position);
					world.addEntity(bat);
				}
			}
		}

		private Set<BlockPos> getWallSlice(BlockPos pos, int y, int radiusX, int radiusZ) {
			Set<BlockPos> slice = new HashSet<>();
			for (float i = 0; i < Math.PI * 2; i += STEP) {
				BlockPos p = pos.add(radiusX * MathHelper.cos(i), y, radiusZ * MathHelper.sin(i));
				slice.add(p);
			}
			return slice;
		}

		private void setLairWall(ISeedReader world, Random rand, BlockPos pos, Set<BlockPos> wall,
				MutableBoundingBox box) {
			setBlockState(world, rand.nextDouble() < 0.1 || wall.contains(pos.up()) ? ROCK : MOSS, pos.getX(),
					pos.getY(), pos.getZ(), box);
		}
	}

}
