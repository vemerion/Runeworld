package mod.vemerion.runeworld.particle;

import java.util.Locale;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import mod.vemerion.runeworld.init.ModParticleTypes;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

public class RunePortalParticleData implements IParticleData {

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
		return ModParticleTypes.RUNE_PORTAL;
	}

	@Override
	public void write(PacketBuffer buffer) {
		buffer.writeFloat(getRed());
		buffer.writeFloat(getGreen());
		buffer.writeFloat(getBlue());
	}

	@Override
	public String getParameters() {
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

	public static class Deserializer implements IParticleData.IDeserializer<RunePortalParticleData> {

		@Override
		public RunePortalParticleData deserialize(ParticleType<RunePortalParticleData> particleTypeIn,
				StringReader reader) throws CommandSyntaxException {
			float colors[] = new float[3];
			for (int i = 0; i < 3; i++) {
				reader.expect(' ');
				colors[i] = reader.readFloat();
			}
			return new RunePortalParticleData(colors[0], colors[1], colors[2]);
		}

		@Override
		public RunePortalParticleData read(ParticleType<RunePortalParticleData> particleTypeIn, PacketBuffer buffer) {
			return new RunePortalParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
		}

	}
}
