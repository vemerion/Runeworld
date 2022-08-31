package mod.vemerion.runeworld.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public abstract class SlingshotEnchantment extends Enchantment {

	protected SlingshotEnchantment(Rarity pRarity) {
		super(pRarity, ModEnchantmentCategories.SLINGSHOT, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
	}

	@Override
	public boolean isTradeable() {
		return false;
	}

	public boolean isTreasureOnly() {
		return true;
	}

	public static class QuickDrawEnchantment extends SlingshotEnchantment {

		public QuickDrawEnchantment() {
			super(Rarity.UNCOMMON);
		}

		public int getMinCost(int pEnchantmentLevel) {
			return pEnchantmentLevel * 25;
		}

		public int getMaxCost(int pEnchantmentLevel) {
			return getMinCost(pEnchantmentLevel) + 50;
		}

		public int getMaxLevel() {
			return 3;
		}

	}

	public static class RetentionEnchantment extends SlingshotEnchantment {

		public RetentionEnchantment() {
			super(Rarity.UNCOMMON);
		}

		public int getMinCost(int pEnchantmentLevel) {
			return pEnchantmentLevel * 25;
		}

		public int getMaxCost(int pEnchantmentLevel) {
			return getMinCost(pEnchantmentLevel) + 50;
		}

		public int getMaxLevel() {
			return 3;
		}

	}

	public static class HardnessEnchantment extends SlingshotEnchantment {

		public HardnessEnchantment() {
			super(Rarity.UNCOMMON);
		}

		public int getMinCost(int pEnchantmentLevel) {
			return pEnchantmentLevel * 25;
		}

		public int getMaxCost(int pEnchantmentLevel) {
			return getMinCost(pEnchantmentLevel) + 50;
		}

		public int getMaxLevel() {
			return 4;
		}

	}

	public static class ElasticEnchantment extends SlingshotEnchantment {

		public ElasticEnchantment() {
			super(Rarity.COMMON);
		}

		public int getMinCost(int pEnchantmentLevel) {
			return pEnchantmentLevel * 25;
		}

		public int getMaxCost(int pEnchantmentLevel) {
			return getMinCost(pEnchantmentLevel) + 50;
		}

		public int getMaxLevel() {
			return 5;
		}

	}

}
