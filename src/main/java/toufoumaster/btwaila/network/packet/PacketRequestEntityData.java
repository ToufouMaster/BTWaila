package toufoumaster.btwaila.network.packet;

import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;
import toufoumaster.btwaila.mixin.interfaces.INetServerHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketRequestEntityData extends Packet {
    public int id;

    public PacketRequestEntityData() {
        this.isChunkDataPacket = true;
    }

    public PacketRequestEntityData(int id) {
        this();
        this.id = id;
    }

    @Override
    public void readPacketData(DataInputStream dis) throws IOException {
        this.id = dis.readInt();
    }

    @Override
    public void writePacketData(DataOutputStream dos) throws IOException {
        dos.writeInt(this.id);
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        ((INetServerHandler)netHandler).handleRequestEntityData(this);
    }

    @Override
    public int getPacketSize() {
        return 4;
    }
}
