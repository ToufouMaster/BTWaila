package toufoumaster.btwaila.mixin.interfaces;


import toufoumaster.btwaila.network.packet.PacketRequestEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestTileEntityData;

public interface INetServerHandler {
    void bTWaila$handleRequestTileEntityData(PacketRequestTileEntityData packet);
    void bTWaila$handleRequestEntityData(PacketRequestEntityData packet);
}