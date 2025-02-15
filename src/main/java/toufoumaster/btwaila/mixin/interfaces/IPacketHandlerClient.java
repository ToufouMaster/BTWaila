package toufoumaster.btwaila.mixin.interfaces;


import toufoumaster.btwaila.network.packet.PacketEntityData;

public interface IPacketHandlerClient {
    void bTWaila$handleEntityData(PacketEntityData packet);
}