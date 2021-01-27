package mod.vemerion.runeworld.item;

import mod.vemerion.runeworld.init.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;

public class BloodFlowerItem extends BlockItem {

	public BloodFlowerItem() {
		super(ModBlocks.BLOOD_FLOWER, new Item.Properties().group(ItemGroup.SEARCH));
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		return ActionResultType.PASS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		BlockRayTraceResult rayTraceResult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
		BlockRayTraceResult rayTraceResultUp = rayTraceResult.withPosition(rayTraceResult.getPos().up());
		ActionResultType result = super.onItemUse(new ItemUseContext(playerIn, handIn, rayTraceResultUp));
		return new ActionResult<>(result, playerIn.getHeldItem(handIn));
	}
}
