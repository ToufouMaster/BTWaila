package toufoumaster.btwaila.mixin.interfaces;


import toufoumaster.btwaila.network.packet.PacketRequestEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestTileEntityData;

public interface INetServerHandler {
    void handleRequestTileEntityData(PacketRequestTileEntityData packet);
    void handleRequestEntityData(PacketRequestEntityData packet);
}