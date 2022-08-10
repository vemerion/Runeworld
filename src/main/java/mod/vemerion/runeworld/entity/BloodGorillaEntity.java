package mod.vemerion.runeworld.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class BloodGorillaEntity extends Monster {

	public BloodGorillaEntity(EntityType<? extends BloodGorillaEntity> type, Level level) {
		super(type, level);
	}

	public static AttributeSupplier.Builder attributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20)
				.add(Attributes.MOVEMENT_SPEED, 0.25)
				.add(Attributes.FOLLOW_RANGE, 16)
				.add(Attributes.ATTACK_DAMAGE, 3);
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

}
