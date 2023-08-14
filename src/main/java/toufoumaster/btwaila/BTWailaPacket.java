package toufoumaster.btwaila;

import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class BTWailaPacket extends Packet {

    @Override
    public void readPacketData(DataInputStream dataInputStream) throws IOException {

    }

    @Override
    public void writePacketData(DataOutputStream dataOutputStream) throws IOException {

    }

    @Override
    public void processPacket(NetHandler netHandler) {
    }

    @Override
    public int getPacketSize() {
        return 0;
    }

    static {
    }
}
