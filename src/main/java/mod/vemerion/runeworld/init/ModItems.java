package mod.vemerion.runeworld.init;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.item.BloodBatToothItem;
import mod.vemerion.runeworld.item.BloodBucketItem;
import mod.vemerion.runeworld.item.BloodCrownItem;
import mod.vemerion.runeworld.item.BloodCrystalliteItem;
import mod.vemerion.runeworld.item.BloodFlowerItem;
import mod.vemerion.runeworld.item.BloodPuddingItem;
import mod.vemerion.runeworld.item.DislocatorItem;
import mod.vemerion.runeworld.item.FireRootItem;
import mod.vemerion.runeworld.item.GuideItem;
import mod.vemerion.runeworld.item.ThrowableItem;
import net.minecraft.core.NonNullList;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModItems {
	
		public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
		
		public static final RegistryObject<Item> MOSQUITO_SPAWN_EGG = ITEMS.register("mosquito_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.MOSQUITO, Helper.color(100, 50, 0, 255), Helper.color(255, 0, 0, 255), (new Item.Properties()).tab(CreativeModeTab.TAB_SEARCH)));
		public static final RegistryObject<Item> BLOOD_BAT_SPAWN_EGG = ITEMS.register("blood_bat_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.BLOOD_BAT, Helper.color(40, 40, 40, 255), Helper.color(255, 0, 0, 255), (new Item.Properties()).tab(CreativeModeTab.TAB_SEARCH)));
		public static final RegistryObject<Item> BLOOD_MONKEY_SPAWN_EGG = ITEMS.register("blood_monkey_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.BLOOD_MONKEY, Helper.color(70, 30, 10, 255), Helper.color(215, 70, 70, 255), (new Item.Properties()).tab(CreativeModeTab.TAB_SEARCH)));
		public static final RegistryObject<Item> BLOOD_GORILLA_SPAWN_EGG = ITEMS.register("blood_gorilla_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.BLOOD_GORILLA, 0x864141, 0x2a2a2a, (new Item.Properties()).tab(CreativeModeTab.TAB_SEARCH)));

		public static final RegistryObject<Item> BLOOD_BUCKET = ITEMS.register("blood_bucket", BloodBucketItem::new);
		public static final RegistryObject<Item> BLOOD_PUDDING = ITEMS.register("blood_pudding", BloodPuddingItem::new);
		public static final RegistryObject<Item> BLOOD_FLOWER = ITEMS.register("blood_flower", BloodFlowerItem::new);
		public static final RegistryObject<Item> MOSQUITO_EGGS = ITEMS.register("mosquito_eggs", () -> new ThrowableItem(ModEntities.MOSQUITO_EGGS, 0.5));
		public static final RegistryObject<Item> BLOOD_BAT_TOOTH = ITEMS.register("blood_bat_tooth", BloodBatToothItem::new);
		public static final RegistryObject<Item> GUIDE = ITEMS.register("guide", () -> new GuideItem(new Item.Properties().tab(CreativeModeTab.TAB_SEARCH)));
		public static final RegistryObject<Item> BLOOD_CRYSTALLITE = ITEMS.register("blood_crystallite", BloodCrystalliteItem::new);
		public static final RegistryObject<DislocatorItem> BLOOD_DISLOCATOR = ITEMS.register("blood_dislocator", () -> new DislocatorItem(new Item.Properties().tab(CreativeModeTab.TAB_SEARCH), ModDimensions.BLOOD));
		public static final RegistryObject<Item> BLOOD_PEBBLE = ITEMS.register("blood_pebble", () -> new ThrowableItem(ModEntities.BLOOD_PEBBLE, 0.75));
		public static final RegistryObject<Item> GRILLED_BLOOD_LEECH = ITEMS.register("grilled_blood_leech", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_SEARCH)
				.food(new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).meat().build())));
		public static final RegistryObject<Item> FIRE_HEART = ITEMS.register("fire_heart", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_SEARCH).rarity(Rarity.UNCOMMON).fireResistant()));
		public static final RegistryObject<DislocatorItem> FIRE_DISLOCATOR = ITEMS.register("fire_dislocator", () -> new DislocatorItem(new Item.Properties().tab(CreativeModeTab.TAB_SEARCH).durability(16).fireResistant(),
				ModDimensions.FIRE));
		public static final RegistryObject<Item> FIRE_ROOT = ITEMS.register("fire_root", FireRootItem::new);
		public static final RegistryObject<Item> BLOOD_CROWN = ITEMS.register("blood_crown", BloodCrownItem::new);

	static final CreativeModeTab ITEM_GROUP = new RuneworldItemGroup();

	private static Set<Pair<Block, Item.Properties>> withItem = new HashSet<>();

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event) {
		registerBlockItems(event.getRegistry());
	}

	private static void registerBlockItems(IForgeRegistry<Item> registry) {
		for (Pair<Block, Item.Properties> p : withItem) {
			Block b = p.getLeft();
			Item.Properties properties = p.getRight().tab(CreativeModeTab.TAB_SEARCH);
			registry.register(Init.setup(new BlockItem(b, properties), b.getRegistryName()));
		}
	}

	public static void addBlockWithItem(Block block) {
		withItem.add(Pair.of(block, new Item.Properties()));
	}

	public static void addBlockWithItem(Block block, Item.Properties properties) {
		withItem.add(Pair.of(block, properties));
	}

	private static class RuneworldItemGroup extends CreativeModeTab {

		public RuneworldItemGroup() {
			super(Main.MODID);
		}

		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModBlocks.BLOOD_CRYSTAL.get());
		}

		@Override
		public void fillItemList(NonNullList<ItemStack> items) {
			for (Item item : ForgeRegistries.ITEMS) {
				if (item != null)
					if (item.getRegistryName().getNamespace().equals(Main.MODID)) {
						item.fillItemCategory(CreativeModeTab.TAB_SEARCH, items);
					}
			}
		}
	}
}
