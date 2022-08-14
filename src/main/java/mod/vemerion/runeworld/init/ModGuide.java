package mod.vemerion.runeworld.init;

import mod.vemerion.runesword.api.IGuide;
import mod.vemerion.runesword.api.IGuideChapter;
import mod.vemerion.runesword.api.RuneswordAPI;
import mod.vemerion.runeworld.Main;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TranslatableComponent;

public class ModGuide {

	private static IGuideChapter start;
	private static IGuideChapter portal;
	private static IGuideChapter bloodWorld;
	private static IGuideChapter blood;
	private static IGuideChapter bloodWorldMobs;
	private static IGuideChapter bloodCrystallite;
	private static IGuideChapter fireWorld;
	private static IGuideChapter fireRitual;

	public static IGuideChapter getStartChapter() {
		if (start == null) {
			IGuide guide = RuneswordAPI.guide;
			start = guide.createGuideChapter(ModItems.GUIDE.get(), new TranslatableComponent(transKey("guide")));
			portal = guide.createGuideChapter(Blocks.OBSIDIAN, new TranslatableComponent(transKey("portal")));
			bloodWorld = guide.createGuideChapter(Runesword.BLOOD_RUNE,
					new TranslatableComponent(transKey("blood_world")));
			blood = guide.createGuideChapter(ModItems.BLOOD_BUCKET.get(),
					new TranslatableComponent(transKey("blood_world.blood")));
			bloodWorldMobs = guide.createGuideChapter(ModItems.MOSQUITO_SPAWN_EGG.get(),
					new TranslatableComponent(transKey("blood_world.mobs")));
			bloodCrystallite = guide.createGuideChapter(ModItems.BLOOD_CRYSTALLITE.get(),
					new TranslatableComponent(transKey("blood_world.blood_crystallite")));
			fireWorld = guide.createGuideChapter(Runesword.FIRE_RUNE,
					new TranslatableComponent(transKey("fire_world")));
			fireRitual = guide.createGuideChapter(ModBlocks.FIRE_RITUAL_STONE.get(),
					new TranslatableComponent(transKey("fire_world.fire_ritual")));

			portal.addText(transKey("portal.text1")).addText(transKey("portal.text2"))
					.addImage(image("portal1"), 1536, 864).addImage(image("portal2"), 1536, 864)
					.addText(transKey("portal.text3"));
			bloodWorld.addChild(blood).addChild(bloodWorldMobs).addChild(bloodCrystallite)
					.addText(transKey("blood_world.text1"));
			blood.addText(transKey("blood_world.blood.text1")).addText(transKey("blood_world.blood.text2"))
					.addText(transKey("blood_world.blood.text3")).addText(transKey("blood_world.blood.text4"))
					.addText(transKey("blood_world.blood.text5"));
			bloodWorldMobs.addText(transKey("blood_world.mobs.intro")).addHeader(transKey("blood_world.mobs.blood_bat"))
					.addText(transKey("blood_world.mobs.blood_bat.text1"))
					.addText(transKey("blood_world.mobs.blood_bat.text2"))
					.addHeader(transKey("blood_world.mobs.mosquito"))
					.addText(transKey("blood_world.mobs.mosquito.text1"))
					.addText(transKey("blood_world.mobs.mosquito.text2"))
					.addHeader(transKey("blood_world.mobs.blood_monkey"))
					.addText(transKey("blood_world.mobs.blood_monkey.text1"))
					.addText(transKey("blood_world.mobs.blood_monkey.text2"));
			bloodCrystallite.addText(transKey("blood_world.blood_crystallite.text1"))
					.addText(transKey("blood_world.blood_crystallite.text2"))
					.addText(transKey("blood_world.blood_crystallite.text3"))
					.addText(transKey("blood_world.blood_crystallite.text4"))
					.addText(transKey("blood_world.blood_crystallite.text5"));
			fireWorld.addChild(fireRitual).addText(transKey("fire_world.text1"));
			fireRitual.addText(transKey("fire_world.fire_ritual.text1"))
					.addText(transKey("fire_world.fire_ritual.text2"));
			start.addChild(portal).addChild(bloodWorld).addChild(fireWorld).addText(transKey("intro"));
		}
		return start;
	}

	private static String transKey(String suffix) {
		return transKey("guide", suffix);
	}

	private static String transKey(String prefix, String suffix) {
		return prefix + "." + Main.MODID + "." + suffix;
	}

	private static ResourceLocation image(String name) {
		return new ResourceLocation(Main.MODID, "textures/guide/" + name + ".png");
	}
}
