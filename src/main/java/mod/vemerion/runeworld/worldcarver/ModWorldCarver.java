package mod.vemerion.runeworld.worldcarver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;

import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;

public class ModWorldCarver extends CaveWorldCarver {

	public ModWorldCarver(Codec<CaveCarverConfiguration> codec) {
		super(codec);
		this.replaceableBlocks = ImmutableSet.of(ModBlocks.CHARRED_DIRT.get(), ModBlocks.CHARRED_STONE.get(),
				ModBlocks.BURNT_DIRT.get());
	}

}
