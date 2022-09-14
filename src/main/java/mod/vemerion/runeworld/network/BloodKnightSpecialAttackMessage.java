package mod.vemerion.runeworld.network;

import java.util.function.Supplier;

import mod.vemerion.runeworld.entity.BloodKnightEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.DistExecutor.SafeRunnable;
import net.minecraftforge.network.NetworkEvent;

public class BloodKnightSpecialAttackMessage {

	private int id;

	public BloodKnightSpecialAttackMessage(int id) {
		this.id = id;
	}

	public void encode(final FriendlyByteBuf buffer) {
		buffer.writeInt(id);
	}

	public static BloodKnightSpecialAttackMessage decode(final FriendlyByteBuf buffer) {
		return new BloodKnightSpecialAttackMessage(buffer.readInt());
	}

	public void handle(final Supplier<NetworkEvent.Context> supplier) {
		final NetworkEvent.Context context = supplier.get();
		context.setPacketHandled(true);
		context.enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> Handle.specialAttack(id)));
	}

	private static class Handle {
		private static SafeRunnable specialAttack(int id) {
			return new SafeRunnable() {
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					var mc = Minecraft.getInstance();
					var level = mc.level;
					if (level != null) {
						var e = level.getEntity(id);
						if (e instanceof BloodKnightEntity knight)
							knight.startSpecialAttack();
					}
				}
			};
		}
	}
}
