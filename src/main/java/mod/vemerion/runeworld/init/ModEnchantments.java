package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.enchantment.SlingshotEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
	public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Main.MODID);

	public static final RegistryObject<Enchantment> QUICK_DRAW = ENCHANTMENTS.register("quick_draw", SlingshotEnchantment.QuickDrawEnchantment::new);
	public static final RegistryObject<Enchantment> RETENTION = ENCHANTMENTS.register("retention", SlingshotEnchantment.RetentionEnchantment::new);
	public static final RegistryObject<Enchantment> HARDNESS = ENCHANTMENTS.register("hardness", SlingshotEnchantment.HardnessEnchantment::new);
	public static final RegistryObject<Enchantment> ELASTIC = ENCHANTMENTS.register("elastic", SlingshotEnchantment.ElasticEnchantment::new);
}
