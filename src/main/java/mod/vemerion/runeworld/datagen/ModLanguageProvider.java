package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.init.ModBiomes;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModEffects;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

	public ModLanguageProvider(DataGenerator gen) {
		super(gen, Main.MODID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add(ModBlocks.BLOOD, "Blood");
		add(ModBlocks.BLOOD_FLOWER, "Blood Flower");
		add(ModBlocks.BLOOD_PILLAR_LARGE, "Blood Pillar Large");
		add(ModBlocks.BLOOD_PILLAR_MEDIUM, "Blood Pillar Medium");
		add(ModBlocks.BLOOD_PILLAR_SMALL, "Blood Pillar Small");
		add(ModBlocks.BLOOD_ROCK, "Blood Rock");
		add(ModBlocks.BLOOD_MOSS, "Blood Moss");
		add(ModBlocks.BLOOD_CRYSTAL, "Blood Crystal");
		add(ModBlocks.BLOOD_LEECH, "Blood Leech");
		add(ModItems.BLOOD_BUCKET, "Blood Bucket");
		add(ModItems.BLOOD_PUDDING, "Blood Pudding");
		add(ModItems.MOSQUITO_EGGS, "Mosquito Eggs");
		add(ModItems.BLOOD_BAT_TOOTH, "Blood Bat Tooth");
		add(ModItems.GUIDE, "Runeworld Guide");
		add(ModItems.BLOOD_CRYSTALLITE, "Blood Crystallite");
		add(ModItems.BLOOD_DISLOCATOR, "Blood Dislocator");
		add(ModEffects.BLOOD_DRAINED, "Blood Drained");
		add(ModBiomes.BLOOD_PLAINS, "Blood Plains");
		add(ModEntities.MOSQUITO, "Mosquito");
		add(ModEntities.MOSQUITO_SPAWN_EGG, "Mosquito Spawn Egg");
		add(ModEntities.BLOOD_BAT_SPAWN_EGG, "Blood Bat Spawn Egg");
		add(ModEntities.MOSQUITO_EGGS, "Mosquito Eggs");
		add(ModEntities.BLOOD_BAT, "Blood Bat");
		add("itemGroup." + Main.MODID, "Runeworld");

		guide();
	}

	private void guide() {
		addGuide("guide", "Runeworld");
		addGuide("intro",
				"Welcome to the Runeworld guide, containing all the information you need to begin your dimension travelling! Click on one of the icons above to learn more about a specific subject.");

		addGuide("portal", "Portal Creation");
		addGuide("portal.text1",
				"You travel to the different rune worlds similar to how you travel to the nether. Start by creating an obsidian frame. Then, light the portal by right-clicking the frame with a rune (the rune will be consumed).");
		addGuide("portal.text2",
				"The rules for the portal size and shape are less strict than for nether portals. See below images for a couple of examples.");
		addGuide("portal.text3",
				"But beware, for unlike the nether portal, no portal is generated on the other side when you enter! Be sure to bring extra obsidian and runes, to make sure that you are not stuck in a rune world forever!");

		addGuide("blood_world", "Blood World");
		addGuide("blood_world.text1",
				"The blood world is a grim and hostile world. In a perpetuate dusk, the light is always dim, only the faintest light coming from the dark sun. Giant mosquitoes buzzing around the many blood pools, and Blood Bats hanging upside down from large dead trees. Why would you want to travel there?");

		addGuide("blood_world.blood", "Blood");
		addGuide("blood_world.blood.text1",
				"Instead of water, blood is the fluid that can be found on the surface of the blood world. In many ways, it functions similar to water, for example, you can scoop it up in a bucket. On the other hand, blood has a lot of unique mechanics, detailed below:");
		addGuide("blood_world.blood.text2",
				"You can drink for the blood bucket, similar to milk. This can be used as a cure to the dangerous blood drained sickness that some enemies inflict.");
		addGuide("blood_world.blood.text3",
				"If you throw any type of rune into a large enough blood pool, after a while, some of the blood will be consumed, and the rune will be converted into a blood rune.");
		addGuide("blood_world.blood.text4",
				"Blood can be used to speed up the growth of nearby crops, but after a while, the blood will be converted into normal water.");
		addGuide("blood_world.blood.text5", "Also, fishing in blood could yield interesting catch...");

		addGuide("blood_world.mobs", "Mobs");
		addGuide("blood_world.mobs.intro", "The Blood World is the home of several hideous monsters.");
		addGuide("blood_world.mobs.blood_bat", "Blood Bat");
		addGuide("blood_world.mobs.blood_bat.text1",
				"The blood bat is a large and dangerous monster. They spend most of their time sleeping upside down in giant dead trees, but if awakened, they will attack! When they bite their long teeth into the flesh of their target, they have a chance to apply the dangerous blood drained disease.");
		addGuide("blood_world.mobs.blood_bat.text2",
				"When killed, the blood bat has a chance to drop a tooth, which can be used as a weapon that applies the blood drained disease.");
		addGuide("blood_world.mobs.mosquito", "Mosquito");
		addGuide("blood_world.mobs.mosquito.text1",
				"Giant mosquitoes, filled to the brim with blood, can be found buzzing near blood pools in the blood world. They are fairly weak, but can overwhelm careless adventurers by sheer numbers.");
		addGuide("blood_world.mobs.mosquito.text2",
				"When killed, the mosquito has a chance to drop a bundle of mosquito eggs.");
	}

	private void addGuide(String suffix, String text) {
		addText("guide", suffix, text);
	}

	private void addText(String prefix, String suffix, String text) {
		add(prefix + "." + Main.MODID + "." + suffix, text);
	}

	private void add(Biome key, String name) {
		add("biome." + key.getRegistryName().getNamespace() + "." + key.getRegistryName().getPath(), name);
	}
}
