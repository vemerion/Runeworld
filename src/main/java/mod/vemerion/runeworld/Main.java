package mod.vemerion.runeworld;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.ModLootModifiers;
import mod.vemerion.runeworld.init.ModSounds;
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
	}
    
    /*
     * TODO: Use DeferredRegisters
     */
}
