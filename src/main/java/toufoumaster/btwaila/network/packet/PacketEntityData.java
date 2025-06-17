package toufoumaster.btwaila.network.packet;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.net.handler.PacketHandler;
import net.minecraft.core.net.packet.Packet;
import toufoumaster.btwaila.mixin.interfaces.IPacketHandlerClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketEntityData extends Packet {
    public int id;
    public CompoundTag tag;

    public PacketEntityData() {
        this.isChunkDataPacket = true;
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        this.id = dis.readInt();
        this.tag = readCompressedCompoundTag(dis);
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        dos.writeInt(this.id);
        writeCompressedCompoundTag(this.tag, dos);
    }

    @Override
    public void handlePacket(PacketHandler packetHandler) {
        ((IPacketHandlerClient)packetHandler).bTWaila$handleEntityData(this);
    }

    @Override
    public int getEstimatedSize() {
        return 12;
    }

    public PacketEntityData(int id, CompoundTag tag) {
        this();
        this.id = id;
        this.tag = tag;
    }
}
