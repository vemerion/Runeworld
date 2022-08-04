package mod.vemerion.runeworld.item;

import mod.vemerion.runeworld.entity.BloodBatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;

public class BloodCrystalliteItem extends Item {

	public BloodCrystalliteItem() {
		super(new Item.Properties().tab(CreativeModeTab.TAB_SEARCH));

		// Convert crystallite to diamond
		CauldronInteraction.WATER.put(this,
				(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack) -> {
					if (!level.isClientSide) {
						LayeredCauldronBlock.lowerFillLevel(state, level, pos);

						if (!player.isCreative())
							stack.shrink(1);

						ItemHandlerHelper.giveItemToPlayer(player, Items.DIAMOND.getDefaultInstance());
					}

					return InteractionResult.sidedSuccess(level.isClientSide);
				});
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (worldIn.isClientSide || !(entityIn instanceof Player) || ((Player) entityIn).isCreative())
			return;
		for (BloodBatEntity bat : worldIn.getEntitiesOfClass(BloodBatEntity.class, entityIn.getBoundingBox().inflate(5),
				bat -> bat.isHanging())) {
			bat.stopHanging();
			bat.setTarget((Player) entityIn);
		}
	}

}
