package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.BloodBatEntity;
import mod.vemerion.runeworld.entity.BloodGorillaEntity;
import mod.vemerion.runeworld.entity.BloodMonkeyEntity;
import mod.vemerion.runeworld.entity.BloodPebbleEntity;
import mod.vemerion.runeworld.entity.FireElementalEntity;
import mod.vemerion.runeworld.entity.FireElementalProjectileEntity;
import mod.vemerion.runeworld.entity.MosquitoEggsEntity;
import mod.vemerion.runeworld.entity.MosquitoEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
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
	public static final EntityType<BloodGorillaEntity> BLOOD_GORILLA = null;

	@SubscribeEvent
	public static void onRegisterEntity(RegistryEvent.Register<EntityType<?>> event) {
		var mosquito = Init
				.setup(EntityType.Builder.<MosquitoEntity>of(MosquitoEntity::new, MobCategory.MONSTER)
						.sized(1, 1).build(new ResourceLocation(Main.MODID, "mosquito").toString()), "mosquito");
		
		var bloodBat = Init
				.setup(EntityType.Builder.<BloodBatEntity>of(BloodBatEntity::new, MobCategory.MONSTER)
						.sized(1f, 1.6f).build(new ResourceLocation(Main.MODID, "blood_bat").toString()), "blood_bat");
		
		var bloodMonkey = Init.setup(
				EntityType.Builder.<BloodMonkeyEntity>of(BloodMonkeyEntity::new, MobCategory.MONSTER)
						.sized(1f, 1.6f).build(new ResourceLocation(Main.MODID, "blood_monkey").toString()),
				"blood_monkey");

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
		
		var bloodGorilla = EntityType.Builder.<BloodGorillaEntity>of(BloodGorillaEntity::new, MobCategory.MONSTER)
				.sized(2.1f, 2.1f).build("");

		event.getRegistry().registerAll(Init.setup(mosquitoEggs, "mosquito_eggs"),
				Init.setup(bloodPebble, "blood_pebble"), Init.setup(fireElemental, "fire_elemental"),
				Init.setup(fireElementalProjectile, "fire_elemental_projectile"), Init.setup(bloodGorilla, "blood_gorilla"), mosquito, bloodBat, bloodMonkey);
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
		event.put(BLOOD_GORILLA, BloodGorillaEntity.attributes().build());
	}

	private static void setEntitySpawns() {
		SpawnPlacements.register(MOSQUITO, SpawnPlacements.Type.NO_RESTRICTIONS,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MosquitoEntity::canSpawn);

	}
}
