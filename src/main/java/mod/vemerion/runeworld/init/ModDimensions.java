package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ModDimensions {
	public static final RegistryKey<World> BLOOD = RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
			new ResourceLocation(Main.MODID, "blood"));
}
