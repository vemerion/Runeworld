package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.BloodBlock;
import mod.vemerion.runeworld.block.BloodFlowerBlock;
import mod.vemerion.runeworld.block.BloodPillarBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModBlocks {
	
	public static final FlowingFluidBlock BLOOD = null;
	public static final Block BLOOD_FLOWER = null;
	public static final BloodPillarBlock BLOOD_PILLAR_LARGE = null;
	public static final BloodPillarBlock BLOOD_PILLAR_MEDIUM = null;
	public static final BloodPillarBlock BLOOD_PILLAR_SMALL = null;
	public static final Block BLOOD_ROCK = null;
	public static final Block BLOOD_MOSS = null;

	@SubscribeEvent
	public static void onRegisterBlock(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(Init.setup(new BloodBlock(), "blood"));
		event.getRegistry().register(Init.setup(new BloodFlowerBlock(), "blood_flower"));
		event.getRegistry().register(Init.setup(new BloodPillarBlock(BloodPillarBlock.LARGE), "blood_pillar_large"));
		event.getRegistry().register(Init.setup(new BloodPillarBlock(BloodPillarBlock.MEDIUM), "blood_pillar_medium"));
		event.getRegistry().register(Init.setup(new BloodPillarBlock(BloodPillarBlock.SMALL), "blood_pillar_small"));
		event.getRegistry().register(Init.setup(new Block(AbstractBlock.Properties.from(Blocks.COBBLESTONE)), "blood_rock"));
		event.getRegistry().register(Init.setup(new Block(AbstractBlock.Properties.from(Blocks.COBBLESTONE)), "blood_moss"));
	}
}
