package mod.vemerion.runeworld.particle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModFluids;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraft.core.particles.SimpleParticleType;

public class DrippingBloodProvider implements ParticleProvider<SimpleParticleType> {

	private SpriteSet sprite;
	Constructor<DripParticle> dripConstructor;

	public DrippingBloodProvider(SpriteSet sprite) {
		this.sprite = sprite;

		dripConstructor = ObfuscationReflectionHelper.findConstructor(DripParticle.class, ClientLevel.class,
				double.class, double.class, double.class, Fluid.class);
	}

	@Override
	public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z,
			double xSpeed, double ySpeed, double zSpeed) {
		DripParticle particle = null;
		try {
			particle = dripConstructor.newInstance(worldIn, x, y, z, ModFluids.BLOOD);
			particle.setColor(1, 0, 0);
			particle.pickSprite(sprite);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			Main.LOGGER.warn("Could not construct a new drip particle: " + e.getMessage());
		}

		return particle;
	}
}
