package mod.vemerion.runeworld.init;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ObjectHolder;

public class Runesword {
	public static final String MODID = "runesword";
	
	@ObjectHolder(Runesword.MODID + ":fire_rune")
	public static final Item FIRE_RUNE = null;
	@ObjectHolder(Runesword.MODID + ":water_rune")
	public static final Item WATER_RUNE = null;
	@ObjectHolder(Runesword.MODID + ":earth_rune")
	public static final Item EARTH_RUNE = null;
	@ObjectHolder(Runesword.MODID + ":air_rune")
	public static final Item AIR_RUNE = null;
	@ObjectHolder(Runesword.MODID + ":blood_rune")
	public static final Item BLOOD_RUNE = null;
	
	private static ImmutableSet<Item> runes;
	
	public static Set<Item> getRunes() {
		if (runes == null)
			runes = initRunes();
		return runes;
	}
	
	public static boolean isRune(Item item) {
		return getRunes().contains(item);
	}
	
	public static boolean isLoaded() {
		return ModList.get().isLoaded(MODID);
	}

	private static ImmutableSet<Item> initRunes() {
		return ImmutableSet.of(FIRE_RUNE, WATER_RUNE, EARTH_RUNE, AIR_RUNE, BLOOD_RUNE);
	}
}
