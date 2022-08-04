package mod.vemerion.runeworld.capability;

import mod.vemerion.runeworld.Main;
import mod.vemerion.runeworld.block.RunePortalBlock;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class RuneTeleport implements INBTSerializable<CompoundTag> {
	
	public static final Capability<RuneTeleport> CAPABILITY = CapabilityManager.get(new CapabilityToken<RuneTeleport>() {
	});


	public static final int TELEPORT_DURATION = 20 * 5;

	private int timer;
	private boolean inPortal;
	private ResourceKey<Level> destDim;

	public RuneTeleport() {

	}

	public static LazyOptional<RuneTeleport> getRuneTeleport(Entity entity) {
		return entity.getCapability(CAPABILITY);
	}

	public void setPortal(Player player, Level worldIn, ResourceKey<Level> dimension) {
		if (player.isCreative()) {
			teleport(player, worldIn, dimension);
		} else {
			inPortal = true;
			destDim = dimension;
		}
	}

	public void tick(Player player) {
		if (inPortal) {
			inPortal = false;
			if (timer++ > TELEPORT_DURATION) {
				timer = 0;
				teleport(player, player.level, destDim);
			}
		} else {
			timer = Math.max(0, timer - 1);
		}
	}

	public static void teleport(Entity entityIn, Level worldIn, ResourceKey<Level> dimension) {
		ResourceKey<Level> other = worldIn.dimension() == dimension ? Level.OVERWORLD : dimension;
		ServerLevel otherWorld = worldIn.getServer().getLevel(other);
		entityIn.changeDimension(otherWorld, new RunePortalBlock.RuneTeleporter());
	}

	public void deserializeNBT(CompoundTag compound) {
	}

	public CompoundTag serializeNBT() {
		CompoundTag compound = new CompoundTag();
		return compound;
	}

	@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.FORGE)
	public static class RuneTeleportProvider implements ICapabilitySerializable<CompoundTag> {

		private LazyOptional<RuneTeleport> instance = LazyOptional.of(RuneTeleport::new);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return CAPABILITY.orEmpty(cap, instance);
		}

		@Override
		public CompoundTag serializeNBT() {
			return instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")).serializeNBT();
		}

		@Override
		public void deserializeNBT(CompoundTag nbt) {
			instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")).deserializeNBT(nbt);
		}

		public static final ResourceLocation LOCATION = new ResourceLocation(Main.MODID, "runeteleport");

		@SubscribeEvent
		public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player)
				event.addCapability(LOCATION, new RuneTeleportProvider());
		}
	}
}
