package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.structure.BloodBatLairStructure;
import mod.vemerion.runeworld.structure.FireRitualStructure;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class ModStructurePieces {

	public static IStructurePieceType BLOOD_BAT_LAIR_PIECE;
	public static IStructurePieceType FIRE_RITUAL_PIECE;

	public static void register() {
		Registry<IStructurePieceType> reg = Registry.STRUCTURE_PIECE;

		BLOOD_BAT_LAIR_PIECE = BloodBatLairStructure.Piece::new;
		FIRE_RITUAL_PIECE = FireRitualStructure.Piece::new;

		Registry.register(reg, new ResourceLocation(Main.MODID, "blood_bat_lair_piece"), BLOOD_BAT_LAIR_PIECE);
		Registry.register(reg, new ResourceLocation(Main.MODID, "fire_ritual_piece"), FIRE_RITUAL_PIECE);
	}
}
