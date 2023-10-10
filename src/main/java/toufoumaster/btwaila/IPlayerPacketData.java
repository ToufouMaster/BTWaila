package toufoumaster.btwaila;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.net.packet.Packet140TileEntityData;

public interface IPlayerPacketData {
    Packet140TileEntityData getPreviousTileEntityData();
    void setPreviousTileEntityData(Packet140TileEntityData packet);
}
