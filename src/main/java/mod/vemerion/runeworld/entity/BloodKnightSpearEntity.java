package mod.vemerion.runeworld.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class BloodKnightSpearEntity extends BloodKnightEntity {

	public BloodKnightSpearEntity(EntityType<? extends BloodKnightSpearEntity> type, Level level) {
		super(type, level, 30, 15 * 15);
	}

	public static AttributeSupplier.Builder attributes() {
		return BloodKnightEntity.attributes().add(Attributes.ATTACK_KNOCKBACK, 3);
	}

	@Override
	protected void specialAttack() {

	}

}
