package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStructureSets {
	
	public static final DeferredRegister<StructureSet> STRUCTURE_SETS = DeferredRegister.create(Registry.STRUCTURE_SET_REGISTRY, Main.MODID);

	public static final RegistryObject<StructureSet> BLOOD_BAT_LAIR = STRUCTURE_SETS.register("blood_bat_lair", () -> new StructureSet(ModConfiguredStructures.BLOOD_BAT_LAIR.getHolder().get(), new RandomSpreadStructurePlacement(48, 8, RandomSpreadType.LINEAR, 1079509817)));
	public static final RegistryObject<StructureSet> FIRE_RITUAL = STRUCTURE_SETS.register("fire_ritual", () -> new StructureSet(ModConfiguredStructures.FIRE_RITUAL.getHolder().get(), new RandomSpreadStructurePlacement(64, 8, RandomSpreadType.LINEAR, 204693832)));
	public static final RegistryObject<StructureSet> BLOOD_GORILLA_THRONE = STRUCTURE_SETS.register("blood_gorilla_throne", () -> new StructureSet(ModConfiguredStructures.BLOOD_GORILLA_THRONE.getHolder().get(), new RandomSpreadStructurePlacement(64, 8, RandomSpreadType.LINEAR, 843757620)));
	public static final RegistryObject<StructureSet> BLOOD_MONKEY_TUNNELS = STRUCTURE_SETS.register("blood_monkey_tunnels", () -> new StructureSet(ModConfiguredStructures.BLOOD_MONKEY_TUNNELS.getHolder().get(), new RandomSpreadStructurePlacement(64, 8, RandomSpreadType.LINEAR, 2732226)));
}
