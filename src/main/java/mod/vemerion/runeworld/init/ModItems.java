package mod.vemerion.runeworld.init;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.helpers.Helper;
import mod.vemerion.runeworld.item.BloodBatToothItem;
import mod.vemerion.runeworld.item.BloodBucketItem;
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
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryObject;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModItems {
	
	public static class Deferred {
		public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
		
		public static final RegistryObject<Item> MOSQUITO_SPAWN_EGG = ITEMS.register("mosquito_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.MOSQUITO, Helper.color(100, 50, 0, 255), Helper.color(255, 0, 0, 255), (new Item.Properties()).tab(CreativeModeTab.TAB_SEARCH)));
		public static final RegistryObject<Item> BLOOD_BAT_SPAWN_EGG = ITEMS.register("blood_bat_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.BLOOD_BAT, Helper.color(40, 40, 40, 255), Helper.color(255, 0, 0, 255), (new Item.Properties()).tab(CreativeModeTab.TAB_SEARCH)));
		public static final RegistryObject<Item> BLOOD_MONKEY_SPAWN_EGG = ITEMS.register("blood_monkey_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.BLOOD_MONKEY, Helper.color(70, 30, 10, 255), Helper.color(215, 70, 70, 255), (new Item.Properties()).tab(CreativeModeTab.TAB_SEARCH)));
	}

	public static final Item BLOOD_BUCKET = null;
	public static final Item BLOOD_PUDDING = null;
	public static final Item BLOOD_FLOWER = null;
	public static final Item BLOOD_PILLAR_LARGE = null;
	public static final Item BLOOD_PILLAR_MEDIUM = null;
	public static final Item BLOOD_PILLAR_SMALL = null;
	public static final Item BLOOD_ROCK = null;
	public static final Item BLOOD_MOSS = null;
	public static final Item BLOOD_CRYSTAL = null;
	public static final Item MOSQUITO_EGGS = null;
	public static final Item BLOOD_BAT_TOOTH = null;
	public static final Item BLOOD_LEECH = null;
	public static final Item GUIDE = null;
	public static final Item BLOOD_CRYSTALLITE = null;
	public static final DislocatorItem BLOOD_DISLOCATOR = null;
	public static final Item BLOOD_PEBBLE = null;
	public static final Item GRILLED_BLOOD_LEECH = null;
	public static final Item FIRE_HEART = null;
	public static final DislocatorItem FIRE_DISLOCATOR = null;
	public static final Item FIRE_ROOT = null;

	static final CreativeModeTab ITEM_GROUP = new RuneworldItemGroup();

	private static List<Pair<Block, Item.Properties>> withItem = new ArrayList<>();

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		Item bloodBucket = Init.setup(new BloodBucketItem(), "blood_bucket");
		Item bloodPudding = Init.setup(new BloodPuddingItem(), "blood_pudding");
		Item bloodFlower = Init.setup(new BloodFlowerItem(), "blood_flower");
		Item mosquitoEggs = Init.setup(new ThrowableItem(ModEntities.MOSQUITO_EGGS, 0.5), "mosquito_eggs");
		Item bloodBatTooth = Init.setup(new BloodBatToothItem(), "blood_bat_tooth");
		Item guide = Init.setup(new GuideItem(new Item.Properties().tab(CreativeModeTab.TAB_SEARCH)), "guide");
		Item bloodCrystallite = Init.setup(new BloodCrystalliteItem(), "blood_crystallite");
		Item bloodDislocator = Init.setup(
				new DislocatorItem(new Item.Properties().tab(CreativeModeTab.TAB_SEARCH), ModDimensions.BLOOD),
				"blood_dislocator");
		Item bloodPebble = Init.setup(new ThrowableItem(ModEntities.BLOOD_PEBBLE, 0.75), "blood_pebble");
		Item grilledBloodLeech = Init.setup(new Item(new Item.Properties().tab(CreativeModeTab.TAB_SEARCH)
				.food(new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).meat().build())), "grilled_blood_leech");
		Item fireHeart = Init.setup(
				new Item(new Item.Properties().tab(CreativeModeTab.TAB_SEARCH).rarity(Rarity.UNCOMMON).fireResistant()),
				"fire_heart");
		Item fireDislocator = Init
				.setup(new DislocatorItem(new Item.Properties().tab(CreativeModeTab.TAB_SEARCH).durability(16).fireResistant(),
						ModDimensions.FIRE), "fire_dislocator");
		Item fireRoot = Init.setup(new FireRootItem(), "fire_root");

		registry.registerAll(bloodBucket, bloodPudding, bloodFlower, mosquitoEggs, bloodBatTooth, guide,
				bloodCrystallite, bloodDislocator, bloodPebble, grilledBloodLeech, fireHeart, fireDislocator, fireRoot);

		registerBlockItems(registry);
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
			return new ItemStack(BLOOD_CRYSTAL);
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
