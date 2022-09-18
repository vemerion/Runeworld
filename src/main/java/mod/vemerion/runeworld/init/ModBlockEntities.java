package mod.vemerion.runeworld.init;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.blockentity.BloodLeechBlockEntity;
import mod.vemerion.runeworld.blockentity.ChaliceBlockEntity;
import mod.vemerion.runeworld.blockentity.MirrorBlockEntity;
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
	public static final RegistryObject<BlockEntityType<MirrorBlockEntity>> MIRROR = BLOCK_ENTITIES.register("mirror",
			() -> BlockEntityType.Builder.of(MirrorBlockEntity::new, ModBlocks.MIRROR.get()).build(null));
	public static final RegistryObject<BlockEntityType<ChaliceBlockEntity>> CHALICE = BLOCK_ENTITIES.register("chalice",
			() -> BlockEntityType.Builder.of(ChaliceBlockEntity::new, ModBlocks.CHALICE.get()).build(null));
}
