package mod.vemerion.runeworld.init;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import mod.vemerion.runeworld.Main;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTemplatePools {
	
	public static final DeferredRegister<StructureTemplatePool> TEMPLATE_POOLS = DeferredRegister.create(Registry.TEMPLATE_POOL_REGISTRY, Main.MODID);

	public static final RegistryObject<StructureTemplatePool> BLOOD_MONKEY_TUNNELS_START = TEMPLATE_POOLS.register("blood_monkey_tunnels_start", () -> new StructureTemplatePool(new ResourceLocation(Main.MODID, "blood_monkey_tunnels"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(StructurePoolElement.single(new ResourceLocation(Main.MODID, "blood_monkey_tunnels/throne_room").toString()), 1)), StructureTemplatePool.Projection.RIGID));

}
