package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.effect.BloodDrainedEffect;
import mod.vemerion.runeworld.effect.MonkeyCurseEffect;
import mod.vemerion.runeworld.helpers.Helper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
	
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Main.MODID);

	public static final RegistryObject<MobEffect> BLOOD_DRAINED = EFFECTS.register("blood_drained", () -> new BloodDrainedEffect(MobEffectCategory.HARMFUL, Helper.color(150, 20, 20, 255)));
	public static final RegistryObject<MobEffect> MONKEY_CURSE = EFFECTS.register("monkey_curse", () -> new MonkeyCurseEffect(MobEffectCategory.HARMFUL, 0x27231f));
}
