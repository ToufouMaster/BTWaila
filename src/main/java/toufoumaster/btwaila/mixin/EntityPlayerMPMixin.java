package toufoumaster.btwaila.mixin;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.packet.Packet140TileEntityData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import toufoumaster.btwaila.IPlayerPacketData;

@Mixin(value = EntityPlayer.class,remap = false)
public class EntityPlayerMPMixin implements IPlayerPacketData {
    @Unique
    private Packet140TileEntityData prevTEntityData;
    @Unique
    public Packet140TileEntityData getPreviousTileEntityData() {
        return prevTEntityData;
    }
    @Unique
    public void setPreviousTileEntityData(Packet140TileEntityData packet) {
        prevTEntityData = packet;
    }
}
