package mod.vemerion.runeworld.event;

import java.util.Map;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.biome.dimensionrenderer.BloodSpecialEffects;
import mod.vemerion.runeworld.biome.dimensionrenderer.FireSpecialEffects;
import mod.vemerion.runeworld.block.RunePortalBlock;
import mod.vemerion.runeworld.block.TopazBlock;
import mod.vemerion.runeworld.init.ModBlockEntities;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModDimensions;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.ModLayerLocations;
import mod.vemerion.runeworld.init.ModParticles;
import mod.vemerion.runeworld.item.DislocatorItem;
import mod.vemerion.runeworld.model.BloodBatModel;
import mod.vemerion.runeworld.model.BloodCrownModel;
import mod.vemerion.runeworld.model.BloodGorillaModel;
import mod.vemerion.runeworld.model.BloodKnightModel;
import mod.vemerion.runeworld.model.BloodLeechModel;
import mod.vemerion.runeworld.model.BloodMonkeyModel;
import mod.vemerion.runeworld.model.MosquitoModel;
import mod.vemerion.runeworld.model.TickModel;
import mod.vemerion.runeworld.model.TopazCreatureModel;
import mod.vemerion.runeworld.particle.BloodDropParticle;
import mod.vemerion.runeworld.particle.BloodSplashParticle;
import mod.vemerion.runeworld.particle.DrippingBloodProvider;
import mod.vemerion.runeworld.particle.RunePortalParticle;
import mod.vemerion.runeworld.renderer.BloodBatRenderer;
import mod.vemerion.runeworld.renderer.BloodGorillaRenderer;
import mod.vemerion.runeworld.renderer.BloodKnightRenderer;
import mod.vemerion.runeworld.renderer.BloodLeechBlockEntityRenderer;
import mod.vemerion.runeworld.renderer.BloodMonkeyRenderer;
import mod.vemerion.runeworld.renderer.FireElementalProjectileRenderer;
import mod.vemerion.runeworld.renderer.FireElementalRenderer;
import mod.vemerion.runeworld.renderer.MirrorBlockEntityRenderer;
import mod.vemerion.runeworld.renderer.MosquitoRenderer;
import mod.vemerion.runeworld.renderer.TickRenderer;
import mod.vemerion.runeworld.renderer.TopazCreatureRenderer;
import mod.vemerion.runeworld.renderer.textureatlas.HideableTextureAtlasSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID, value = Dist.CLIENT)
public class ClientModEventSubscriber {

	@SubscribeEvent
	public static void onRegisterParticleFactory(ParticleFactoryRegisterEvent event) {
		Minecraft mc = Minecraft.getInstance();

		mc.particleEngine.register(ModParticles.DRIPPING_BLOOD.get(), (s) -> new DrippingBloodProvider(s));
		mc.particleEngine.register(ModParticles.RUNE_PORTAL.get(), (s) -> new RunePortalParticle.Provider(s));
		mc.particleEngine.register(ModParticles.BLOOD_DROP.get(), (s) -> new BloodDropParticle.Provider(s));
		mc.particleEngine.register(ModParticles.BLOOD_SPLASH.get(), (s) -> new BloodSplashParticle.Provider(s));
	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLOOD_FLOWER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLOOD_CRYSTAL.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.FIRE_ROOT.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.FLESH_EATING_PLANT_STEM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.FLESH_EATING_PLANT_FLOWER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.HIDEABLE_BLOOD_ROCK.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.HIDEABLE_BLOOD_ROCK_BRICKS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.TOPAZ.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(ModFluids.BLOOD.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(ModFluids.BLOOD_FLOWING.get(), RenderType.translucent());

		for (Block portal : ModBlocks.getRunePortals())
			ItemBlockRenderTypes.setRenderLayer(portal, RenderType.translucent());

		event.enqueueWork(() -> registerItemProperties());
		event.enqueueWork(ClientModEventSubscriber::registerDimensionSpecialEffects);

		MinecraftForgeClient.registerTextureAtlasSpriteLoader(new ResourceLocation(Main.MODID, "hideable_tas_loader"),
				new HideableTextureAtlasSprite.Loader());
	}

	private static void registerDimensionSpecialEffects() {
		try {
			@SuppressWarnings("unchecked")
			var effects = (Map<ResourceLocation, DimensionSpecialEffects>) ObfuscationReflectionHelper
					.findField(DimensionSpecialEffects.class, "f_108857_").get(null);
			effects.put(ModDimensions.BLOOD.location(), new BloodSpecialEffects());
			effects.put(ModDimensions.FIRE.location(), new FireSpecialEffects());
		} catch (Exception e) {
			Main.LOGGER.warn("Unable to register dimension special effects " + e);
		}
	}

	@SubscribeEvent
	public static void onRegisterEntityRendererLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(ModLayerLocations.BLOOD_BAT, BloodBatModel::createLayer);
		event.registerLayerDefinition(ModLayerLocations.BLOOD_LEECH, BloodLeechModel::createLayer);
		event.registerLayerDefinition(ModLayerLocations.BLOOD_MONKEY, BloodMonkeyModel::createLayer);
		event.registerLayerDefinition(ModLayerLocations.MOSQUITO, MosquitoModel::createLayer);
		event.registerLayerDefinition(ModLayerLocations.BLOOD_GORILLA, BloodGorillaModel::createBodyLayer);
		event.registerLayerDefinition(ModLayerLocations.BLOOD_CROWN, BloodCrownModel::createBodyLayer);
		event.registerLayerDefinition(ModLayerLocations.TICK, TickModel::createBodyLayer);
		event.registerLayerDefinition(ModLayerLocations.TOPAZ_CREATURE, TopazCreatureModel::createBodyLayer);
		event.registerLayerDefinition(ModLayerLocations.BLOOD_KNIGHT, BloodKnightModel::createBodyLayer);
	}

