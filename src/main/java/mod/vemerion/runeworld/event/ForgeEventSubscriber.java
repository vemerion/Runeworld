package mod.vemerion.runeworld.event;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.RunePortalBlock;
import mod.vemerion.runeworld.capability.RuneTeleport;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.init.ModEffects;
import mod.vemerion.runeworld.init.ModSounds;
import mod.vemerion.runeworld.init.Runesword;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
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

	// Quickly set jigsaw values
	// TODO: Remove before release
	@SubscribeEvent
	public static void quickUpdateJigsaw(PlayerInteractEvent.RightClickBlock event) {
		var level = event.getPlayer().level;
		var pos = event.getHitVec().getBlockPos();
		var state = level.getBlockState(pos);
		if (state.getBlock() == Blocks.JIGSAW) {
			if (level.getBlockEntity(pos) instanceof JigsawBlockEntity jigsaw) {
				var item = event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem();
				boolean handled = false;
				if (item == Items.DIRT) {
					setJigsawProperties(jigsaw, "blood_monkey_tunnels/mobs/mosquito", "bottom", "bottom", "minecraft:air");
					handled = true;
				} else if (item == Items.STONE) {
					setJigsawProperties(jigsaw, "blood_monkey_tunnels/rooms", "connection", "connection", "minecraft:air");
					handled = true;
				} else if (item == Items.COBBLESTONE) {
					level.setBlock(pos, state.cycle(JigsawBlock.ORIENTATION), 3);
					handled = true;
				}
				event.setCanceled(handled);
			}
		}
	}

	private static void setJigsawProperties(JigsawBlockEntity jigsaw, String pool, String name, String target, String finalState) {
		if (pool != null)
			jigsaw.setPool(new ResourceLocation(Main.MODID, pool));
		jigsaw.setName(new ResourceLocation(Main.MODID, name));
		jigsaw.setTarget(new ResourceLocation(Main.MODID, target));
		jigsaw.setFinalState(finalState);
		jigsaw.setChanged();
	}

	@SubscribeEvent
	public static void createPortal(PlayerInteractEvent.RightClickBlock event) {
		var level = event.getWorld();
		if (level.isClientSide)
			return;

		ItemStack stack = event.getItemStack();
		BlockState state = event.getWorld().getBlockState(event.getPos());
		BlockPos pos = event.getPos();
		if (Runesword.isRune(stack.getItem()) && state.is(Tags.Blocks.OBSIDIAN)) {
			if (RunePortalBlock.createPortal(event.getWorld(), pos,
					RunePortalBlock.getPortalFromRune(stack.getItem()))) {
				if (!event.getPlayer().isCreative())
					stack.shrink(1);
				event.getWorld().playSound(null, pos.getX(), pos.getY(), pos.getZ(), ModSounds.PORTAL.get(),
						SoundSource.PLAYERS, 1, Helper.soundPitch(event.getPlayer().getRandom()));
			}
		}
	}

	@SubscribeEvent
	public static void playerTick(PlayerTickEvent event) {
		Player player = event.player;
		if (player.level.isClientSide || event.phase == Phase.END)
			return;
		RuneTeleport.getRuneTeleport(player).ifPresent(tp -> tp.tick(player));
	}
	
	@SubscribeEvent
	public static void persistMonkeyCurse(PlayerEvent.Clone event) {
		Player original = event.getOriginal();
		Player player = event.getPlayer();
		if (original.hasEffect(ModEffects.MONKEY_CURSE.get()))
			player.addEffect(original.getEffect(ModEffects.MONKEY_CURSE.get()));
	}
}
