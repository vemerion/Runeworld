package mod.vemerion.runeworld.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.WaterDropParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class BloodSplashParticle extends WaterDropParticle {

	protected BloodSplashParticle(ClientLevel level, double x, double y, double z) {
		super(level, x, y, z);
		this.setColor(0.8f, 0, 0);
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet sprites) {
			this.sprites = sprites;
		}

		public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ,
				double pXSpeed, double pYSpeed, double pZSpeed) {
			var particle = new BloodSplashParticle(pLevel, pX, pY, pZ);
			particle.pickSprite(sprites);
			return particle;
		}
	}
}
