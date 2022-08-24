package mod.vemerion.runeworld.structure;

import com.mojang.serialization.Codec;

import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.JigsawFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

public class BloodMonkeyTunnelsStructure extends JigsawFeature {
	public BloodMonkeyTunnelsStructure(Codec<JigsawConfiguration> codec) {
		super(codec, 28, false, false, context -> true);
	}
	
	@Override
	public Decoration step() {
		return Decoration.UNDERGROUND_STRUCTURES;
	}
}