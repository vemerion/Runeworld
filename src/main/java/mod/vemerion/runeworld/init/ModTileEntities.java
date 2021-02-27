package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.tileentity.BloodLeechTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModTileEntities {

	public static final TileEntityType<BloodLeechTileEntity> BLOOD_LEECH = null;

	@SubscribeEvent
	public static void onRegisterTileEntity(RegistryEvent.Register<TileEntityType<?>> event) {
		TileEntityType<BloodLeechTileEntity> bloodLeech = TileEntityType.Builder
				.create(BloodLeechTileEntity::new, ModBlocks.BLOOD_LEECH).build(null);

		event.getRegistry().register(Init.setup(bloodLeech, "blood_leech"));
	}
}
