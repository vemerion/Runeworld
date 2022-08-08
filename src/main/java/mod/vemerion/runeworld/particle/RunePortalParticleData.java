package mod.vemerion.runeworld.particle;

import java.util.Locale;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import mod.vemerion.runeworld.init.ModParticles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

public class RunePortalParticleData implements ParticleOptions {

	public static final Codec<RunePortalParticleData> CODEC = RecordCodecBuilder.create((instance) -> {
		return instance.group(Codec.FLOAT.fieldOf("r").forGetter((data) -> {
			return data.getRed();
		}), Codec.FLOAT.fieldOf("g").forGetter((data) -> {
			return data.getGreen();
		}), Codec.FLOAT.fieldOf("b").forGetter((data) -> {
			return data.getBlue();
		})).apply(instance, RunePortalParticleData::new);
	});

	private float red, green, blue;

	public RunePortalParticleData(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	@Override
	public ParticleType<?> getType() {
		return ModParticles.RUNE_PORTAL;
	}

	@Override
	public void writeToNetwork(FriendlyByteBuf buffer) {
		buffer.writeFloat(getRed());
		buffer.writeFloat(getGreen());
		buffer.writeFloat(getBlue());
	}

	@Override
	public String writeToString() {
		return String.format(Locale.ROOT, "%s %.2f %.2f %.2f", getType().getRegistryName().toString(), getRed(),
				getGreen(), getBlue());
	}

	public float getRed() {
		return red;
	}

	public float getGreen() {
		return green;
	}

	public float getBlue() {
		return blue;
	}

	public static class Deserializer implements ParticleOptions.Deserializer<RunePortalParticleData> {

		@Override
		public RunePortalParticleData fromCommand(ParticleType<RunePortalParticleData> particleTypeIn,
				StringReader reader) throws CommandSyntaxException {
			float colors[] = new float[3];
			for (int i = 0; i < 3; i++) {
				reader.expect(' ');
				colors[i] = reader.readFloat();
			}
			return new RunePortalParticleData(colors[0], colors[1], colors[2]);
		}

		@Override
		public RunePortalParticleData fromNetwork(ParticleType<RunePortalParticleData> particleTypeIn, FriendlyByteBuf buffer) {
			return new RunePortalParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
		}

	}
}
