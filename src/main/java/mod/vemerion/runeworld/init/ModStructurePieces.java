package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.structure.BloodBatLairStructure;
import mod.vemerion.runeworld.structure.BloodMonkeyTunnelsStructure;
import mod.vemerion.runeworld.structure.FireRitualStructure;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStructurePieces {
	
	public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECES = DeferredRegister.create(Registry.STRUCTURE_PIECE_REGISTRY, Main.MODID);

	public static final RegistryObject<StructurePieceType> BLOOD_BAT_LAIR_PIECE = STRUCTURE_PIECES.register("blood_bat_lair_piece", () -> BloodBatLairStructure.Piece::new);
	public static final RegistryObject<StructurePieceType> FIRE_RITUAL_PIECE = STRUCTURE_PIECES.register("fire_ritual_piece", () -> FireRitualStructure.Piece::new);
	public static final RegistryObject<StructurePieceType> BLOOD_MONKEY_TUNNELS_ENTRANCE_PIECE = STRUCTURE_PIECES.register("blood_monkey_tunnels_entrance_piece", () -> BloodMonkeyTunnelsStructure.EntrancePiece::new);

}
