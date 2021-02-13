package mod.vemerion.runeworld.capability;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.RunePortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class RuneTeleport implements INBTSerializable<CompoundNBT> {
	@CapabilityInject(RuneTeleport.class)
	public static final Capability<RuneTeleport> CAPABILITY = null;

	public static final int TELEPORT_DURATION = 20 * 5;

	private int timer;
	private boolean inPortal;
	private RegistryKey<World> destDim;

	public RuneTeleport() {

	}

	public static LazyOptional<RuneTeleport> getRuneTeleport(Entity entity) {
		return entity.getCapability(CAPABILITY);
	}

	public void setPortal(PlayerEntity player, World worldIn, RegistryKey<World> dimension) {
		if (player.isCreative()) {
			teleport(player, worldIn, dimension);
		} else {
			inPortal = true;
			destDim = dimension;
		}
	}

	public void tick(PlayerEntity player) {
		if (inPortal) {
			inPortal = false;
			if (timer++ > TELEPORT_DURATION) {
				timer = 0;
				teleport(player, player.world, destDim);
			}
		} else {
			timer = Math.max(0, timer - 1);
		}
	}

	public static void teleport(Entity entityIn, World worldIn, RegistryKey<World> dimension) {
		RegistryKey<World> other = worldIn.getDimensionKey() == dimension ? World.OVERWORLD : dimension;
		ServerWorld otherWorld = worldIn.getServer().getWorld(other);
		entityIn.changeDimension(otherWorld, new RunePortalBlock.RuneTeleporter());
	}

	public void deserializeNBT(CompoundNBT compound) {
	}

	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		return compound;
	}

	@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.FORGE)
	public static class RuneTeleportProvider implements ICapabilitySerializable<INBT> {

		private LazyOptional<RuneTeleport> instance = LazyOptional.of(CAPABILITY::getDefaultInstance);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return CAPABILITY.orEmpty(cap, instance);
		}

		@Override
		public INBT serializeNBT() {
			return CAPABILITY.getStorage().writeNBT(CAPABILITY,
					instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
		}

		@Override
		public void deserializeNBT(INBT nbt) {
			CAPABILITY.getStorage().readNBT(CAPABILITY,
					instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null,
					nbt);
		}

		public static final ResourceLocation LOCATION = new ResourceLocation(Main.MODID, "runeteleport");

		@SubscribeEvent
		public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof PlayerEntity)
				event.addCapability(LOCATION, new RuneTeleportProvider());
		}
	}

	public static class Storage implements IStorage<RuneTeleport> {

		@Override
		public INBT writeNBT(Capability<RuneTeleport> capability, RuneTeleport instance, Direction side) {
			return instance.serializeNBT();

		}

		@Override
		public void readNBT(Capability<RuneTeleport> capability, RuneTeleport instance, Direction side, INBT nbt) {
			instance.deserializeNBT((CompoundNBT) nbt);
		}
	}
}
