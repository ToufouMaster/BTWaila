package toufoumaster.btwaila;


import net.minecraft.core.entity.Entity;
import org.spongepowered.asm.mixin.gen.Accessor;
import toufoumaster.btwaila.network.packet.PacketEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestTileEntityData;
import toufoumaster.btwaila.network.packet.PacketServerCheck;

public interface INetClientHandler {

    void handleEntityData(PacketEntityData packet);

    void handleServerCheck(PacketServerCheck packet);
}