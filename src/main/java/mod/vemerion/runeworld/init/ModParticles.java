package mod.vemerion.runeworld.init;

import com.mojang.serialization.Codec;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.particle.RunePortalParticleData;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
	
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Main.MODID);

	public static final RegistryObject<SimpleParticleType> DRIPPING_BLOOD = PARTICLES.register("dripping_blood", () -> new SimpleParticleType(false));
	public static final RegistryObject<ParticleType<RunePortalParticleData>> RUNE_PORTAL = PARTICLES.register("rune_portal", () -> new ParticleType<RunePortalParticleData>(false, new RunePortalParticleData.Deserializer()) {

		@Override
		public Codec<RunePortalParticleData> codec() {
			return RunePortalParticleData.CODEC;
		}
	});
	public static final RegistryObject<SimpleParticleType> BLOOD_DROP = PARTICLES.register("blood_drop", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> BLOOD_SPLASH = PARTICLES.register("blood_splash", () -> new SimpleParticleType(false));
}
