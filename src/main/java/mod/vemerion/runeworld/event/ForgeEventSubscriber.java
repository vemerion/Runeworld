package mod.vemerion.runeworld.event;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.RunePortalBlock;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.Runesword;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = Main.MODID)
public class ForgeEventSubscriber {

	// For testing world gen features
//	@SubscribeEvent
//	public static void onBiomeLoad(BiomeLoadingEvent event) {
//		event.getGeneration().withFeature(GenerationStage.Decoration.LAKES, ModConfiguredFeatures.BLOOD_POOL);
//	}

	@SubscribeEvent
	public static void createPortal(PlayerInteractEvent.RightClickBlock event) {
		if (event.getWorld().isRemote)
			return;
		ItemStack stack = event.getItemStack();
		BlockState state = event.getWorld().getBlockState(event.getPos());
		if (stack.getItem() == Runesword.BLOOD_RUNE && state.isIn(Tags.Blocks.OBSIDIAN)) {
			RunePortalBlock.createPortal(event.getWorld(), event.getPos(), ModBlocks.BLOOD_RUNE_PORTAL);
			if (!event.getPlayer().isCreative())
				stack.shrink(1);
		}
	}
}
