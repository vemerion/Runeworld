package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.structure.BloodBatLairStructure;
import mod.vemerion.runeworld.structure.BloodGorillaThroneStructure;
import mod.vemerion.runeworld.structure.FireRitualStructure;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public class ModStructurePieces {

	public static StructurePieceType BLOOD_BAT_LAIR_PIECE;
	public static StructurePieceType FIRE_RITUAL_PIECE;
	public static StructurePieceType BLOOD_GORILLA_THRONE_PIECE;

	public static void register() {
		Registry<StructurePieceType> reg = Registry.STRUCTURE_PIECE;

		BLOOD_BAT_LAIR_PIECE = BloodBatLairStructure.Piece::new;
		FIRE_RITUAL_PIECE = FireRitualStructure.Piece::new;
		BLOOD_GORILLA_THRONE_PIECE = BloodGorillaThroneStructure.Piece::new;

		Registry.register(reg, new ResourceLocation(Main.MODID, "blood_bat_lair_piece"), BLOOD_BAT_LAIR_PIECE);
		Registry.register(reg, new ResourceLocation(Main.MODID, "fire_ritual_piece"), FIRE_RITUAL_PIECE);
		Registry.register(reg, new ResourceLocation(Main.MODID, "blood_gorilla_throne_piece"), BLOOD_GORILLA_THRONE_PIECE);
	}
}
