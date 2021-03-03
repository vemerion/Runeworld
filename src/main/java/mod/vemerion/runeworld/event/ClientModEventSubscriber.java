package mod.vemerion.runeworld.event;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.RunePortalBlock;
import mod.vemerion.runeworld.init.ModBlocks;
import mod.vemerion.runeworld.init.ModDimensions;
import mod.vemerion.runeworld.init.ModEntities;
import mod.vemerion.runeworld.init.ModItems;
import mod.vemerion.runeworld.init.ModParticleTypes;
import mod.vemerion.runeworld.init.ModTileEntities;
import mod.vemerion.runeworld.particle.DrippingBloodFactory;
import mod.vemerion.runeworld.particle.RunePortalParticle;
import mod.vemerion.runeworld.renderer.BloodBatRenderer;
import mod.vemerion.runeworld.renderer.BloodLeechTileEntityRenderer;
import mod.vemerion.runeworld.renderer.BloodMonkeyRenderer;
import mod.vemerion.runeworld.renderer.MosquitoRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID, value = Dist.CLIENT)
public class ClientModEventSubscriber {

	@SubscribeEvent
	public static void onRegisterParticleFactory(ParticleFactoryRegisterEvent event) {
		Minecraft mc = Minecraft.getInstance();

		mc.particles.registerFactory(ModParticleTypes.DRIPPING_BLOOD, (s) -> new DrippingBloodFactory(s));
		mc.particles.registerFactory(ModParticleTypes.RUNE_PORTAL, (s) -> new RunePortalParticle.Factory(s));

	}

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(ModBlocks.BLOOD_FLOWER, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.BLOOD_CRYSTAL, RenderType.getCutout());

		ClientRegistry.bindTileEntityRenderer(ModTileEntities.BLOOD_LEECH, BloodLeechTileEntityRenderer::new);

		for (Block portal : ModBlocks.getRunePortals())
			RenderTypeLookup.setRenderLayer(portal, RenderType.getTranslucent());

		Minecraft mc = event.getMinecraftSupplier().get();

		registerEntityRenderers(mc);

		event.enqueueWork(() -> registerItemProperties());
	}

	private static void registerEntityRenderers(Minecraft mc) {
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.MOSQUITO, MosquitoRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.BLOOD_BAT, BloodBatRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.BLOOD_MONKEY, BloodMonkeyRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntities.MOSQUITO_EGGS,
				m -> new SpriteRenderer<>(m, mc.getItemRenderer()));
	}

	private static void registerItemProperties() {
		ItemModelsProperties.registerProperty(ModItems.BLOOD_DISLOCATOR, new ResourceLocation(Main.MODID, "dimension"),
				(stack, world, entity) -> {
					float property = 0.5f;
					if (world != null && world.getDimensionKey() == ModDimensions.BLOOD)
						property = 1;
					else if (entity != null && entity.world != null
							&& entity.world.getDimensionKey() == ModDimensions.BLOOD)
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
