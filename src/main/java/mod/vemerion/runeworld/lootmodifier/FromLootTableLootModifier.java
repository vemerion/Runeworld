package mod.vemerion.runeworld.lootmodifier;

import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class FromLootTableLootModifier extends LootModifier {

	private ResourceLocation table;
	private boolean alreadyGenerated; // To avoid recursion

	public FromLootTableLootModifier(LootItemCondition[] conditionsIn, ResourceLocation table) {
		super(conditionsIn);
		this.table = table;
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		if (alreadyGenerated)
			return generatedLoot;
		alreadyGenerated = true;
		generatedLoot = context.getLevel().getServer().getLootTables().get(table)
				.getRandomItems(context);
		alreadyGenerated = false;
		return generatedLoot;
	}

	public static class Serializer extends GlobalLootModifierSerializer<FromLootTableLootModifier> {

		public Serializer() {
		}

		@Override
		public FromLootTableLootModifier read(ResourceLocation name, JsonObject object, LootItemCondition[] conditionsIn) {
			String table = GsonHelper.getAsString(object, "table");
			return new FromLootTableLootModifier(conditionsIn, new ResourceLocation(table));
		}

		@Override
		public JsonObject write(FromLootTableLootModifier instance) {
			JsonObject json = makeConditions(instance.conditions);
			json.addProperty("table", instance.table.toString());
			return json;
		}
	}
}