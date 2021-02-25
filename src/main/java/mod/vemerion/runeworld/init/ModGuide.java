package mod.vemerion.runeworld.init;

import mod.vemerion.runesword.api.IGuide;
import mod.vemerion.runesword.api.IGuideChapter;
import mod.vemerion.runesword.api.RuneswordAPI;
import mod.vemerion.runeworld.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class ModGuide {
	
	private static IGuideChapter start;

	public static IGuideChapter getStartChapter() {
		if (start == null) {
			IGuide guide = RuneswordAPI.guide;
			start = guide.createGuideChapter(ModItems.GUIDE, new TranslationTextComponent(transKey("guide")));

			start.addText(transKey("intro"));
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
