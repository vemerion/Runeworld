package mod.vemerion.runeworld.item;

import mod.vemerion.runesword.api.RuneswordAPI;
import mod.vemerion.runeworld.init.ModGuide;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class GuideItem extends Item {

	public GuideItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (worldIn.isRemote)
			RuneswordAPI.guide.openGuide(ModGuide.getStartChapter());

		return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());

	}
}
