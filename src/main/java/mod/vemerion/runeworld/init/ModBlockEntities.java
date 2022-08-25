package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.blockentity.BloodLeechBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
			.create(ForgeRegistries.BLOCK_ENTITIES, Main.MODID);

	public static final RegistryObject<BlockEntityType<BloodLeechBlockEntity>> BLOOD_LEECH = BLOCK_ENTITIES.register(
			"blood_leech",
			() -> BlockEntityType.Builder.of(BloodLeechBlockEntity::new, ModBlocks.BLOOD_LEECH.get()).build(null));
}
