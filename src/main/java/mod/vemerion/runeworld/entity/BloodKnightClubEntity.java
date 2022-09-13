package mod.vemerion.runeworld.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;

public class BloodKnightClubEntity extends BloodKnightEntity {

	public BloodKnightClubEntity(EntityType<? extends BloodKnightClubEntity> type, Level level) {
		super(type, level);
	}

	public static AttributeSupplier.Builder attributes() {
		return BloodKnightEntity.attributes();
	}

}
