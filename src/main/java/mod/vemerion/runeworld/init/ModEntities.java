package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.MosquitoEggsEntity;
import mod.vemerion.runeworld.entity.MosquitoEntity;
import mod.vemerion.runeworld.helpers.Helper;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
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
	public static final EntityType<MosquitoEggsEntity> MOSQUITO_EGGS = null;

	public static final Item MOSQUITO_SPAWN_EGG = null;

	private static EntityType<?> mosquito;

	@SubscribeEvent
	public static void onRegisterEntity(RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().register(Init.setup(mosquito, "mosquito"));

		EntityType<MosquitoEggsEntity> mosquitoEggs = EntityType.Builder
				.<MosquitoEggsEntity>create(MosquitoEggsEntity::new, EntityClassification.MISC).size(0.25F, 0.25F)
				.trackingRange(4).func_233608_b_(10)
				.build(new ResourceLocation(Main.MODID, "mosquito_eggs").toString());
		
		event.getRegistry().register(Init.setup(mosquitoEggs, "mosquito_eggs"));
	}

	@SubscribeEvent
	public static void onRegisterSpawnEggs(RegistryEvent.Register<Item> event) {
		mosquito = EntityType.Builder.<MosquitoEntity>create(MosquitoEntity::new, EntityClassification.MONSTER)
				.size(1, 1).build(new ResourceLocation(Main.MODID, "mosquito").toString());

		Item mosquitoSpawnEgg = new SpawnEggItem(mosquito, Helper.color(100, 50, 0, 255), Helper.color(255, 0, 0, 255),
				(new Item.Properties()).group(ItemGroup.SEARCH));
		event.getRegistry().register(Init.setup(mosquitoSpawnEgg, "mosquito_spawn_egg"));
	}

	@SubscribeEvent
	public static void setupEntities(ParallelDispatchEvent event) {
		event.enqueueWork(() -> setEntityAttributes());
		event.enqueueWork(() -> setEntitySpawns());
	}

	private static void setEntityAttributes() {
		GlobalEntityTypeAttributes.put(MOSQUITO, MosquitoEntity.attributes().create());
	}

	private static void setEntitySpawns() {
		EntitySpawnPlacementRegistry.register(MOSQUITO, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MosquitoEntity::canSpawn);

	}
}
