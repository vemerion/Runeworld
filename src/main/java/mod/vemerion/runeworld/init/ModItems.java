package mod.vemerion.runeworld.init;

import java.util.ArrayList;
import java.util.List;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.item.BloodBatToothItem;
import mod.vemerion.runeworld.item.BloodBucketItem;
import mod.vemerion.runeworld.item.BloodCrystalliteItem;
import mod.vemerion.runeworld.item.DislocatorItem;
import mod.vemerion.runeworld.item.BloodFlowerItem;
import mod.vemerion.runeworld.item.BloodPuddingItem;
import mod.vemerion.runeworld.item.GuideItem;
import mod.vemerion.runeworld.item.ThrowableItem;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModItems {

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

	static final ItemGroup ITEM_GROUP = new RuneworldItemGroup();

	private static List<Block> withItem = new ArrayList<>();

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		Item bloodBucket = Init.setup(new BloodBucketItem(), "blood_bucket");
		Item bloodPudding = Init.setup(new BloodPuddingItem(), "blood_pudding");
		Item bloodFlower = Init.setup(new BloodFlowerItem(), "blood_flower");
		Item mosquitoEggs = Init.setup(new ThrowableItem(() -> ModEntities.MOSQUITO_EGGS, 0.5), "mosquito_eggs");
		Item bloodBatTooth = Init.setup(new BloodBatToothItem(), "blood_bat_tooth");
		Item guide = Init.setup(new GuideItem(new Item.Properties().group(ItemGroup.SEARCH)), "guide");
		Item bloodCrystallite = Init.setup(new BloodCrystalliteItem(), "blood_crystallite");
		Item bloodDislocator = Init.setup(
				new DislocatorItem(new Item.Properties().group(ItemGroup.SEARCH), ModDimensions.BLOOD),
				"blood_dislocator");
		Item bloodPebble = Init.setup(new ThrowableItem(() -> ModEntities.BLOOD_PEBBLE, 0.75), "blood_pebble");
		Item grilledBloodLeech = Init.setup(new Item(new Item.Properties().group(ItemGroup.SEARCH)
				.food(new Food.Builder().hunger(4).saturation(0.4f).meat().build())), "grilled_blood_leech");
		Item fireHeart = Init.setup(
				new Item(new Item.Properties().group(ItemGroup.SEARCH).rarity(Rarity.UNCOMMON).isImmuneToFire()),
				"fire_heart");
		Item fireDislocator = Init
				.setup(new DislocatorItem(new Item.Properties().group(ItemGroup.SEARCH).maxDamage(16).isImmuneToFire(),
						ModDimensions.FIRE), "fire_dislocator");

		registry.registerAll(bloodBucket, bloodPudding, bloodFlower, mosquitoEggs, bloodBatTooth, guide,
				bloodCrystallite, bloodDislocator, bloodPebble, grilledBloodLeech, fireHeart, fireDislocator);

		registerBlockItems(registry);
	}

	private static void registerBlockItems(IForgeRegistry<Item> registry) {
		Item.Properties properties = new Item.Properties().group(ItemGroup.SEARCH);
		for (Block b : withItem)
			registry.register(Init.setup(new BlockItem(b, properties), b.getRegistryName()));
	}

	public static void addBlockWithItem(Block block) {
		withItem.add(block);
	}

	private static class RuneworldItemGroup extends ItemGroup {

		public RuneworldItemGroup() {
			super(Main.MODID);
		}

		@Override
		public ItemStack createIcon() {
			return new ItemStack(BLOOD_CRYSTAL);
		}

		@Override
		public void fill(NonNullList<ItemStack> items) {
			for (Item item : ForgeRegistries.ITEMS) {
				if (item != null)
					if (item.getRegistryName().getNamespace().equals(Main.MODID)) {
						item.fillItemGroup(ItemGroup.SEARCH, items);
					}
			}
		}
	}
}
