package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
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
		Item bloodBucket = Init.setup(
				new BucketItem(() -> ModFluids.BLOOD,
						new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ItemGroup.SEARCH)),
				"blood_bucket");

		Item bloodPudding = Init.setup(new Item(new Item.Properties().group(ItemGroup.SEARCH)
				.food(new Food.Builder().hunger(3).saturation(0.3F).meat().build())), "blood_pudding");

		event.getRegistry().registerAll(bloodBucket, bloodPudding);
	}
}
