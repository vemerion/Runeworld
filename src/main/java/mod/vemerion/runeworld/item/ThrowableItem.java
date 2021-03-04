package mod.vemerion.runeworld.item;

import java.util.function.Supplier;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ThrowableItem extends Item {

	private Supplier<EntityType<? extends ProjectileItemEntity>> projectile;
	private double motionScale;

	public ThrowableItem(Supplier<EntityType<? extends ProjectileItemEntity>> projectile, double motionScale) {
		super(new Item.Properties().group(ItemGroup.SEARCH));
		this.projectile = projectile;
		this.motionScale = motionScale;
	}

	public ThrowableItem(Supplier<EntityType<? extends ProjectileItemEntity>> supplier) {
		this(supplier, 1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack heldStack = playerIn.getHeldItem(handIn);
		if (!worldIn.isRemote) {
			ProjectileItemEntity entity = projectile.get().create(worldIn);
			entity.setShooter(playerIn);
			entity.setPosition(playerIn.getPosX(), playerIn.getPosYEye() - 0.1f, playerIn.getPosZ());
			entity.setItem(heldStack);
			entity.func_234612_a_(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
			entity.setMotion(entity.getMotion().scale(motionScale));
			worldIn.addEntity(entity);
		}

		if (!playerIn.abilities.isCreativeMode) {
			heldStack.shrink(1);
		}

		return ActionResult.func_233538_a_(heldStack, worldIn.isRemote());
	}

}
