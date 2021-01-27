package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.item.BloodBucketItem;
import mod.vemerion.runeworld.item.BloodPuddingItem;
import net.minecraft.item.Item;
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

	@SubscribeEvent
	public static void onRegisterItem(RegistryEvent.Register<Item> event) {
		Item bloodBucket = Init.setup(new BloodBucketItem(), "blood_bucket");

		Item bloodPudding = Init.setup(new BloodPuddingItem(), "blood_pudding");

		event.getRegistry().registerAll(bloodBucket, bloodPudding);
	}
}