	@SubscribeEvent
	public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ModBlockEntities.BLOOD_LEECH.get(), BloodLeechBlockEntityRenderer::new);
		event.registerBlockEntityRenderer(ModBlockEntities.MIRROR.get(), MirrorBlockEntityRenderer::new);

		event.registerEntityRenderer(ModEntities.MOSQUITO.get(), MosquitoRenderer::new);
		event.registerEntityRenderer(ModEntities.BLOOD_BAT.get(), BloodBatRenderer::new);
		event.registerEntityRenderer(ModEntities.BLOOD_MONKEY.get(), BloodMonkeyRenderer::new);
		event.registerEntityRenderer(ModEntities.MOSQUITO_EGGS.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(ModEntities.SLINGSHOT_PROJECTILE.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(ModEntities.FIRE_ELEMENTAL.get(), FireElementalRenderer::new);
		event.registerEntityRenderer(ModEntities.FIRE_ELEMENTAL_PROJECTILE.get(), FireElementalProjectileRenderer::new);
		event.registerEntityRenderer(ModEntities.BLOOD_GORILLA.get(), BloodGorillaRenderer::new);
		event.registerEntityRenderer(ModEntities.TICK.get(), TickRenderer::new);
		event.registerEntityRenderer(ModEntities.TOPAZ_CREATURE.get(), TopazCreatureRenderer::new);
		event.registerEntityRenderer(ModEntities.BLOOD_KNIGHT_CLUB.get(), BloodKnightRenderer::new);
		event.registerEntityRenderer(ModEntities.BLOOD_KNIGHT_SPEAR.get(), BloodKnightRenderer::new);
	}

	private static void registerItemProperties() {
		registerDislocator(ModItems.BLOOD_DISLOCATOR.get());
		registerDislocator(ModItems.FIRE_DISLOCATOR.get());
		ItemProperties.register(ModItems.SLINGSHOT.get(), new ResourceLocation(Main.MODID, "shooting"),
				(stack, level, entity, seed) -> {
					return entity == null || entity.getUseItem() != stack ? 0 : 0.5f;
				});
	}

	private static void registerDislocator(DislocatorItem dislocator) {
		ItemProperties.register(dislocator, new ResourceLocation(Main.MODID, "dimension"),
				(stack, level, entity, seed) -> {
					float property = 0.5f;
					if (level != null && level.dimension() == dislocator.getDimension())
						property = 1;
					else if (entity != null && entity.level != null
							&& entity.level.dimension() == dislocator.getDimension())
						property = 1;

					return property;
				});
	}

	@SubscribeEvent
	public static void onRegisterItemColor(ColorHandlerEvent.Item event) {
		var colors = event.getItemColors();
		colors.register((stack, tint) -> TopazBlock.getColor(null, tint), ModBlocks.TOPAZ.get(),
				ModItems.TOPAZ_GEM.get(), ModItems.TOPAZ_SHARD.get(), ModBlocks.MIRROR.get());
		colors.register((stack, tint) -> tint > 0 ? -1 : TopazBlock.getColor(null, tint), ModItems.HAND_MIRROR.get());
	}

	@SubscribeEvent
	public static void onRegisterBlockColor(ColorHandlerEvent.Block event) {
		event.getBlockColors().register((state, reader, pos, tint) -> ((RunePortalBlock) state.getBlock()).getColor(),
				ModBlocks.getRunePortals().toArray(new RunePortalBlock[0]));
		event.getBlockColors().register((state, reader, pos, tint) -> TopazBlock.getColor(pos, tint),
				ModBlocks.TOPAZ.get(), ModBlocks.MIRROR.get());
	}
}
