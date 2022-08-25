package mod.vemerion.runeworld.structure;

import com.mojang.serialization.Codec;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModStructures;
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
	
	public static String translationKey() {
		return Main.MODID + ".filled_map." + ModStructures.BLOOD_MONKEY_TUNNELS.getId().getPath();
	}
}