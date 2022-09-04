package mod.vemerion.runeworld;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.vemerion.runeworld.init.ModBiomes;
import mod.vemerion.runeworld.init.ModBlockEntities;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModConfiguredFeatures;
import mod.vemerion.runeworld.init.ModConfiguredStructures;
import mod.vemerion.runeworld.init.ModConfiguredWorldCarvers;
import mod.vemerion.runeworld.init.ModEffects;
import mod.vemerion.runeworld.init.ModEnchantments;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModFeatures;
import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.ModLootModifiers;
import mod.vemerion.runeworld.init.ModParticles;
import mod.vemerion.runeworld.init.ModPlacedFeatures;
import mod.vemerion.runeworld.init.ModSounds;
import mod.vemerion.runeworld.init.ModStructurePieces;
import mod.vemerion.runeworld.init.ModStructureSets;
import mod.vemerion.runeworld.init.ModStructures;
import mod.vemerion.runeworld.init.ModTemplatePools;
import mod.vemerion.runeworld.init.ModWorldCarvers;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MODID)
public class Main {
	public static final String MODID = "runeworld";
	
    public static final Logger LOGGER = LogManager.getLogger();
    
    public Main() {
		var bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModFluids.FLUIDS.register(bus);
		ModItems.ITEMS.register(bus);
		ModLootModifiers.LOOT_MODIFIERS.register(bus);
		ModSounds.SOUNDS.register(bus);
		ModEntities.ENTITIES.register(bus);
		ModBlocks.BLOCKS.register(bus);
		ModBiomes.BIOMES.register(bus);
		ModConfiguredFeatures.CONFIGURED_FEATURES.register(bus);
		ModFeatures.FEATURES.register(bus);
		ModPlacedFeatures.PLACED_FEATURES.register(bus);
		ModConfiguredStructures.CONFIGURED_STRUCTURES.register(bus);
		ModStructurePieces.STRUCTURE_PIECES.register(bus);
		ModStructures.STRUCTURES.register(bus);
		ModStructureSets.STRUCTURE_SETS.register(bus);
		ModTemplatePools.TEMPLATE_POOLS.register(bus);
		ModBlockEntities.BLOCK_ENTITIES.register(bus);
		ModEffects.EFFECTS.register(bus);
		ModParticles.PARTICLES.register(bus);
		ModEnchantments.ENCHANTMENTS.register(bus);
		ModWorldCarvers.WORLD_CARVERS.register(bus);
		ModConfiguredWorldCarvers.CONFIGURED_WORLD_CARVERS.register(bus);
	}
}
