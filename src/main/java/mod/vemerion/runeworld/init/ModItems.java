package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.item.BloodBucketItem;
import mod.vemerion.runeworld.item.BloodFlowerItem;
import mod.vemerion.runeworld.item.BloodPuddingItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
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

		event.getRegistry().registerAll(bloodBucket, bloodPudding, bloodFlower, bloodPillarLarge, bloodPillarMedium,
				bloodPillarSmall);
	}
}