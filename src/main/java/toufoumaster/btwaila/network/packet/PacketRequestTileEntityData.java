package toufoumaster.btwaila.network.packet;

import net.minecraft.core.net.handler.PacketHandler;
import net.minecraft.core.net.packet.Packet;
import toufoumaster.btwaila.mixin.interfaces.IPacketHandlerServer;

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

    @Override
    public void read(DataInputStream dis) throws IOException {
        this.x = dis.readInt();
        this.y = dis.readInt();
        this.z = dis.readInt();
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(this.x);
        dos.writeInt(this.y);
        dos.writeInt(this.z);
    }

    @Override
    public void handlePacket(PacketHandler packetHandler) {
        ((IPacketHandlerServer)packetHandler).bTWaila$handleRequestTileEntityData(this);
    }

    @Override
    public int getEstimatedSize() {
        return 12;
    }

    public PacketRequestTileEntityData(int x, int y, int z) {
        this();
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
