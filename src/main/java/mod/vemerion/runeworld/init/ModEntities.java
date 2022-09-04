package mod.vemerion.runeworld.init;

import java.util.function.Supplier;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.BloodBatEntity;
import mod.vemerion.runeworld.entity.BloodGorillaEntity;
import mod.vemerion.runeworld.entity.BloodMonkeyEntity;
import mod.vemerion.runeworld.entity.SlingshotProjectileEntity;
import mod.vemerion.runeworld.entity.FireElementalEntity;
import mod.vemerion.runeworld.entity.FireElementalProjectileEntity;
import mod.vemerion.runeworld.entity.MosquitoEggsEntity;
import mod.vemerion.runeworld.entity.MosquitoEntity;
import mod.vemerion.runeworld.entity.TickEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModEntities {

	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			Main.MODID);

	public static final RegistryObject<EntityType<MosquitoEntity>> MOSQUITO = ENTITIES.register("mosquito",
			() -> EntityType.Builder.<MosquitoEntity>of(MosquitoEntity::new, MobCategory.MONSTER).sized(1, 1)
					.build(""));

	public static final Supplier<EntityType<? extends ThrowableItemProjectile>> MOSQUITO_EGGS = ENTITIES.register(
			"mosquito_eggs", () -> EntityType.Builder.<MosquitoEggsEntity>of(MosquitoEggsEntity::new, MobCategory.MISC)
					.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build(""));

	public static final RegistryObject<EntityType<BloodBatEntity>> BLOOD_BAT = ENTITIES.register("blood_bat",
			() -> EntityType.Builder.<BloodBatEntity>of(BloodBatEntity::new, MobCategory.MONSTER).sized(1f, 1.6f)
					.build(""));

	public static final RegistryObject<EntityType<BloodMonkeyEntity>> BLOOD_MONKEY = ENTITIES.register("blood_monkey",
			() -> EntityType.Builder.<BloodMonkeyEntity>of(BloodMonkeyEntity::new, MobCategory.MONSTER).sized(1f, 1.6f)
					.build(""));

	public static final Supplier<EntityType<SlingshotProjectileEntity>> SLINGSHOT_PROJECTILE = ENTITIES.register("slingshot_projectile",
			() -> EntityType.Builder.<SlingshotProjectileEntity>of(SlingshotProjectileEntity::new, MobCategory.MISC).sized(0.25F, 0.25F)
					.clientTrackingRange(4).updateInterval(10).build(""));

	public static final RegistryObject<EntityType<FireElementalEntity>> FIRE_ELEMENTAL = ENTITIES.register(
			"fire_elemental",
			() -> EntityType.Builder.<FireElementalEntity>of(FireElementalEntity::new, MobCategory.MONSTER).fireImmune()
					.sized(2, 7).clientTrackingRange(10).build(""));

	public static final RegistryObject<EntityType<FireElementalProjectileEntity>> FIRE_ELEMENTAL_PROJECTILE = ENTITIES
			.register("fire_elemental_projectile",
					() -> EntityType.Builder
							.<FireElementalProjectileEntity>of(FireElementalProjectileEntity::new, MobCategory.MISC)
							.sized(1, 1).clientTrackingRange(4).updateInterval(10).build(""));

	public static final RegistryObject<EntityType<BloodGorillaEntity>> BLOOD_GORILLA = ENTITIES
			.register("blood_gorilla", () -> EntityType.Builder
					.<BloodGorillaEntity>of(BloodGorillaEntity::new, MobCategory.MONSTER).sized(2.1f, 2.1f).build(""));
	
	public static final RegistryObject<EntityType<TickEntity>> TICK = ENTITIES
			.register("tick", () -> EntityType.Builder
					.<TickEntity>of(TickEntity::new, MobCategory.MONSTER).sized(0.6f, 0.6f).build(""));

	@SubscribeEvent
	public static void setupEntities(ParallelDispatchEvent event) {
		event.enqueueWork(() -> setEntitySpawns());
	}

	@SubscribeEvent
	public static void onRegisterEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(MOSQUITO.get(), MosquitoEntity.attributes().build());
		event.put(BLOOD_BAT.get(), BloodBatEntity.attributes().build());
		event.put(BLOOD_MONKEY.get(), BloodMonkeyEntity.attributes().build());
		event.put(FIRE_ELEMENTAL.get(), FireElementalEntity.attributes().build());
		event.put(BLOOD_GORILLA.get(), BloodGorillaEntity.attributes().build());
		event.put(TICK.get(), TickEntity.attributes().build());
	}

	private static void setEntitySpawns() {
		SpawnPlacements.register(MOSQUITO.get(), SpawnPlacements.Type.NO_RESTRICTIONS,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MosquitoEntity::canSpawn);

	}
}
