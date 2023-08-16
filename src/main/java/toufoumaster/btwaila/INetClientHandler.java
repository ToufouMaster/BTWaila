package toufoumaster.btwaila;


import toufoumaster.btwaila.network.packet.PacketEntityData;

public interface INetClientHandler {

    void handleEntityData(PacketEntityData packet);
}