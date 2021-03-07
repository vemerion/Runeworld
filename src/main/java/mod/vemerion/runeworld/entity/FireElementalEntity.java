package mod.vemerion.runeworld.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;

public class FireElementalEntity extends MonsterEntity {
	
	private final ServerBossInfo bossInfo = new ServerBossInfo(getDisplayName(),
			BossInfo.Color.YELLOW, BossInfo.Overlay.PROGRESS);

	public FireElementalEntity(EntityType<? extends FireElementalEntity> type, World worldIn) {
		super(type, worldIn);
		this.experienceValue = 20;
	}
	
	public static AttributeModifierMap.MutableAttribute attributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 20)
				.createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25)
				.createMutableAttribute(Attributes.FOLLOW_RANGE, 16)
				.createMutableAttribute(Attributes.ATTACK_DAMAGE, 3);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		bossInfo.setPercent(getHealthPercent());
	}
	
	private float getHealthPercent() {
		return getHealth() / getMaxHealth();
	}
	
	@Override
	public void addTrackingPlayer(ServerPlayerEntity player) {
		super.addTrackingPlayer(player);
		bossInfo.addPlayer(player);
	}

	@Override
	public void removeTrackingPlayer(ServerPlayerEntity player) {
		super.removeTrackingPlayer(player);
		bossInfo.removePlayer(player);
	}

	@Override
	public boolean onLivingFall(float fallDistance, float damageMultiplier) {
		return false;
	}
	
	@Override
	public boolean isNonBoss() {
		return false;
	}
}
