package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.blockentity.BloodLeechBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModBlockEntities {

	public static final BlockEntityType<BloodLeechBlockEntity> BLOOD_LEECH = null;

	@SubscribeEvent
	public static void onRegisterTileEntity(RegistryEvent.Register<BlockEntityType<?>> event) {
		BlockEntityType<BloodLeechBlockEntity> bloodLeech = BlockEntityType.Builder
				.of(BloodLeechBlockEntity::new, ModBlocks.BLOOD_LEECH).build(null);

		event.getRegistry().register(Init.setup(bloodLeech, "blood_leech"));
	}
}
