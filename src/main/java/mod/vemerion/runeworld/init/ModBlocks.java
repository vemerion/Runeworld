package mod.vemerion.runeworld.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.BloodBlock;
import mod.vemerion.runeworld.block.BloodCrystalBlock;
import mod.vemerion.runeworld.block.BloodFlowerBlock;
import mod.vemerion.runeworld.block.BloodLeechBlock;
import mod.vemerion.runeworld.block.BloodPillarBlock;
import mod.vemerion.runeworld.block.CharredDirtBlock;
import mod.vemerion.runeworld.block.FireGroundBlock;
import mod.vemerion.runeworld.block.FireRitualStoneBlock;
import mod.vemerion.runeworld.block.FireRootBlock;
import mod.vemerion.runeworld.block.RunePortalBlock;
import mod.vemerion.runeworld.block.complex.StoneMaterial;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(value = Main.MODID)
@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class ModBlocks {

	public static final LiquidBlock BLOOD = null;
	public static final Block BLOOD_FLOWER = null;
	public static final BloodPillarBlock BLOOD_PILLAR_LARGE = null;
	public static final BloodPillarBlock BLOOD_PILLAR_MEDIUM = null;
	public static final BloodPillarBlock BLOOD_PILLAR_SMALL = null;
	public static final Block BLOOD_ROCK = null;
	public static final Block HIDEABLE_BLOOD_ROCK = null;
	public static final Block BLOOD_MOSS = null;
	public static final Block BLOOD_CRYSTAL = null;
	public static final Block BLOOD_LEECH = null;
	public static final Block CHARRED_DIRT = null;
	public static final Block BURNT_DIRT = null;
	public static final Block FIRE_RITUAL_STONE = null;
	public static final Block FIRE_ROOT = null;

	// Complex
	public static StoneMaterial SPARKSTONE;
	public static StoneMaterial CHARRED_STONE;

	public static final Block BLOOD_RUNE_PORTAL = null;
	public static final Block FIRE_RUNE_PORTAL = null;

	private static final List<RunePortalBlock> RUNE_PORTALS = new ArrayList<>();

	@SubscribeEvent
	public static void onRegisterBlock(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();

		registry.register(Init.setup(new BloodBlock(), "blood"));
		registry.register(Init.setup(new BloodFlowerBlock(), "blood_flower"));
		registry.register(withItem(Init.setup(new BloodPillarBlock(BloodPillarBlock.LARGE), "blood_pillar_large")));
		registry.register(withItem(Init.setup(new BloodPillarBlock(BloodPillarBlock.MEDIUM), "blood_pillar_medium")));
		registry.register(withItem(Init.setup(new BloodPillarBlock(BloodPillarBlock.SMALL), "blood_pillar_small")));
		registry.register(
				withItem(Init.setup(new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)), "blood_rock")));
		registry.register(
				withItem(Init.setup(new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).noOcclusion().noDrops()), "hideable_blood_rock")));
		registry.register(
				withItem(Init.setup(new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)), "blood_moss")));
		registry.register(withItem(Init.setup(new BloodCrystalBlock(), "blood_crystal")));
		registry.register(withItem(Init.setup(new BloodLeechBlock(), "blood_leech")));
		registry.register(withItem(Init.setup(new CharredDirtBlock(), "charred_dirt")));
		registry.register(withItem(
				Init.setup(new FireGroundBlock(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_ORANGE)
						.strength(0.6F).sound(SoundType.GRASS)), "burnt_dirt")));
		registry.register(withItem(Init.setup(new FireRitualStoneBlock(), "fire_ritual_stone"), noFire()));
		registry.register(Init.setup(new FireRootBlock(), "fire_root"));

		createRunePortal(ModDimensions.BLOOD, () -> Runesword.BLOOD_RUNE, 170, 0, 0);
		createRunePortal(ModDimensions.FIRE, () -> Runesword.FIRE_RUNE, 255, 100, 0);
		registry.registerAll(getRunePortals().toArray(new RunePortalBlock[0]));

		// Complex
		SPARKSTONE = new StoneMaterial("sparkstone", MaterialColor.STONE);
		CHARRED_STONE = new StoneMaterial("charred_stone", MaterialColor.COLOR_BLACK);
		stoneMaterial(SPARKSTONE, registry);
		stoneMaterial(CHARRED_STONE, registry);
	}

	private static void stoneMaterial(StoneMaterial material, IForgeRegistry<Block> registry) {
		for (Block b : material.getBlocks())
			registry.register(withItem(b));
	}

	private static Block withItem(Block block) {
		ModItems.addBlockWithItem(block);
		return block;
	}

	private static Block withItem(Block block, Item.Properties properties) {
		ModItems.addBlockWithItem(block, properties);
		return block;
	}

	private static Item.Properties noFire() {
		return new Item.Properties().fireResistant();
	}

	private static RunePortalBlock createRunePortal(ResourceKey<Level> dimension, Supplier<Item> rune, int red,
			int green, int blue) {
		RunePortalBlock portal = Init.setup(new RunePortalBlock(dimension, rune, red, green, blue),
				dimension.location().getPath() + "_rune_portal");
		RUNE_PORTALS.add(portal);
		return portal;
	}

	public static ImmutableList<RunePortalBlock> getRunePortals() {
		return ImmutableList.copyOf(RUNE_PORTALS);
	}
}
