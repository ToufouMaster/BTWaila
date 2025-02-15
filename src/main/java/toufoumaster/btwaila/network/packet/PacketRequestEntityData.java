package toufoumaster.btwaila.network.packet;

import net.minecraft.core.net.handler.PacketHandler;
import net.minecraft.core.net.packet.Packet;
import toufoumaster.btwaila.mixin.interfaces.IPacketHandlerServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketRequestEntityData extends Packet {
    public int id;

    public PacketRequestEntityData() {
        this.isChunkDataPacket = true;
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        this.id = dis.readInt();
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(this.id);
    }

    @Override
    public void handlePacket(PacketHandler packetHandler) {
        ((IPacketHandlerServer)packetHandler).bTWaila$handleRequestEntityData(this);
    }

    @Override
    public int getEstimatedSize() {
        return 4;
    }

    public PacketRequestEntityData(int id) {
        this();
        this.id = id;
    }
}
