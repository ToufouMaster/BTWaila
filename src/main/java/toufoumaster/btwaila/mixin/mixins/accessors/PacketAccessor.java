package toufoumaster.btwaila.mixin.mixins.accessors;

import net.minecraft.core.net.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Packet.class, remap = false)
public interface PacketAccessor {
    @Invoker("addMapping")
    static void callAddMapping(int id, boolean isClientPacket, boolean isServerPacket, Class<? extends Packet> packetClass) {
        throw new UnsupportedOperationException();
    }
}
