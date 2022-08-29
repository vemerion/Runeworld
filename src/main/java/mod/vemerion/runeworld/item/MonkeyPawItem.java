package mod.vemerion.runeworld.item;

import com.google.common.collect.ImmutableList;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.entity.BloodMonkeyEntity;
import mod.vemerion.runeworld.init.ModConfiguredStructures;
import mod.vemerion.runeworld.init.ModEffects;
import mod.vemerion.runeworld.structure.BloodMonkeyTunnelsStructure;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;

public class MonkeyPawItem extends Item {

	public static final DamageSource DAMAGE_SOURCE = new DamageSource(Main.MODID + ".monkey_paw");
	private static final int CURSE_DURATION = 20 * 60 * 15;

	public MonkeyPawItem() {
		super(new Item.Properties().tab(CreativeModeTab.TAB_SEARCH).durability(3).rarity(Rarity.UNCOMMON)
				.setNoRepair());
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 40;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		var stack = playerIn.getItemInHand(handIn);
		playerIn.startUsingItem(handIn);
		return InteractionResultHolder.consume(stack);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		if (level.isClientSide)
			return stack;

		if (entity instanceof Player player && !player.isCreative())
			stack.hurtAndBreak(1, entity, e -> e.broadcastBreakEvent(entity.getUsedItemHand()));

		var rand = entity.getRandom();
		var serverLevel = (ServerLevel) level;

		switch (rand.nextInt(6)) {
		case 0: // Monkey curse
			entity.addEffect(new MobEffectInstance(ModEffects.MONKEY_CURSE.get(), CURSE_DURATION, 0));
			break;
		case 1: // Instant death
			entity.hurt(DAMAGE_SOURCE, Float.MAX_VALUE);
			break;
		case 2: {// Spawn monkeys
			for (int i = 0; i < 20; i++) {
				var pos = entity.position()
						.add(new Vec3(1, 0, 0).yRot(rand.nextFloat() * Mth.TWO_PI).scale(rand.nextDouble() * 10));
				BloodMonkeyEntity.spawn(serverLevel, pos, MobSpawnType.MOB_SUMMONED);
			}
			break;
		}
		case 3: { // Create treasure map to blood monkey tunnels
			var pos = serverLevel.findNearestMapFeature(ModConfiguredStructures.BLOOD_MONKEY_TUNNELS_TAG,
					entity.blockPosition(), 100, true);
			if (pos != null) {
				var map = MapItem.create(serverLevel, pos.getX() + rand.nextInt(-40, 40),
						pos.getZ() + rand.nextInt(-40, 40), (byte) 2, true, true);
				MapItem.renderBiomePreviewMap(serverLevel, map);
				MapItemSavedData.addTargetDecoration(map, pos, "+", MapDecoration.Type.RED_X);
				entity.spawnAtLocation(map);
				map.setHoverName(new TranslatableComponent(BloodMonkeyTunnelsStructure.translationKey()));
			}
			break;
		}
		case 4: { // Spawn valuable items
			var valuables = ImmutableList.of(Items.DIAMOND, Items.GOLD_INGOT, Items.IRON_INGOT, Items.LAPIS_LAZULI,
					Items.REDSTONE, Items.EMERALD, Items.COPPER_INGOT);
			for (int i = 0; i < 40; i++) {
				var pos = nearby(entity);
				level.addFreshEntity(new ItemEntity(level, pos.x, pos.y, pos.z,
						valuables.get(rand.nextInt(valuables.size())).getDefaultInstance()));
			}
			break;
		}
		case 5: // Spawn exp
			for (int i = 0; i < 20; i++) {
				ExperienceOrb.award(serverLevel, nearby(entity), 200);
			}
			break;
		}

		return stack;
	}

	private Vec3 nearby(LivingEntity entity) {
		return entity.position().add(new Vec3(2, 0, 0).yRot(entity.getRandom().nextFloat() * Mth.TWO_PI));
	}

	// No unbreaking/mending!
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}
}
