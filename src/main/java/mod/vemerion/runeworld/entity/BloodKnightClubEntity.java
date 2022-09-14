package mod.vemerion.runeworld.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BloodKnightClubEntity extends BloodKnightEntity {

	public BloodKnightClubEntity(EntityType<? extends BloodKnightClubEntity> type, Level level) {
		super(type, level, 11, 20);
	}

	public static AttributeSupplier.Builder attributes() {
		return BloodKnightEntity.attributes();
	}

	@Override
	protected void specialAttack() {
		for (var player : level.getEntitiesOfClass(Player.class, getBoundingBox().inflate(3))) {
			player.hurt(DamageSource.mobAttack(this), (float) (getAttributeValue(Attributes.ATTACK_DAMAGE) * 3));
			player.setDeltaMovement(player.getDeltaMovement().add(0, 1, 0));
		}
	}
}
