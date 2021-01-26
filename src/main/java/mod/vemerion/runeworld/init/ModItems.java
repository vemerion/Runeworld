package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import net.minecraft.item.BucketItem;
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

	@SubscribeEvent
	public static void onRegisterFluid(RegistryEvent.Register<Item> event) {
		event.getRegistry()
				.register(Init.setup(new BucketItem(() -> ModFluids.BLOOD,
						new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ItemGroup.SEARCH)),
						"blood_bucket"));
	}
}
