package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.lootmodifier.FromLootTableLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {

	public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister
			.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, Main.MODID);

	public static final RegistryObject<GlobalLootModifierSerializer<FromLootTableLootModifier>> FROM_LOOT_TABLE = LOOT_MODIFIERS
			.register("from_loot_table", FromLootTableLootModifier.Serializer::new);
}
