package mod.vemerion.runeworld.structure;

import java.util.Random;

import com.mojang.serialization.Codec;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModStructurePieces;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class FireRitualStructure extends Structure<NoFeatureConfig> {

	public FireRitualStructure(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public Decoration getDecorationStage() {
		return Decoration.SURFACE_STRUCTURES;
	}

	@Override
	public String getStructureName() {
		return Main.MODID + ":fire_ritual";
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
			components.add(new Piece(manager, new BlockPos(x, height, z)));
			recalculateStructureSize();
		}
	}

	public static class Piece extends TemplateStructurePiece {

		private static final ResourceLocation TEMPLATE = new ResourceLocation(Main.MODID, "fire_ritual");

		public Piece(TemplateManager templateManager, BlockPos pos) {
			super(ModStructurePieces.BLOOD_BAT_LAIR_PIECE, 0);
			this.templatePosition = pos;
			this.init(templateManager);
		}

		public Piece(TemplateManager templateManager, CompoundNBT nbt) {
			super(ModStructurePieces.FIRE_RITUAL_PIECE, nbt);
			this.init(templateManager);
		}

		private void init(TemplateManager templateManager) {
			Template template = templateManager.getTemplateDefaulted(TEMPLATE);
			PlacementSettings placementsettings = new PlacementSettings()
					.addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK);
			setup(template, templatePosition, placementsettings);
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand,
				MutableBoundingBox sbb) {

		}

	}

}
