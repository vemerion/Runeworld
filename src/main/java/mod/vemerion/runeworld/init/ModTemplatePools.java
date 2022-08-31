package mod.vemerion.runeworld.init;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import mod.vemerion.runeworld.Main;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTemplatePools {
	
	public static final DeferredRegister<StructureTemplatePool> TEMPLATE_POOLS = DeferredRegister.create(Registry.TEMPLATE_POOL_REGISTRY, Main.MODID);

	public static final RegistryObject<StructureTemplatePool> BLOOD_MONKEY_TUNNELS_START = TEMPLATE_POOLS.register("blood_monkey_tunnels_start", () -> new StructureTemplatePool(new ResourceLocation(Main.MODID, "blood_monkey_tunnels_start"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/throne_room").toString()), 1)), StructureTemplatePool.Projection.RIGID));
	public static final RegistryObject<StructureTemplatePool> BLOOD_MONKEY_TUNNELS_ROOMS = TEMPLATE_POOLS.register("blood_monkey_tunnels/rooms", () -> new StructureTemplatePool(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms/statue_room").toString()), 10), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms/pillar_turn_room").toString()), 10), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms/blood_pool_room").toString()), 10), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms/tick_pit_room").toString()), 10), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms/hidden_room").toString()), 10), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms/t_junction_trap_room").toString()), 10), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms/fire_portal_room").toString()), 10), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms/bridge_room").toString()), 10), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms/all_around_connections_room").toString()), 10), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms/bottom_connection_room").toString()), 10), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms/top_connection_room").toString()), 10), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms/leech_corridor_room").toString()), 10), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms/head_corridor_room").toString()), 10), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms/runeforge_room").toString()), 10), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms/tick_spawner_room").toString()), 10), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/rooms/jungle_room").toString()), 10)), StructureTemplatePool.Projection.RIGID));
	public static final RegistryObject<StructureTemplatePool> BLOOD_MONKEY_TUNNELS_STATUES = TEMPLATE_POOLS.register("blood_monkey_tunnels/statues", () -> new StructureTemplatePool(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/statues"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/statues/banana_statue").toString()), 1), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/statues/monkey_statue").toString()), 1), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/statues/blood_fountain").toString()), 1), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/statues/crouching_monkey_statue").toString()), 1)), StructureTemplatePool.Projection.RIGID));
	public static final RegistryObject<StructureTemplatePool> BLOOD_MONKEY_TUNNELS_MOBS_MONKEY = TEMPLATE_POOLS.register("blood_monkey_tunnels/mobs/monkey", () -> new StructureTemplatePool(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/mobs/monkey"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/mobs/monkey").toString()), 1)), StructureTemplatePool.Projection.RIGID));
	public static final RegistryObject<StructureTemplatePool> BLOOD_MONKEY_TUNNELS_MOBS_MONKEY_IRON = TEMPLATE_POOLS.register("blood_monkey_tunnels/mobs/monkey_iron", () -> new StructureTemplatePool(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/mobs/monkey_iron"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/mobs/monkey_iron").toString()), 1)), StructureTemplatePool.Projection.RIGID));
	public static final RegistryObject<StructureTemplatePool> BLOOD_MONKEY_TUNNELS_MOBS_TICK = TEMPLATE_POOLS.register("blood_monkey_tunnels/mobs/tick", () -> new StructureTemplatePool(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/mobs/tick"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/mobs/tick").toString()), 1)), StructureTemplatePool.Projection.RIGID));
	public static final RegistryObject<StructureTemplatePool> BLOOD_MONKEY_TUNNELS_MOBS_MOSQUITO = TEMPLATE_POOLS.register("blood_monkey_tunnels/mobs/mosquito", () -> new StructureTemplatePool(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/mobs/mosquito"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/mobs/mosquito").toString()), 1)), StructureTemplatePool.Projection.RIGID));
	public static final RegistryObject<StructureTemplatePool> BLOOD_MONKEY_TUNNELS_RUNEFORGES = TEMPLATE_POOLS.register("blood_monkey_tunnels/runeforges", () -> new StructureTemplatePool(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/runeforges"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/runeforges/runeforge0").toString()), 1), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/runeforges/runeforge1").toString()), 1), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/runeforges/runeforge2").toString()), 1), Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/runeforges/runeforge3").toString()), 1)), StructureTemplatePool.Projection.RIGID));
}
