package toufoumaster.btwaila.network.packet;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;
import toufoumaster.btwaila.mixin.interfaces.INetClientHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketEntityData extends Packet {
    public int id;
    public CompoundTag tag;

    public PacketEntityData() {
        this.isChunkDataPacket = true;
    }

    public PacketEntityData(int id, CompoundTag tag) {
        this();
        this.id = id;
        this.tag = tag;
    }

    @Override
    public void readPacketData(DataInputStream dis) throws IOException {
        this.id = dis.readInt();
        this.tag = readCompressedCompoundTag(dis);
    }

    @Override
    public void writePacketData(DataOutputStream dos) throws IOException {
        dos.writeInt(this.id);
        writeCompressedCompoundTag(this.tag, dos);
    }

    @Override
    public void processPacket(NetHandler netHandler) {
        ((INetClientHandler)netHandler).handleEntityData(this);
    }

    @Override
    public int getPacketSize() {
        return 12;
    }
}
