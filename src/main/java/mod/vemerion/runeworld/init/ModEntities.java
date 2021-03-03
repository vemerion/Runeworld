package mod.vemerion.runeworld.init;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.BloodBatEntity;
import mod.vemerion.runeworld.entity.BloodMonkeyEntity;
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
	public static final EntityType<BloodBatEntity> BLOOD_BAT = null;
	public static final EntityType<BloodMonkeyEntity> BLOOD_MONKEY = null;

	public static final Item MOSQUITO_SPAWN_EGG = null;
	public static final Item BLOOD_BAT_SPAWN_EGG = null;
	public static final Item BLOOD_MONKEY_SPAWN_EGG = null;

	private static EntityType<?> mosquito;
	private static EntityType<?> bloodBat;
	private static EntityType<?> bloodMonkey;

	private static final List<SpawnEggItem> SPAWN_EGGS = new ArrayList<>();

	@SubscribeEvent
	public static void onRegisterEntity(RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().register(mosquito);
		event.getRegistry().register(bloodBat);
		event.getRegistry().register(bloodMonkey);

		EntityType<MosquitoEggsEntity> mosquitoEggs = EntityType.Builder
				.<MosquitoEggsEntity>create(MosquitoEggsEntity::new, EntityClassification.MISC).size(0.25F, 0.25F)
				.trackingRange(4).func_233608_b_(10)
				.build(new ResourceLocation(Main.MODID, "mosquito_eggs").toString());

		event.getRegistry().register(Init.setup(mosquitoEggs, "mosquito_eggs"));
	}

	@SubscribeEvent
	public static void onRegisterSpawnEggs(RegistryEvent.Register<Item> event) {
		mosquito = Init
				.setup(EntityType.Builder.<MosquitoEntity>create(MosquitoEntity::new, EntityClassification.MONSTER)
						.size(1, 1).build(new ResourceLocation(Main.MODID, "mosquito").toString()), "mosquito");
		bloodBat = Init
				.setup(EntityType.Builder.<BloodBatEntity>create(BloodBatEntity::new, EntityClassification.MONSTER)
						.size(1f, 1.6f).build(new ResourceLocation(Main.MODID, "blood_bat").toString()), "blood_bat");
		bloodMonkey = Init.setup(
				EntityType.Builder.<BloodMonkeyEntity>create(BloodMonkeyEntity::new, EntityClassification.MONSTER)
						.size(1f, 1.6f).build(new ResourceLocation(Main.MODID, "blood_monkey").toString()),
				"blood_monkey");

		Item mosquitoSpawnEgg = createSpawnEgg(mosquito, Helper.color(100, 50, 0, 255), Helper.color(255, 0, 0, 255));
		Item bloodBatSpawnEgg = createSpawnEgg(bloodBat, Helper.color(40, 40, 40, 255), Helper.color(255, 0, 0, 255));
		Item bloodMonkeySpawnEgg = createSpawnEgg(bloodMonkey, Helper.color(255, 255, 255, 255),
				Helper.color(255, 0, 0, 255));

		event.getRegistry().registerAll(mosquitoSpawnEgg, bloodBatSpawnEgg, bloodMonkeySpawnEgg);
	}

	@SubscribeEvent
	public static void setupEntities(ParallelDispatchEvent event) {
		event.enqueueWork(() -> setEntityAttributes());
		event.enqueueWork(() -> setEntitySpawns());
	}

	private static void setEntityAttributes() {
		GlobalEntityTypeAttributes.put(MOSQUITO, MosquitoEntity.attributes().create());
		GlobalEntityTypeAttributes.put(BLOOD_BAT, BloodBatEntity.attributes().create());
		GlobalEntityTypeAttributes.put(BLOOD_MONKEY, BloodMonkeyEntity.attributes().create());
	}

	private static void setEntitySpawns() {
		EntitySpawnPlacementRegistry.register(MOSQUITO, EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS,
				Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MosquitoEntity::canSpawn);

	}

	private static SpawnEggItem createSpawnEgg(EntityType<?> type, int primaryColor, int secondaryColor) {
		SpawnEggItem egg = Init.<SpawnEggItem>setup(
				new SpawnEggItem(type, primaryColor, secondaryColor, (new Item.Properties()).group(ItemGroup.SEARCH)),
				type.getRegistryName().getPath() + "_spawn_egg");
		SPAWN_EGGS.add(egg);
		return egg;
	}

	public static ImmutableList<SpawnEggItem> getSpawnEggs() {
		return ImmutableList.copyOf(SPAWN_EGGS);
	}
}
