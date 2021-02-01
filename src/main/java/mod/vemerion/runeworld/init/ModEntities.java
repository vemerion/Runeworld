package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.MosquitoEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModEntities {
	
	public static final EntityType<MosquitoEntity> MOSQUITO = null;

	@SubscribeEvent
	public static void onRegisterEntity(RegistryEvent.Register<EntityType<?>> event) {
		EntityType<?> mosquito = EntityType.Builder
				.<MosquitoEntity>create(MosquitoEntity::new, EntityClassification.CREATURE).size(1, 1)
				.build(new ResourceLocation(Main.MODID, "mosquito").toString());
		event.getRegistry().register(Init.setup(mosquito, "mosquito"));
	}
	
	@SubscribeEvent
	public static void registerAttributes(ParallelDispatchEvent event) {
		event.enqueueWork(() -> setEntityAttributes());
	}

	private static void setEntityAttributes() {
		GlobalEntityTypeAttributes.put(MOSQUITO, MosquitoEntity.attributes().create());
	}
}
