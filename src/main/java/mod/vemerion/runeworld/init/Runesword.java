package mod.vemerion.runeworld.init;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ObjectHolder;

public class Runesword {
	public static final String MODID = "runesword";
	
	@ObjectHolder(Runesword.MODID + ":fire_rune_item")
	public static final Item FIRE_RUNE = null;
	@ObjectHolder(Runesword.MODID + ":water_rune_item")
	public static final Item WATER_RUNE = null;
	@ObjectHolder(Runesword.MODID + ":earth_rune_item")
	public static final Item EARTH_RUNE = null;
	@ObjectHolder(Runesword.MODID + ":air_rune_item")
	public static final Item AIR_RUNE = null;
	@ObjectHolder(Runesword.MODID + ":blood_rune_item")
	public static final Item BLOOD_RUNE = null;
	
	private static ImmutableSet<Item> runes;
	
	public static Set<Item> getRunes() {
		if (runes == null)
			runes = initRunes();
		return runes;
	}
	
	public static boolean isLoaded() {
		return ModList.get().isLoaded(MODID);
	}

	private static ImmutableSet<Item> initRunes() {
		return ImmutableSet.of(FIRE_RUNE, WATER_RUNE, EARTH_RUNE, AIR_RUNE, BLOOD_RUNE);
	}
}
