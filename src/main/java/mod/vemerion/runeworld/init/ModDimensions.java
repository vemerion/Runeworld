package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ModDimensions {
	public static final ResourceKey<Level> BLOOD = ResourceKey.create(Registry.DIMENSION_REGISTRY,
			new ResourceLocation(Main.MODID, "blood"));
	public static final ResourceKey<Level> FIRE = ResourceKey.create(Registry.DIMENSION_REGISTRY,
			new ResourceLocation(Main.MODID, "fire"));
}
