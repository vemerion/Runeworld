package mod.vemerion.runeworld.init;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

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
import mod.vemerion.runeworld.block.FleshEatingPlantFlowerBlock;
import mod.vemerion.runeworld.block.FleshEatingPlantStemBlock;
import mod.vemerion.runeworld.block.RunePortalBlock;
import mod.vemerion.runeworld.block.TopazBlock;
import mod.vemerion.runeworld.block.complex.StoneMaterial;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);
	
	public static final RegistryObject<LiquidBlock> BLOOD = BLOCKS.register("blood", BloodBlock::new);
	public static final RegistryObject<Block> BLOOD_FLOWER = BLOCKS.register("blood_flower", BloodFlowerBlock::new);
	public static final RegistryObject<BloodPillarBlock> BLOOD_PILLAR_LARGE = BLOCKS.register("blood_pillar_large", () -> withItem(new BloodPillarBlock(BloodPillarBlock.LARGE)));
	public static final RegistryObject<BloodPillarBlock> BLOOD_PILLAR_MEDIUM = BLOCKS.register("blood_pillar_medium", () -> withItem(new BloodPillarBlock(BloodPillarBlock.MEDIUM)));
	public static final RegistryObject<BloodPillarBlock> BLOOD_PILLAR_SMALL = BLOCKS.register("blood_pillar_small", () -> withItem(new BloodPillarBlock(BloodPillarBlock.SMALL)));
	public static final RegistryObject<Block> HIDEABLE_BLOOD_ROCK = BLOCKS.register("hideable_blood_rock", () -> withItem(new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().strength(1.5f, 6).noOcclusion().noDrops())));
	public static final RegistryObject<Block> HIDEABLE_BLOOD_ROCK_BRICKS = BLOCKS.register("hideable_blood_rock_bricks", () -> withItem(new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLUE).strength(2, 6).noOcclusion().noDrops())));
	public static final RegistryObject<Block> BLOOD_MOSS = BLOCKS.register("blood_moss", () -> withItem(new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_RED).requiresCorrectToolForDrops().strength(2, 6))));
	public static final RegistryObject<Block> BLOOD_CRYSTAL = BLOCKS.register("blood_crystal", () -> withItem(new BloodCrystalBlock()));
	public static final RegistryObject<Block> BLOOD_LEECH = BLOCKS.register("blood_leech", () -> withItem(new BloodLeechBlock()));
	public static final RegistryObject<Block> FLESH_EATING_PLANT_STEM = BLOCKS.register("flesh_eating_plant_stem", () -> new FleshEatingPlantStemBlock());
	public static final RegistryObject<FleshEatingPlantFlowerBlock> FLESH_EATING_PLANT_FLOWER = BLOCKS.register("flesh_eating_plant_flower", () -> withItem(new FleshEatingPlantFlowerBlock()));
	public static final RegistryObject<Block> CHARRED_DIRT = BLOCKS.register("charred_dirt", () -> withItem(new CharredDirtBlock()));
	public static final RegistryObject<Block> BURNT_DIRT = BLOCKS.register("burnt_dirt", () -> withItem(new FireGroundBlock(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_ORANGE)
			.strength(0.6F).sound(SoundType.GRASS))));
	public static final RegistryObject<Block> FIRE_RITUAL_STONE = BLOCKS.register("fire_ritual_stone", () -> withItem(new FireRitualStoneBlock(), noFire()));
	public static final RegistryObject<Block> FIRE_ROOT = BLOCKS.register("fire_root", FireRootBlock::new);
	public static final RegistryObject<Block> TOPAZ = BLOCKS.register("topaz", () -> withItem(new TopazBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_ORANGE).requiresCorrectToolForDrops().strength(3, 3).noOcclusion().lightLevel(s -> 10)), noFire()));
	
	public static final RegistryObject<Block> BLOOD_ROCK = BLOCKS.register("blood_rock", () -> withItem(new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().strength(2, 6))));
	public static final RegistryObject<StairBlock> BLOOD_ROCK_STAIRS = BLOCKS.register("blood_rock_stairs", () -> withItem(new StairBlock(() -> BLOOD_ROCK.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().strength(2, 6))));
	public static final RegistryObject<SlabBlock> BLOOD_ROCK_SLAB = BLOCKS.register("blood_rock_slab", () -> withItem(new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().strength(2, 6))));
	public static final RegistryObject<WallBlock> BLOOD_ROCK_WALL = BLOCKS.register("blood_rock_wall", () -> withItem(new WallBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().strength(2, 6))));
	
	public static final RegistryObject<Block> BLOOD_ROCK_BRICKS = BLOCKS.register("blood_rock_bricks", () -> withItem(new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().strength(1.5f, 6))));
	public static final RegistryObject<StairBlock> BLOOD_ROCK_BRICK_STAIRS = BLOCKS.register("blood_rock_brick_stairs", () -> withItem(new StairBlock(() -> BLOOD_ROCK_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().strength(1.5f, 6))));
	public static final RegistryObject<SlabBlock> BLOOD_ROCK_BRICK_SLAB = BLOCKS.register("blood_rock_brick_slab", () -> withItem(new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().strength(1.5f, 6))));
	public static final RegistryObject<WallBlock> BLOOD_ROCK_BRICK_WALL = BLOCKS.register("blood_rock_brick_wall", () -> withItem(new WallBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_BLUE).requiresCorrectToolForDrops().strength(1.5f, 6))));

	
	public static final RegistryObject<Block> SPARKSTONE = BLOCKS.register("sparkstone", () -> withItem(new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6))));
	public static final RegistryObject<StairBlock> SPARKSTONE_STAIRS = BLOCKS.register("sparkstone_stairs", () -> withItem(new StairBlock(() -> SPARKSTONE.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6))));
	public static final RegistryObject<SlabBlock> SPARKSTONE_SLAB = BLOCKS.register("sparkstone_slab", () -> withItem(new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6))));
	public static final RegistryObject<WallBlock> SPARKSTONE_WALL = BLOCKS.register("sparkstone_wall", () -> withItem(new WallBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).requiresCorrectToolForDrops().strength(1.5f, 6))));

	public static final RegistryObject<Block> CHARRED_STONE = BLOCKS.register("charred_stone", () -> withItem(new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5f, 6))));
	public static final RegistryObject<StairBlock> CHARRED_STONE_STAIRS = BLOCKS.register("charred_stone_stairs", () -> withItem(new StairBlock(() -> CHARRED_STONE.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5f, 6))));
	public static final RegistryObject<SlabBlock> CHARRED_STONE_SLAB = BLOCKS.register("charred_stone_slab", () -> withItem(new SlabBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5f, 6))));
	public static final RegistryObject<WallBlock> CHARRED_STONE_WALL = BLOCKS.register("charred_stone_wall", () -> withItem(new WallBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5f, 6))));
	
	public static StoneMaterial SPARKSTONE_MATERIAL = new StoneMaterial(SPARKSTONE, SPARKSTONE_STAIRS, SPARKSTONE_SLAB, SPARKSTONE_WALL);
	public static StoneMaterial CHARRED_STONE_MATERIAL = new StoneMaterial(CHARRED_STONE, CHARRED_STONE_STAIRS, CHARRED_STONE_SLAB, CHARRED_STONE_WALL);
	public static StoneMaterial BLOOD_ROCK_MATERIAL = new StoneMaterial(BLOOD_ROCK, BLOOD_ROCK_STAIRS, BLOOD_ROCK_SLAB, BLOOD_ROCK_WALL);
	public static StoneMaterial BLOOD_ROCK_BRICKS_MATERIAL = new StoneMaterial(BLOOD_ROCK_BRICKS, BLOOD_ROCK_BRICK_STAIRS, BLOOD_ROCK_BRICK_SLAB, BLOOD_ROCK_BRICK_WALL, BLOOD_ROCK);
	
	public static final RegistryObject<Block> BLOOD_RUNE_PORTAL = BLOCKS.register("blood_rune_portal", () -> createRunePortal(ModDimensions.BLOOD, () -> Runesword.BLOOD_RUNE, 170, 0, 0));
	public static final RegistryObject<Block> FIRE_RUNE_PORTAL = BLOCKS.register("fire_rune_portal", () -> createRunePortal(ModDimensions.FIRE, () -> Runesword.FIRE_RUNE, 255, 100, 0));

	private static final Set<RunePortalBlock> RUNE_PORTALS = new HashSet<>();

	private static <T extends Block> T withItem(T block) {
		ModItems.addBlockWithItem(block);
		return block;
	}

	private static <T extends Block> T withItem(T block, Item.Properties properties) {
		ModItems.addBlockWithItem(block, properties);
		return block;
	}

	private static Item.Properties noFire() {
		return new Item.Properties().fireResistant();
	}

	private static RunePortalBlock createRunePortal(ResourceKey<Level> dimension, Supplier<Item> rune, int red,
			int green, int blue) {
		var portal = new RunePortalBlock(dimension, rune, red, green, blue);
		RUNE_PORTALS.add(portal);
		return portal;
	}

	public static Set<RunePortalBlock> getRunePortals() {
		return RUNE_PORTALS;
	}
}
