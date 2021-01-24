package mod.vemerion.runeworld.fluid;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModFluids;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.ForgeFlowingFluid;

// TODO: Blood bucket
// TODO: Maybe add water tag to blood, to add water physics etc?
public class BloodFluid {
	private static final ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(
			() -> ModFluids.BLOOD, () -> ModFluids.BLOOD_FLOWING,
			net.minecraftforge.fluids.FluidAttributes.builder(
					new ResourceLocation("block/water_still"),
					new ResourceLocation("block/water_flow"))
					.color(Helper.color(255, 0, 0, 255))
					.translationKey("block." + Main.MODID + "blood")).block(() -> ModBlocks.BLOOD)
							.slopeFindDistance(4).levelDecreasePerBlock(1).explosionResistance(100F);
	
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
