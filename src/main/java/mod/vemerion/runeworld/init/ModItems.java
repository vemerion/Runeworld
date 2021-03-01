package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.item.BloodBatToothItem;
import mod.vemerion.runeworld.item.BloodBucketItem;
import mod.vemerion.runeworld.item.BloodCrystalliteItem;
import mod.vemerion.runeworld.item.BloodFlowerItem;
import mod.vemerion.runeworld.item.BloodPuddingItem;
import mod.vemerion.runeworld.item.GuideItem;
import mod.vemerion.runeworld.item.MosquitoEggsItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;
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

	static final ItemGroup ITEM_GROUP = new RuneworldItemGroup();

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event) {
		Item bloodBucket = Init.setup(new BloodBucketItem(), "blood_bucket");
		Item bloodPudding = Init.setup(new BloodPuddingItem(), "blood_pudding");
		Item bloodFlower = Init.setup(new BloodFlowerItem(), "blood_flower");
		Item bloodPillarLarge = Init.setup(
				new BlockItem(ModBlocks.BLOOD_PILLAR_LARGE, new Item.Properties().group(ItemGroup.SEARCH)),
				"blood_pillar_large");
		Item bloodPillarMedium = Init.setup(
				new BlockItem(ModBlocks.BLOOD_PILLAR_MEDIUM, new Item.Properties().group(ItemGroup.SEARCH)),
				"blood_pillar_medium");
		Item bloodPillarSmall = Init.setup(
				new BlockItem(ModBlocks.BLOOD_PILLAR_SMALL, new Item.Properties().group(ItemGroup.SEARCH)),
				"blood_pillar_small");
		Item bloodRock = Init.setup(new BlockItem(ModBlocks.BLOOD_ROCK, new Item.Properties().group(ItemGroup.SEARCH)),
				"blood_rock");
		Item bloodMoss = Init.setup(new BlockItem(ModBlocks.BLOOD_MOSS, new Item.Properties().group(ItemGroup.SEARCH)),
				"blood_moss");
		Item bloodCrystal = Init.setup(
				new BlockItem(ModBlocks.BLOOD_CRYSTAL, new Item.Properties().group(ItemGroup.SEARCH)), "blood_crystal");
		Item mosquitoEggs = Init.setup(new MosquitoEggsItem(), "mosquito_eggs");
		Item bloodBatTooth = Init.setup(new BloodBatToothItem(), "blood_bat_tooth");
		Item bloodLeech = Init.setup(
				new BlockItem(ModBlocks.BLOOD_LEECH, new Item.Properties().group(ItemGroup.SEARCH)), "blood_leech");
		Item guide = Init.setup(new GuideItem(new Item.Properties().group(ItemGroup.SEARCH)), "guide");
		Item bloodCrystallite = Init.setup(new BloodCrystalliteItem(), "blood_crystallite");

		event.getRegistry().registerAll(bloodBucket, bloodPudding, bloodFlower, bloodPillarLarge, bloodPillarMedium,
				bloodPillarSmall, bloodRock, bloodMoss, bloodCrystal, mosquitoEggs, bloodBatTooth, bloodLeech, guide,
				bloodCrystallite);
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
