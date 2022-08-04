package mod.vemerion.runeworld.particle;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.multiplayer.ClientLevel;

public class RunePortalParticle extends PortalParticle {

	protected RunePortalParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY,
			double motionZ, RunePortalParticleData data) {
		super(world, x, y, z, motionX, motionY, motionZ);
		this.rCol = data.getRed() * 0.7f;
		this.gCol = data.getGreen() * 0.7f;
		this.bCol = data.getBlue() * 0.7f;
	}

	public static class Factory implements ParticleProvider<RunePortalParticleData> {
		private final SpriteSet spriteSet;

		public Factory(SpriteSet spriteSet) {
			this.spriteSet = spriteSet;
		}

		@Override
		public Particle createParticle(RunePortalParticleData data, ClientLevel worldIn, double x, double y, double z,
				double xSpeed, double ySpeed, double zSpeed) {
			PortalParticle particle = new RunePortalParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, data);
			particle.pickSprite(spriteSet);
			return particle;
		}
	}
}
