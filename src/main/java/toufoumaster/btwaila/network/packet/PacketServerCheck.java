package toufoumaster.btwaila.network.packet;

import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;
import toufoumaster.btwaila.INetClientHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketServerCheck extends Packet {

    public PacketServerCheck() {
        this.isChunkDataPacket = true;
    }

    public PacketServerCheck(int x, int y, int z) {
        this();
    }

    @Override
    public void readPacketData(DataInputStream dis) throws IOException {
    }

    @Override
    public void writePacketData(DataOutputStream dos) throws IOException {
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        ((INetClientHandler)netHandler).handleServerCheck(this);
    }

    @Override
    public int getPacketSize() {
        return 12;
    }
}
