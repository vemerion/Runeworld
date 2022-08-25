package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.biome.BloodPlainsBiome;
import mod.vemerion.runeworld.biome.FirePlainsBiome;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBiomes {
	
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Main.MODID);

	public static final RegistryObject<Biome> BLOOD_PLAINS = BIOMES.register("blood_plains", () -> new BloodPlainsBiome().create());
	public static final RegistryObject<Biome> FIRE_PLAINS = BIOMES.register("fire_plains", () -> new FirePlainsBiome().create());
}
