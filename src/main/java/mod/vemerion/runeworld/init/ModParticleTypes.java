package mod.vemerion.runeworld.init;

import com.mojang.serialization.Codec;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.particle.RunePortalParticleData;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModParticleTypes {

	public static final BasicParticleType DRIPPING_BLOOD = null;
	public static final ParticleType<RunePortalParticleData> RUNE_PORTAL = null;

	@SubscribeEvent
	public static void onRegisterParticle(RegistryEvent.Register<ParticleType<?>> event) {
		event.getRegistry().register(Init.setup(new BasicParticleType(false), "dripping_blood"));
		event.getRegistry().register(
				Init.setup(new ParticleType<RunePortalParticleData>(false, new RunePortalParticleData.Deserializer()) {

					@Override
					public Codec<RunePortalParticleData> func_230522_e_() {
						return RunePortalParticleData.CODEC;
					}
				}, "rune_portal"));
	}
}
