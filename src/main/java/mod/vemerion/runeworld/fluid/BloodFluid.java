package mod.vemerion.runeworld.fluid;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
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
	}

	public static class Source extends ForgeFlowingFluid.Source {
		public Source() {
			super(properties);
		}
	}
}
