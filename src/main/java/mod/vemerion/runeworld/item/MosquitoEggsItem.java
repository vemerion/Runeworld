package mod.vemerion.runeworld.item;

import mod.vemerion.runeworld.entity.MosquitoEggsEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MosquitoEggsItem extends Item {

	public MosquitoEggsItem() {
		super(new Item.Properties().group(ItemGroup.SEARCH));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack heldStack = playerIn.getHeldItem(handIn);
		if (!worldIn.isRemote) {
			MosquitoEggsEntity eggs = new MosquitoEggsEntity(playerIn, worldIn);
			eggs.setItem(heldStack);
			eggs.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
			eggs.setMotion(eggs.getMotion().scale(0.5));
			worldIn.addEntity(eggs);
		}

		if (!playerIn.abilities.isCreativeMode) {
			heldStack.shrink(1);
		}

		return ActionResult.func_233538_a_(heldStack, worldIn.isRemote());
	}

}
