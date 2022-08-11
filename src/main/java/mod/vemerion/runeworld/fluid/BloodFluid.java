package mod.vemerion.runeworld.fluid;

import java.util.Random;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.ModParticles;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class BloodFluid {
	private static final ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(
			ModFluids.BLOOD, ModFluids.BLOOD_FLOWING,
			net.minecraftforge.fluids.FluidAttributes
					.builder(new ResourceLocation("block/water_still"), new ResourceLocation("block/water_flow"))
					.sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
					.color(Helper.color(255, 0, 0, 255))
					.overlay(new ResourceLocation(Main.MODID, "block/blood_overlay"))
					.translationKey("block." + Main.MODID + "blood")).block(() -> ModBlocks.BLOOD).slopeFindDistance(4)
							.levelDecreasePerBlock(1).explosionResistance(100F).tickRate(15).slopeFindDistance(3)
							.levelDecreasePerBlock(2).bucket(ModItems.BLOOD_BUCKET);

	public static class Flowing extends ForgeFlowingFluid.Flowing {

		public Flowing() {
			super(properties);
		}

		@Override
		protected ParticleOptions getDripParticle() {
			return ModParticles.DRIPPING_BLOOD;
		}
	}

	public static class Source extends ForgeFlowingFluid.Source {
		public Source() {
			super(properties);
		}

		@Override
		protected ParticleOptions getDripParticle() {
			return ModParticles.DRIPPING_BLOOD;
		}

		@Override
		protected boolean isRandomlyTicking() {
			return true;
		}

		@Override
		protected void randomTick(Level world, BlockPos pos, FluidState state, Random random) {
			if (world.isClientSide || random.nextDouble() < 0.7)
				return;

			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (Math.abs(i + j) == 1) {
						BlockPos adjacent = pos.offset(i, 1, j);
						BlockState adjacentState = world.getBlockState(adjacent);
						Block adjacentBlock = adjacentState.getBlock();
						if (adjacentBlock instanceof BonemealableBlock && ((BonemealableBlock) adjacentBlock).isValidBonemealTarget(world, adjacent,
								adjacentState, world.isClientSide)) {
							adjacentState.randomTick((ServerLevel) world, adjacent, random);
							if (random.nextDouble() < 0.05)
								world.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
						}
					}
				}
			}
		}
	}
}
