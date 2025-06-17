package toufoumaster.btwaila.mixin.mixins;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.net.packet.PacketTileEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerServer;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import toufoumaster.btwaila.mixin.interfaces.IPacketHandlerServer;
import toufoumaster.btwaila.network.packet.PacketEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestTileEntityData;

@Mixin(value = PacketHandlerServer.class, remap = false)
public class PacketHandlerServerMixin implements IPacketHandlerServer {
    @Shadow private PlayerServer playerEntity;

    //TODO: Future update need to send tileEntity data only once the data is different.
    @Override
    public void bTWaila$handleRequestTileEntityData(PacketRequestTileEntityData packet) {
        MinecraftServer server = MinecraftServer.getInstance();
        WorldServer worldserver = server.getDimensionWorld(this.playerEntity.dimension);
        TileEntity tileEntity = worldserver.getTileEntity(packet.x, packet.y, packet.z);
        if (tileEntity != null) {
            PacketTileEntityData newPacket = new PacketTileEntityData(tileEntity);
            this.playerEntity.playerNetServerHandler.sendPacket(newPacket);
        }
    }

    //TODO: Future update need to send Entity data only once the data is different.
    @Override
    public void bTWaila$handleRequestEntityData(PacketRequestEntityData packet) {
        MinecraftServer server = MinecraftServer.getInstance();
        WorldServer worldserver = server.getDimensionWorld(this.playerEntity.dimension);
        Entity entity = worldserver.getEntityFromId(packet.id);
        if (entity != null) {
            CompoundTag tag = new CompoundTag();
            entity.addAdditionalSaveData(tag);
            PacketEntityData newPacket = new PacketEntityData(packet.id, tag);
            this.playerEntity.playerNetServerHandler.sendPacket(newPacket);
        }
    }
}
