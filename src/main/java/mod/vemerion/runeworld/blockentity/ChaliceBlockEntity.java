package mod.vemerion.runeworld.blockentity;

import mod.vemerion.runeworld.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

public class ChaliceBlockEntity extends BlockEntity {

	private Fluid fluid = Fluids.EMPTY;

	public ChaliceBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
		super(ModBlockEntities.CHALICE.get(), pWorldPosition, pBlockState);
	}

	public void putFluid(Fluid fluid) {
		this.fluid = fluid;
		setChanged();
	}

	public Fluid getFluid() {
		return fluid;
	}

	@Override
	protected void saveAdditional(CompoundTag pTag) {
		super.saveAdditional(pTag);
		pTag.putString("fluid", fluid.getRegistryName().toString());
	}

	@Override
	public void load(CompoundTag pTag) {
		super.load(pTag);
		if (pTag.contains("fluid"))
			fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(pTag.getString("fluid")));
	}

	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		load(pkt.getTag());
	}

	@Override
	public CompoundTag getUpdateTag() {
		return saveWithoutMetadata();
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		load(tag);
	}
}
