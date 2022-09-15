package mod.vemerion.runeworld.entity;

import mod.vemerion.runeworld.helpers.Helper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BloodKnightSpearEntity extends BloodKnightEntity {

	public BloodKnightSpearEntity(EntityType<? extends BloodKnightSpearEntity> type, Level level) {
		super(type, level, 30, 15 * 15);
	}

	public static AttributeSupplier.Builder attributes() {
		return BloodKnightEntity.attributes().add(Attributes.ATTACK_KNOCKBACK, 3);
	}

	@Override
	protected void specialAttack() {
		var forward = Vec3.directionFromRotation(0, getYRot());
		var target = getTarget();
		var targetPos = position().add(forward.scale(10));
		if (target != null)
			targetPos = target.getEyePosition();

		var pos = position().add(0, 4, 0).add(forward.yRot(Helper.toRad(-90)));
		var spear = new SpearEntity(pos.x, pos.y, pos.z, level);
		spear.setOwner(this);
		spear.shoot(targetPos.x - pos.x, targetPos.y - pos.y, targetPos.z - pos.z, 1.6f, 4);
		level.addFreshEntity(spear);
	}

}
