package mod.vemerion.runeworld.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.world.ClientWorld;

public class RunePortalParticle extends PortalParticle {

	protected RunePortalParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY,
			double motionZ, RunePortalParticleData data) {
		super(world, x, y, z, motionX, motionY, motionZ);
		this.particleRed = data.getRed() * 0.7f;
		this.particleGreen = data.getGreen() * 0.7f;
		this.particleBlue = data.getBlue() * 0.7f;
	}

	public static class Factory implements IParticleFactory<RunePortalParticleData> {
		private final IAnimatedSprite spriteSet;

		public Factory(IAnimatedSprite spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle makeParticle(RunePortalParticleData data, ClientWorld worldIn, double x, double y, double z,
				double xSpeed, double ySpeed, double zSpeed) {
			PortalParticle particle = new RunePortalParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, data);
			particle.selectSpriteRandomly(spriteSet);
			return particle;
		}
	}
}
