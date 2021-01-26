package mod.vemerion.runeworld.particle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModFluids;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class DrippingBloodFactory implements IParticleFactory<BasicParticleType> {

	private IAnimatedSprite sprite;
	Constructor<DripParticle> dripConstructor;

	public DrippingBloodFactory(IAnimatedSprite sprite) {
		this.sprite = sprite;

		dripConstructor = ObfuscationReflectionHelper.findConstructor(DripParticle.class, ClientWorld.class,
				double.class, double.class, double.class, Fluid.class);
	}

	@Override
	public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z,
			double xSpeed, double ySpeed, double zSpeed) {
		DripParticle particle = null;
		try {
			particle = dripConstructor.newInstance(worldIn, x, y, z, ModFluids.BLOOD);
			particle.setColor(1, 0, 0);
			particle.selectSpriteRandomly(sprite);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			Main.LOGGER.warn("Could not construct a new drip particle: " + e.getMessage());
		}

		return particle;
	}
}
