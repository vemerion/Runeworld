package mod.vemerion.runeworld.item;

import mod.vemerion.runesword.api.RuneswordAPI;
import mod.vemerion.runeworld.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GuideItem extends Item {

	public GuideItem(Properties properties) {
		super(properties);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		ItemStack itemstack = playerIn.getItemInHand(handIn);
		if (!worldIn.isClientSide)
			RuneswordAPI.guide.openGuide(playerIn, new ResourceLocation(Main.MODID, "guide").toString());

		return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());

	}
}
