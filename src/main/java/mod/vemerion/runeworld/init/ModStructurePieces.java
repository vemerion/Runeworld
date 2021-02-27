package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.structure.BloodBatLairStructure;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class ModStructurePieces {

	public static IStructurePieceType BLOOD_BAT_LAIR_PIECE;

	public static void register() {
		BLOOD_BAT_LAIR_PIECE = BloodBatLairStructure.Piece::new;

		Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(Main.MODID, "blood_bat_lair_piece"),
				BLOOD_BAT_LAIR_PIECE);
	}
}
