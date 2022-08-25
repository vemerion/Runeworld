package mod.vemerion.runeworld.particle;

import mod.vemerion.runeworld.init.ModParticles;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class BloodDropParticle extends TextureSheetParticle {

	protected BloodDropParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed,
			double zspeed) {
		super(level, x, y, z, xSpeed, ySpeed, zspeed);
		this.setParticleSpeed(xSpeed, ySpeed, zspeed);
		this.setLifetime(20 * 10);
		this.gravity = 1;
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void tick() {
		super.tick();

		if (onGround) {
			level.addParticle(ModParticles.BLOOD_SPLASH.get(), x, y + 1, z, 0, 0, 0);
			remove();
		}
	}

	public static class Provider implements ParticleProvider<SimpleParticleType> {
		private final SpriteSet sprites;

		public Provider(SpriteSet sprites) {
			this.sprites = sprites;
		}

		@Override
		public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z,
				double xSpeed, double ySpeed, double zSpeed) {
			var particle = new BloodDropParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
			particle.pickSprite(sprites);
			return particle;
		}
	}
}
