package mod.vemerion.runeworld;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Main.MODID)
public class Main {
	public static final String MODID = "runeworld";
	
    public static final Logger LOGGER = LogManager.getLogger();
    
    public Main() {
		var bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModFluids.FLUIDS.register(bus);
		ModItems.Deferred.ITEMS.register(bus);
	}
    
    /*
     * TODO: Use LootTableIdCondition
     * TODO: Use DeferredRegisters
     * TODO: Use datagen for sounds
     * TODO: Use new guide api
     */
}
