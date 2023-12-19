package toufoumaster.btwaila.mixin.interfaces;


import toufoumaster.btwaila.network.packet.PacketEntityData;

public interface INetClientHandler {
    void handleEntityData(PacketEntityData packet);
}