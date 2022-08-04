package mod.vemerion.runeworld.init;

import mod.vemerion.runesword.Main;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class ModStructureSets {
	
	public static class Keys {
	  public static final ResourceKey<StructureSet> BLOOD_BAT_LAIR = ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation(Main.MODID, "blood_bat_lair"));
	  public static final ResourceKey<StructureSet> FIRE_RITUAL = ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation(Main.MODID, "fire_ritual"));
	}
	
	public static Holder<StructureSet> BLOOD_BAT_LAIR;
	public static Holder<StructureSet> FIRE_RITUAL;
	
	public static void register() {
		var reg = BuiltinRegistries.STRUCTURE_SETS;

		BLOOD_BAT_LAIR = BuiltinRegistries.register(reg, Keys.BLOOD_BAT_LAIR, new StructureSet(ModConfiguredStructures.BLOOD_BAT_LAIR, new RandomSpreadStructurePlacement(48, 8, RandomSpreadType.LINEAR, 1079509817)));
		FIRE_RITUAL = BuiltinRegistries.register(reg, Keys.FIRE_RITUAL, new StructureSet(ModConfiguredStructures.FIRE_RITUAL, new RandomSpreadStructurePlacement(64, 8, RandomSpreadType.LINEAR, 204693832)));
	}
}
