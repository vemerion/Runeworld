package mod.vemerion.runeworld.event;

import java.util.Map;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.biome.dimensionrenderer.BloodSpecialEffects;
import mod.vemerion.runeworld.biome.dimensionrenderer.FireSpecialEffects;
import mod.vemerion.runeworld.block.RunePortalBlock;
import mod.vemerion.runeworld.init.ModBlockEntities;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModDimensions;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModFluids;
import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.ModLayerLocations;
import mod.vemerion.runeworld.init.ModParticleTypes;
import mod.vemerion.runeworld.item.DislocatorItem;
import mod.vemerion.runeworld.model.BloodBatModel;
import mod.vemerion.runeworld.model.BloodLeechModel;
import mod.vemerion.runeworld.model.BloodMonkeyModel;
import mod.vemerion.runeworld.model.MosquitoModel;
import mod.vemerion.runeworld.particle.DrippingBloodProvider;
import mod.vemerion.runeworld.particle.RunePortalParticle;
import mod.vemerion.runeworld.renderer.BloodBatRenderer;
import mod.vemerion.runeworld.renderer.BloodLeechBlockEntityRenderer;
import mod.vemerion.runeworld.renderer.BloodMonkeyRenderer;
import mod.vemerion.runeworld.renderer.FireElementalProjectileRenderer;
import mod.vemerion.runeworld.renderer.FireElementalRenderer;
import mod.vemerion.runeworld.renderer.MosquitoRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
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

		mc.particleEngine.register(ModParticleTypes.DRIPPING_BLOOD, (s) -> new DrippingBloodProvider(s));
		mc.particleEngine.register(ModParticleTypes.RUNE_PORTAL, (s) -> new RunePortalParticle.Factory(s));

	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLOOD_FLOWER, RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.BLOOD_CRYSTAL, RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModBlocks.FIRE_ROOT, RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(ModFluids.BLOOD.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(ModFluids.BLOOD_FLOWING.get(), RenderType.translucent());

		for (Block portal : ModBlocks.getRunePortals())
			ItemBlockRenderTypes.setRenderLayer(portal, RenderType.translucent());

		event.enqueueWork(() -> registerItemProperties());
		event.enqueueWork(ClientModEventSubscriber::registerDimensionSpecialEffects);

	}
	
	private static void registerDimensionSpecialEffects() {
		try {
			@SuppressWarnings("unchecked")
			var effects = (Map<ResourceLocation, DimensionSpecialEffects>) ObfuscationReflectionHelper.findField(DimensionSpecialEffects.class, "f_108857_").get(null);
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
	}
	
	@SubscribeEvent
	public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(ModBlockEntities.BLOOD_LEECH, BloodLeechBlockEntityRenderer::new);
		
		event.registerEntityRenderer(ModEntities.MOSQUITO, MosquitoRenderer::new);
		event.registerEntityRenderer(ModEntities.BLOOD_BAT, BloodBatRenderer::new);
		event.registerEntityRenderer(ModEntities.BLOOD_MONKEY, BloodMonkeyRenderer::new);
		event.registerEntityRenderer(ModEntities.MOSQUITO_EGGS, ThrownItemRenderer::new);
		event.registerEntityRenderer(ModEntities.BLOOD_PEBBLE, ThrownItemRenderer::new);
		event.registerEntityRenderer(ModEntities.FIRE_ELEMENTAL, FireElementalRenderer::new);
		event.registerEntityRenderer(ModEntities.FIRE_ELEMENTAL_PROJECTILE, FireElementalProjectileRenderer::new);
	}

	private static void registerItemProperties() {
		registerDislocator(ModItems.BLOOD_DISLOCATOR);
		registerDislocator(ModItems.FIRE_DISLOCATOR);
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
		event.getItemColors().register((stack, color) -> ((SpawnEggItem) stack.getItem()).getColor(color),
				ModEntities.getSpawnEggs().toArray(new SpawnEggItem[0]));
	}

	@SubscribeEvent
	public static void onRegisterBlockColor(ColorHandlerEvent.Block event) {
		event.getBlockColors().register((state, reader, pos, color) -> ((RunePortalBlock) state.getBlock()).getColor(),
				ModBlocks.getRunePortals().toArray(new RunePortalBlock[0]));
	}
}
