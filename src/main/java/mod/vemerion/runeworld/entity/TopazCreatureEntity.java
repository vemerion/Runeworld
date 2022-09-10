package mod.vemerion.runeworld.entity;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModLootTables;
import mod.vemerion.runeworld.init.ModSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class TopazCreatureEntity extends TamableAnimal {

	public static final TagKey<Item> FOOD = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(),
			new ResourceLocation(Main.MODID, "topaz_creature_food"));

	public TopazCreatureEntity(EntityType<? extends TopazCreatureEntity> type, Level worldIn) {
		super(type, worldIn);
		this.xpReward = Enemy.XP_REWARD_SMALL;
	}

	public static AttributeSupplier.Builder attributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.MOVEMENT_SPEED, 0.25)
				.add(Attributes.FOLLOW_RANGE, 16);
	}

	protected void registerGoals() {
		goalSelector.addGoal(0, new PanicGoal(this, 1.25));
		goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 8));
		goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
		goalSelector.addGoal(3, new FollowOwnerGoal(this, 1, 10, 2, true));
		goalSelector.addGoal(4, new FollowMobGoal(this, 1, 3, 7));
	}

	public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
		var stack = pPlayer.getItemInHand(pHand);

		if (stack.is(FOOD)) {
			if (!pPlayer.isCreative())
				stack.shrink(1);

			if (!level.isClientSide) {
				poop();
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else if (isOwnedBy(pPlayer) && isTame()) {
			if (!level.isClientSide)
				setOrderedToSit(!isOrderedToSit());
			return InteractionResult.sidedSuccess(level.isClientSide);
		}

		return InteractionResult.PASS;
	}

	private void poop() {
		playSound(ModSounds.CHOMP.get(), 1, getVoicePitch());

		var table = level.getServer().getLootTables().get(ModLootTables.TOPAZ_CREATURE_POOP);
		var context = (new LootContext.Builder((ServerLevel) level)).withParameter(LootContextParams.ORIGIN, position())
				.withParameter(LootContextParams.THIS_ENTITY, this).withRandom(random);

		var pos = position().add(Vec3.directionFromRotation(0, yBodyRot).reverse().scale(0.7));
		for (var poop : table.getRandomItems(context.create(LootContextParamSets.GIFT))) {
			var itemEntity = new ItemEntity(level, pos.x, pos.y, pos.z, poop);
			itemEntity.setDefaultPickUpDelay();
			level.addFreshEntity(itemEntity);
		}
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
		return null;
	}

	@Override
	public boolean isBaby() {
		return false;
	}

	@Override
	public boolean isFood(ItemStack pStack) {
		return false;
	}

	@Override
	public boolean canMate(Animal pOtherAnimal) {
		return false;
	}
}
