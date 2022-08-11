package mod.vemerion.runeworld.item;

import java.util.List;
import java.util.function.Consumer;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.ModLayerLocations;
import mod.vemerion.runeworld.model.BloodCrownModel;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;

public class BloodCrownItem extends ArmorItem {

	public BloodCrownItem() {
		super(ArmorMaterials.DIAMOND, EquipmentSlot.HEAD,
				new Item.Properties().tab(CreativeModeTab.TAB_SEARCH).stacksTo(1));
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return Main.MODID + ":textures/armor/blood_crown.png";
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(new TranslatableComponent("item." + Main.MODID + "." + ModItems.BLOOD_CROWN.getId().getPath() + ".description", "Majestic").withStyle(ChatFormatting.GOLD)
				.withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, level, tooltip, flagIn);
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new RenderProperties());
	}

	private static class RenderProperties implements IItemRenderProperties {

		private BloodCrownModel model;

		@Override
		public Model getBaseArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot,
				HumanoidModel<?> _default) {
			if (model == null)
				model = new BloodCrownModel(
						Minecraft.getInstance().getEntityModels().bakeLayer(ModLayerLocations.BLOOD_CROWN));

			model.base.visible = _default.head.visible;
			model.base.copyFrom(_default.head);

			return model;
		}
	}
}
