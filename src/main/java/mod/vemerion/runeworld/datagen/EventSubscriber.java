package mod.vemerion.runeworld.datagen;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import mod.vemerion.runeworld.Main;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

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
			dataGenerator.addProvider(new ModRecipeProvider(dataGenerator));
			dataGenerator.addProvider(new LootTableProvider(dataGenerator) {
				@Override
				protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootParameterSet>> getTables() {
					return ImmutableList.of(Pair.of(ModBlockLootTables::new, LootParameterSets.BLOCK),
							Pair.of(ModEntityLootTables::new, LootParameterSets.ENTITY));
				};

				@Override
				protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
				}
			});
		}
	}
}
