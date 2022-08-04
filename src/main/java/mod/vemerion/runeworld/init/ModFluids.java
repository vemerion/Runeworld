package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.fluid.BloodFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluids {
	
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Main.MODID);

	public static final RegistryObject<BloodFluid.Flowing> BLOOD_FLOWING = FLUIDS.register("blood_flowing",
			() -> new BloodFluid.Flowing());
	
	public static final RegistryObject<BloodFluid.Source> BLOOD = FLUIDS.register("blood",
			() -> new BloodFluid.Source());
}
