package mod.vemerion.runeworld.structure;

import java.util.Random;

import com.mojang.serialization.Codec;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModStructurePieces;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
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
			components.add(new Piece(new BlockPos(x, height, z)));
			recalculateStructureSize();
		}

	}

	public static class Piece extends StructurePiece {

		private BlockPos center;

		public Piece(BlockPos center) {
			super(ModStructurePieces.BLOOD_BAT_LAIR_PIECE, 0);
			this.center = center;
			boundingBox = new MutableBoundingBox(center, center);
		}

		public Piece(TemplateManager manager, CompoundNBT nbt) {
			super(ModStructurePieces.BLOOD_BAT_LAIR_PIECE, nbt);
			this.center = NBTUtil.readBlockPos(nbt.getCompound("center"));
			boundingBox = new MutableBoundingBox(center, center);
		}

		@Override
		protected void readAdditional(CompoundNBT tagCompound) {
			tagCompound.put("center", NBTUtil.writeBlockPos(center));
		}

		@Override
		public boolean func_230383_a_(ISeedReader world, StructureManager manager, ChunkGenerator generator,
				Random rand, MutableBoundingBox box, ChunkPos chunkPos, BlockPos pos) {
			world.setBlockState(center, ModBlocks.BLOOD_PILLAR_LARGE.getDefaultState(), 2);
			return true;
		}

	}

}
