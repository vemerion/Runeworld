package mod.vemerion.runeworld.item;

import java.util.function.Supplier;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThrowableItem extends Item {

	private Supplier<EntityType<? extends ThrowableItemProjectile>> projectile;
	private double motionScale;

	public ThrowableItem(Supplier<EntityType<? extends ThrowableItemProjectile>> projectile, double motionScale) {
		super(new Item.Properties().tab(CreativeModeTab.TAB_SEARCH));
		this.projectile = projectile;
		this.motionScale = motionScale;
	}

	public ThrowableItem(Supplier<EntityType<? extends ThrowableItemProjectile>> supplier) {
		this(supplier, 1);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn) {
		ItemStack stack = playerIn.getItemInHand(handIn);

		if (!level.isClientSide) {
			var entity = projectile.get().create(level);
			entity.setOwner(playerIn);
			entity.setPos(playerIn.getX(), playerIn.getEyeY() - 0.1f, playerIn.getZ());
			entity.setItem(stack);
			entity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 1.5F, 1.0F);
			entity.setDeltaMovement(entity.getDeltaMovement().scale(motionScale));
			level.addFreshEntity(entity);
		}

		if (!playerIn.getAbilities().instabuild) {
			stack.shrink(1);
		}

		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
	}
}
