package mod.vemerion.runeworld.event;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.RunePortalBlock;
import mod.vemerion.runeworld.capability.RuneTeleport;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModSounds;
import mod.vemerion.runeworld.init.Runesword;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
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
		BlockPos pos = event.getPos();
		if (Runesword.isRune(stack.getItem()) && state.isIn(Tags.Blocks.OBSIDIAN)) {
			if (RunePortalBlock.createPortal(event.getWorld(), pos,
					RunePortalBlock.getPortalFromRune(stack.getItem()))) {
				if (!event.getPlayer().isCreative())
					stack.shrink(1);
				event.getWorld().playSound(null, pos.getX(), pos.getY(), pos.getZ(), ModSounds.PORTAL,
						SoundCategory.PLAYERS, 1, Helper.soundPitch(event.getPlayer().getRNG()));
			}
		}
	}

	@SubscribeEvent
	public static void playerTick(PlayerTickEvent event) {
		PlayerEntity player = event.player;
		if (player.world.isRemote || event.phase == Phase.END)
			return;
		RuneTeleport.getRuneTeleport(player).ifPresent(tp -> tp.tick(player));
	}
}
