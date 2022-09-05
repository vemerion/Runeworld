package mod.vemerion.runeworld.datagen;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.FleshEatingPlantFlowerBlock;
import mod.vemerion.runeworld.block.complex.StoneMaterial;
import mod.vemerion.runeworld.init.ModBiomes;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModEffects;
import mod.vemerion.runeworld.init.ModEnchantments;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.item.MonkeyPawItem;
import mod.vemerion.runeworld.structure.BloodMonkeyTunnelsStructure;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

	public ModLanguageProvider(DataGenerator gen) {
		super(gen, Main.MODID, "en_us");
	}

	@Override
	protected void addTranslations() {
		add(ModBlocks.BLOOD.get(), "Blood");
		add(ModBlocks.BLOOD_FLOWER.get(), "Blood Flower");
		add(ModBlocks.BLOOD_PILLAR_LARGE.get(), "Blood Pillar Large");
		add(ModBlocks.BLOOD_PILLAR_MEDIUM.get(), "Blood Pillar Medium");
		add(ModBlocks.BLOOD_PILLAR_SMALL.get(), "Blood Pillar Small");
		add(ModBlocks.HIDEABLE_BLOOD_ROCK.get(), "Hideable Blood Rock");
		add(ModBlocks.HIDEABLE_BLOOD_ROCK_BRICKS.get(), "Hideable Blood Rock Bricks");
		add(ModBlocks.BLOOD_MOSS.get(), "Blood Moss");
		add(ModBlocks.BLOOD_CRYSTAL.get(), "Blood Crystal");
		add(ModBlocks.BLOOD_LEECH.get(), "Blood Leech");
		add(ModBlocks.BURNT_DIRT.get(), "Burnt Dirt");
		add(ModBlocks.CHARRED_DIRT.get(), "Charred Dirt");
		add(ModBlocks.FIRE_RITUAL_STONE.get(), "Fire Ritual Stone");
		add(ModBlocks.FIRE_ROOT.get(), "Fire Root");
		add(ModBlocks.FLESH_EATING_PLANT_STEM.get(), "Carnivorous Plant");
		add(ModBlocks.FLESH_EATING_PLANT_FLOWER.get(), "Carnivorous Plant");
		add(ModBlocks.TOPAZ.get(), "Topaz");
		add(ModBlocks.MIRROR.get(), "Mirror");
		add(ModItems.BLOOD_BUCKET.get(), "Blood Bucket");
		add(ModItems.BLOOD_PUDDING.get(), "Blood Pudding");
		add(ModItems.MOSQUITO_EGGS.get(), "Mosquito Eggs");
		add(ModItems.BLOOD_BAT_TOOTH.get(), "Blood Bat Tooth");
		add(ModItems.GUIDE.get(), "Runeworld Guide");
		add(ModItems.BLOOD_CRYSTALLITE.get(), "Blood Crystallite");
		add(ModItems.BLOOD_DISLOCATOR.get(), "Blood Dislocator");
		add(ModItems.BLOOD_PEBBLE.get(), "Blood Pebble");
		add(ModItems.GRILLED_BLOOD_LEECH.get(), "Grilled Blood Leech");
		add(ModItems.FIRE_HEART.get(), "Fire Heart");
		add(ModItems.FIRE_DISLOCATOR.get(), "Fire Dislocator");
		add(ModItems.MOSQUITO_SPAWN_EGG.get(), "Mosquito Spawn Egg");
		add(ModItems.BLOOD_BAT_SPAWN_EGG.get(), "Blood Bat Spawn Egg");
		add(ModItems.BLOOD_MONKEY_SPAWN_EGG.get(), "Blood Monkey Spawn Egg");
		add(ModItems.BLOOD_GORILLA_SPAWN_EGG.get(), "Blood Gorilla Spawn Egg");
		add(ModItems.TICK_SPAWN_EGG.get(), "Tick Spawn Egg");
		add(ModItems.BLOOD_CROWN.get(), "Blood Crown");
		add(ModItems.MONKEY_PAW.get(), "Monkey's Paw");
		add(ModItems.SLINGSHOT.get(), "Slingshot");
		add(ModItems.TOPAZ_GEM.get(), "Topaz Gem");
		add(ModItems.TOPAZ_SHARD.get(), "Topaz Shard");
		add(ModEffects.BLOOD_DRAINED.get(), "Blood Drained");
		add(ModEffects.MONKEY_CURSE.get(), "Monkey's Curse");
		add(ModBiomes.BLOOD_PLAINS.get(), "Blood Plains");
		add(ModBiomes.FIRE_PLAINS.get(), "Fire Plains");
		add(ModEntities.MOSQUITO.get(), "Mosquito");
		add(ModEntities.MOSQUITO_EGGS.get(), "Mosquito Eggs");
		add(ModEntities.BLOOD_BAT.get(), "Blood Bat");
		add(ModEntities.BLOOD_MONKEY.get(), "Blood Monkey");
		add(ModEntities.SLINGSHOT_PROJECTILE.get(), "Slingshot Projectile");
		add(ModEntities.FIRE_ELEMENTAL.get(), "Fire Elemental");
		add(ModEntities.FIRE_ELEMENTAL_PROJECTILE.get(), "Fire Elemental Projectile");
		add(ModEntities.BLOOD_GORILLA.get(), "Blood Gorilla");
		add(ModEntities.TICK.get(), "Tick");
		add(ModEnchantments.ELASTIC.get(), "Elastic");
		add(ModEnchantments.HARDNESS.get(), "Hardness");
		add(ModEnchantments.QUICK_DRAW.get(), "Quick Draw");
		add(ModEnchantments.RETENTION.get(), "Retention");

		addDamageSource(FleshEatingPlantFlowerBlock.DAMAGE_SOURCE, "%s was eaten by a plant");
		addDamageSourcePlayer(FleshEatingPlantFlowerBlock.DAMAGE_SOURCE,
				"%s was eaten by a plant whilst trying to escape %s");
		addDamageSource(MonkeyPawItem.DAMAGE_SOURCE, "%s got the short end of the stick when using the Monkey's Paw");
		addDamageSourcePlayer(MonkeyPawItem.DAMAGE_SOURCE, "%s was cursed to death whilst trying to escape %s");

		add(BloodMonkeyTunnelsStructure.translationKey(), "Blood Monkey Tunnels Explorer Map");

		add("itemGroup." + Main.MODID, "Runeworld");
		add("item." + Main.MODID + "." + ModItems.BLOOD_CROWN.getId().getPath() + ".description", "Majestic");

		// Complex
		stoneMaterial(ModBlocks.SPARKSTONE_MATERIAL, "Sparkstone");
		stoneMaterial(ModBlocks.CHARRED_STONE_MATERIAL, "Charred Stone");
		stoneMaterial(ModBlocks.BLOOD_ROCK_MATERIAL, "Blood Rock");
		stoneMaterial(ModBlocks.BLOOD_ROCK_BRICKS_MATERIAL, "Blood Rock Bricks");

		// Guide
		guide();
	}

	private void addDamageSource(DamageSource source, String text) {
		add("death.attack." + source.getMsgId(), text);
	}

	private void addDamageSourcePlayer(DamageSource source, String text) {
		add("death.attack." + source.getMsgId() + ".player", text);
	}

	private void stoneMaterial(StoneMaterial material, String name) {
		add(material.block(), name);
		add(material.stair(), name + " Stairs");
		add(material.slab(), name + " Slab");
		add(material.wall(), name + " Wall");
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
				"The blood bat is a large and dangerous monster. They spend most of their time sleeping upside down in giant dead trees or hidden lairs, but if awakened, they will attack! When they bite their long teeth into the flesh of their target, they have a chance to apply the dangerous blood drained disease.");
		addGuide("blood_world.mobs.blood_bat.text2",
				"When killed, the blood bat has a chance to drop a tooth, which can be used as a weapon that applies the blood drained disease.");
		addGuide("blood_world.mobs.mosquito", "Mosquito");
		addGuide("blood_world.mobs.mosquito.text1",
				"Giant mosquitoes, filled to the brim with blood, can be found buzzing near blood pools in the blood world. They are fairly weak, but can overwhelm careless adventurers by sheer numbers.");
		addGuide("blood_world.mobs.mosquito.text2",
				"When killed, the mosquito has a chance to drop a bundle of mosquito eggs.");
		addGuide("blood_world.mobs.blood_monkey", "Blood Monkey");
		addGuide("blood_world.mobs.blood_monkey.text1",
				"The blood monkey is an annoying creature. They balance on top of large blood pillars, out of reach, and throw pebbles at you! And if you knock them down, they will come running at you!");
		addGuide("blood_world.mobs.blood_monkey.text2",
				"When killed, they drop their pesky blood pebbles. They can be used to skip stones. Fun!");
		addGuide("blood_world.mobs.blood_gorilla", "Blood Gorilla");
		addGuide("blood_world.mobs.blood_gorilla.text1",
				"The blood gorilla is a menacing type of ape, towering above most other creatures. They reside on giant thrones found deep down in the blood monkey tunnels, and will remain calm unless provoked. Adventurers should be prepared before picking a fight with a blood gorilla, since they are both very resilient and hard-hitting.");
		addGuide("blood_world.mobs.blood_gorilla.text2",
				"When killed, they drop their crown, which when worn, will allow the player to 'open their eyes'.");
		addGuide("blood_world.mobs.tick", "Tick");
		addGuide("blood_world.mobs.tick.text1",
				"The tick is a mob that can sometimes be found attached to blood monkeys. They leech blood when attacking (or when their host is hurt), and will grow in size. The bigger they grow, the more unstable they get, and the likelier they are to explode, damaging everyone around them!");

		addGuide("blood_world.blood_crystallite", "Blood Crystallite");
		addGuide("blood_world.blood_crystallite.text1",
				"A Blood Crystallite is a rare and valuable item. It can be found in hidden blood bat lairs, which look like large hills to the untrained eye, but are hollow and the home to flocks of blood bats.");
		addGuide("blood_world.blood_crystallite.text2",
				"You might think it safe to snatch the crystallite while the bats are sleeping, but be careful! Blood bats can smell nearby players carrying blood crystallites, and will wake up to attack!");
		addGuide("blood_world.blood_crystallite.text3",
				"If you manage to escape a blood bat lair with a crystallite, you should consider yourself lucky, for the crystallite has several uses.");
		addGuide("blood_world.blood_crystallite.text4",
				"The blood can be washed of the crystallite in a cauldron, revealing a diamond underneath.");
		addGuide("blood_world.blood_crystallite.text5",
				"It can be used to craft a blood translocator item, which functions like a portable rune portal to and from the blood world. Unfortunately, it is consumed after it is used once.");

		addGuide("blood_world.blood_monkey_tunnels", "Blood Monkey Tunnels");
		addGuide("blood_world.blood_monkey_tunnels.text1",
				"The Blood Monkey Tunnels is a sprawling structure that can be found deep underground. The only trace of these tunnels on the surface are small entraces, with long vines leading down into the structure. These tunnels, home to the blood monkeys, are dark and dangerous, but also holds many treasures, such as the potent ranged slingshot weapon (which uses blood pebbles as ammunition).");
		addGuide("blood_world.blood_monkey_tunnels.text2",
				"A cursed item, the Monkey's Paw, can also be founds hidden in the deepest depths of the tunnels. This items can be used exactly three times, either granting immesurable fortune, or unimaginable terror. Are you brave enough to use it?");

		addGuide("blood_world.blood_enchantments", "Enchantments");
		addGuide("blood_world.blood_enchantments.quick_draw", "Quick Draw");
		addGuide("blood_world.blood_enchantments.quick_draw.text",
				"This enchantment can be applied to slingshots, and will reduce the time it takes for the slingshot to reach full power.");
		addGuide("blood_world.blood_enchantments.retention", "Retention");
		addGuide("blood_world.blood_enchantments.retention.text",
				"Another slingshot enchantment, which will give the slingshot a small chance to return the blood pebbles upon impact.");
		addGuide("blood_world.blood_enchantments.hardness", "Hardness");
		addGuide("blood_world.blood_enchantments.hardness.text",
				"Another slingshot enchantment, which reduces the risk of pebbles getting destroyed upon impact.");
		addGuide("blood_world.blood_enchantments.elastic", "Elastic");
		addGuide("blood_world.blood_enchantments.elastic.text",
				"Yet another slingshot enchantment, which increases the damage of the slingshot.");

		addGuide("fire_world", "Fire World");
		addGuide("fire_world.text1",
				"The fire world is, to no one's surprise, a very warm place. Instead of water, there is lava. Instead of grass, there is fire. But for adventurers brave enough to enter, there is also valuable treasures!");
		addGuide("fire_world.fire_ritual", "Fire Ritual");
		addGuide("fire_world.fire_ritual.text1",
				"The fire ritual is a structure that can be founds while exploring the fire world. It is made up of a single pillar of a rare type of stone, which will at random intervals convert certain blocks placed adjacent to the pillar.");
		addGuide("fire_world.fire_ritual.text2",
				"Another, forbidden and dangerous ritual exists. A blood crystallite can be used to coat a single fire ritual block with blood. If four fire ritual blocks are coated in blood, a terrible and deadly creature will spawn! But beware, as this will permanently destroy the ritual pillar...");
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
