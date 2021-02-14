package mod.vemerion.runeworld.lootmodifier;

import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class FromLootTableLootModifier extends LootModifier {

	private ResourceLocation table;
	private boolean alreadyGenerated; // To avoid recursion

	public FromLootTableLootModifier(ILootCondition[] conditionsIn, ResourceLocation table) {
		super(conditionsIn);
		this.table = table;
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		if (alreadyGenerated)
			return generatedLoot;
		alreadyGenerated = true;
//		if (context.has(LootParameters.TOOL))
//			System.out.println(context.get(LootParameters.TOOL).toString());
//		if (context.has(LootParameters.BLOCK_STATE))
//			System.out.println(context.get(LootParameters.BLOCK_STATE).toString());
//		if (context.has(LootParameters.field_237457_g_))
//			System.out.println(context.getWorld()
//					.getBlockState(new BlockPos(context.get(LootParameters.field_237457_g_))).toString());
//		System.out.println("THIS" + context.getWorld().getServer().getLootTableManager().getLootTableFromLocation(table));
		generatedLoot = context.getWorld().getServer().getLootTableManager().getLootTableFromLocation(table)
				.generate(context);
		alreadyGenerated = false;
		return generatedLoot;
	}

	public static class Serializer extends GlobalLootModifierSerializer<FromLootTableLootModifier> {

		public Serializer() {
		}

		@Override
		public FromLootTableLootModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
			String table = JSONUtils.getString(object, "table");
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