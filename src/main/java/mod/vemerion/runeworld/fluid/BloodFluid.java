package mod.vemerion.runeworld.fluid;

import java.util.Random;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.ModParticleTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.fluid.FluidState;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class BloodFluid {
	private static final ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(
			() -> ModFluids.BLOOD, () -> ModFluids.BLOOD_FLOWING,
			net.minecraftforge.fluids.FluidAttributes
					.builder(new ResourceLocation("block/water_still"), new ResourceLocation("block/water_flow"))
					.sound(SoundEvents.ITEM_BUCKET_FILL, SoundEvents.ITEM_BUCKET_EMPTY)
					.color(Helper.color(255, 0, 0, 255))
					.overlay(new ResourceLocation(Main.MODID, "block/blood_overlay"))
					.translationKey("block." + Main.MODID + "blood")).block(() -> ModBlocks.BLOOD).slopeFindDistance(4)
							.levelDecreasePerBlock(1).explosionResistance(100F).tickRate(15).slopeFindDistance(3)
							.levelDecreasePerBlock(2).bucket(() -> ModItems.BLOOD_BUCKET);

	public static class Flowing extends ForgeFlowingFluid.Flowing {

		public Flowing() {
			super(properties);
		}

		@Override
		protected IParticleData getDripParticleData() {
			return ModParticleTypes.DRIPPING_BLOOD;
		}
	}

	public static class Source extends ForgeFlowingFluid.Source {
		public Source() {
			super(properties);
		}

		@Override
		protected IParticleData getDripParticleData() {
			return ModParticleTypes.DRIPPING_BLOOD;
		}

		@Override
		protected boolean ticksRandomly() {
			return true;
		}

		@Override
		protected void randomTick(World world, BlockPos pos, FluidState state, Random random) {
			if (world.isRemote || random.nextDouble() < 0.7)
				return;

			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (Math.abs(i + j) == 1) {
						BlockPos adjacent = pos.add(i, 1, j);
						BlockState adjacentState = world.getBlockState(adjacent);
						Block adjacentBlock = adjacentState.getBlock();
						if (adjacentBlock instanceof IGrowable && ((IGrowable) adjacentBlock).canGrow(world, adjacent,
								adjacentState, world.isRemote)) {
							adjacentState.randomTick((ServerWorld) world, adjacent, random);
							if (random.nextDouble() < 0.05)
								world.setBlockState(pos, Blocks.WATER.getDefaultState());
						}
					}
				}
			}
		}
	}
}
