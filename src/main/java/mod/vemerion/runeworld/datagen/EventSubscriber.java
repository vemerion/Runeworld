package mod.vemerion.runeworld.datagen;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import mod.vemerion.runeworld.Main;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(bus = Bus.MOD, modid = Main.MODID)
public class EventSubscriber {

	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event) {
		DataGenerator dataGenerator = event.getGenerator();

		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		if (event.includeClient()) {
			dataGenerator.addProvider(new ModLanguageProvider(dataGenerator));
			dataGenerator.addProvider(new ModBlockStateProvider(dataGenerator, existingFileHelper));
			dataGenerator.addProvider(new ModItemModelProvider(dataGenerator, existingFileHelper));
		}
		if (event.includeServer()) {
			BlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(dataGenerator, existingFileHelper);

			dataGenerator.addProvider(new ModRecipeProvider(dataGenerator));
			dataGenerator.addProvider(new ModLootModifierProvider(dataGenerator));
			dataGenerator.addProvider(new ModFluidTagsProvider(dataGenerator, existingFileHelper));
			dataGenerator.addProvider(blockTagsProvider);
			dataGenerator.addProvider(new ModItemTagsProvider(dataGenerator, blockTagsProvider, existingFileHelper));
			dataGenerator.addProvider(new ModBiomeTagsProvider(dataGenerator, blockTagsProvider, existingFileHelper));
			dataGenerator.addProvider(new LootTableProvider(dataGenerator) {
				@Override
				protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
					return ImmutableList.of(Pair.of(ModBlockLootTables::new, LootContextParamSets.BLOCK),
							Pair.of(ModEntityLootTables::new, LootContextParamSets.ENTITY),
							Pair.of(ModFishingLootTables::new, LootContextParamSets.FISHING),
							Pair.of(ModChestLootTables::new, LootContextParamSets.CHEST));
				};

				@Override
				protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
				}
			});
		}
	}
}
