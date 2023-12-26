package toufoumaster.btwaila.network.packet;

import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;
import toufoumaster.btwaila.mixin.interfaces.INetServerHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketRequestTileEntityData extends Packet {
    public int x;
    public int y;
    public int z;

    public PacketRequestTileEntityData() {
        this.isChunkDataPacket = true;
    }

    public PacketRequestTileEntityData(int x, int y, int z) {
        this();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void readPacketData(DataInputStream dis) throws IOException {
        this.x = dis.readInt();
        this.y = dis.readInt();
        this.z = dis.readInt();
    }

    @Override
    public void writePacketData(DataOutputStream dos) throws IOException {
        dos.writeInt(this.x);
        dos.writeInt(this.y);
        dos.writeInt(this.z);
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        ((INetServerHandler)netHandler).bTWaila$handleRequestTileEntityData(this);
    }

    @Override
    public int getPacketSize() {
        return 12;
    }
}
