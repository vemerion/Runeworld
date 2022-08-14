package mod.vemerion.runeworld.item;

import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;

public class BloodFlowerItem extends BlockItem {

	public BloodFlowerItem() {
		super(ModBlocks.BLOOD_FLOWER.get(), new Item.Properties().tab(CreativeModeTab.TAB_SEARCH));
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		return InteractionResult.PASS;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		BlockHitResult rayTraceResult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);
		BlockHitResult rayTraceResultUp = rayTraceResult.withPosition(rayTraceResult.getBlockPos().above());
		InteractionResult result = super.useOn(new UseOnContext(playerIn, handIn, rayTraceResultUp));
		return new InteractionResultHolder<>(result, playerIn.getItemInHand(handIn));
	}
}
