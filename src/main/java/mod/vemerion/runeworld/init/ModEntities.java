package mod.vemerion.runeworld.init;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.BloodBatEntity;
import mod.vemerion.runeworld.entity.BloodMonkeyEntity;
import mod.vemerion.runeworld.entity.BloodPebbleEntity;
import mod.vemerion.runeworld.entity.FireElementalEntity;
import mod.vemerion.runeworld.entity.FireElementalProjectileEntity;
import mod.vemerion.runeworld.entity.MosquitoEggsEntity;
import mod.vemerion.runeworld.entity.MosquitoEntity;
import mod.vemerion.runeworld.helpers.Helper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
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
	public static final EntityType<BloodPebbleEntity> BLOOD_PEBBLE = null;
	public static final EntityType<FireElementalEntity> FIRE_ELEMENTAL = null;
	public static final EntityType<FireElementalProjectileEntity> FIRE_ELEMENTAL_PROJECTILE = null;

	public static final Item MOSQUITO_SPAWN_EGG = null;
	public static final Item BLOOD_BAT_SPAWN_EGG = null;
	public static final Item BLOOD_MONKEY_SPAWN_EGG = null;

	private static EntityType<? extends Mob> mosquito;
	private static EntityType<? extends Mob> bloodBat;
	private static EntityType<? extends Mob> bloodMonkey;

	private static final List<SpawnEggItem> SPAWN_EGGS = new ArrayList<>();

	@SubscribeEvent
	public static void onRegisterEntity(RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().register(mosquito);
		event.getRegistry().register(bloodBat);
		event.getRegistry().register(bloodMonkey);

		EntityType<MosquitoEggsEntity> mosquitoEggs = EntityType.Builder
				.<MosquitoEggsEntity>of(MosquitoEggsEntity::new, MobCategory.MISC).sized(0.25F, 0.25F)
				.clientTrackingRange(4).updateInterval(10)
				.build(new ResourceLocation(Main.MODID, "mosquito_eggs").toString());

		EntityType<BloodPebbleEntity> bloodPebble = EntityType.Builder
				.<BloodPebbleEntity>of(BloodPebbleEntity::new, MobCategory.MISC).sized(0.25F, 0.25F)
				.clientTrackingRange(4).updateInterval(10).build(new ResourceLocation(Main.MODID, "blood_pebble").toString());

		EntityType<FireElementalEntity> fireElemental = EntityType.Builder
				.<FireElementalEntity>of(FireElementalEntity::new, MobCategory.MONSTER).fireImmune()
				.sized(2, 7).clientTrackingRange(10).build(new ResourceLocation(Main.MODID, "fire_elemental").toString());

		EntityType<FireElementalProjectileEntity> fireElementalProjectile = EntityType.Builder
				.<FireElementalProjectileEntity>of(FireElementalProjectileEntity::new, MobCategory.MISC)
				.sized(1, 1).clientTrackingRange(4).updateInterval(10)
				.build(new ResourceLocation(Main.MODID, "fire_elemental_projectile").toString());

		event.getRegistry().registerAll(Init.setup(mosquitoEggs, "mosquito_eggs"),
				Init.setup(bloodPebble, "blood_pebble"), Init.setup(fireElemental, "fire_elemental"),
				Init.setup(fireElementalProjectile, "fire_elemental_projectile"));
	}

	@SubscribeEvent
	public static void onRegisterSpawnEggs(RegistryEvent.Register<Item> event) {
		mosquito = Init
				.setup(EntityType.Builder.<MosquitoEntity>of(MosquitoEntity::new, MobCategory.MONSTER)
						.sized(1, 1).build(new ResourceLocation(Main.MODID, "mosquito").toString()), "mosquito");
		bloodBat = Init
				.setup(EntityType.Builder.<BloodBatEntity>of(BloodBatEntity::new, MobCategory.MONSTER)
						.sized(1f, 1.6f).build(new ResourceLocation(Main.MODID, "blood_bat").toString()), "blood_bat");
		bloodMonkey = Init.setup(
				EntityType.Builder.<BloodMonkeyEntity>of(BloodMonkeyEntity::new, MobCategory.MONSTER)
						.sized(1f, 1.6f).build(new ResourceLocation(Main.MODID, "blood_monkey").toString()),
				"blood_monkey");

		Item mosquitoSpawnEgg = createSpawnEgg(mosquito, Helper.color(100, 50, 0, 255), Helper.color(255, 0, 0, 255));
		Item bloodBatSpawnEgg = createSpawnEgg(bloodBat, Helper.color(40, 40, 40, 255), Helper.color(255, 0, 0, 255));
		Item bloodMonkeySpawnEgg = createSpawnEgg(bloodMonkey, Helper.color(70, 30, 10, 255),
				Helper.color(215, 70, 70, 255));

		event.getRegistry().registerAll(mosquitoSpawnEgg, bloodBatSpawnEgg, bloodMonkeySpawnEgg);
	}

	@SubscribeEvent
	public static void setupEntities(ParallelDispatchEvent event) {
		event.enqueueWork(() -> setEntitySpawns());
	}
	
	@SubscribeEvent
	public static void onRegisterEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(MOSQUITO, MosquitoEntity.attributes().build());
		event.put(BLOOD_BAT, BloodBatEntity.attributes().build());
		event.put(BLOOD_MONKEY, BloodMonkeyEntity.attributes().build());
		event.put(FIRE_ELEMENTAL, FireElementalEntity.attributes().build());
	}

	private static void setEntitySpawns() {
		SpawnPlacements.register(MOSQUITO, SpawnPlacements.Type.NO_RESTRICTIONS,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MosquitoEntity::canSpawn);

	}

	private static SpawnEggItem createSpawnEgg(EntityType<? extends Mob> type, int primaryColor, int secondaryColor) {
		SpawnEggItem egg = Init.<SpawnEggItem>setup(
				new SpawnEggItem(type, primaryColor, secondaryColor, (new Item.Properties()).tab(CreativeModeTab.TAB_SEARCH)),
				type.getRegistryName().getPath() + "_spawn_egg");
		SPAWN_EGGS.add(egg);
		return egg;
	}

	public static ImmutableList<SpawnEggItem> getSpawnEggs() {
		return ImmutableList.copyOf(SPAWN_EGGS);
	}
}
