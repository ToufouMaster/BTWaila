package toufoumaster.btwaila.mixin.mixins;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.net.packet.Packet140TileEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import toufoumaster.btwaila.mixin.interfaces.INetServerHandler;
import toufoumaster.btwaila.network.packet.PacketEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestTileEntityData;

@Mixin(value = NetServerHandler.class, remap = false)
public class NetServerHandlerMixin implements INetServerHandler {
    @Shadow private EntityPlayerMP playerEntity;

    //TODO: Future update need to send tileEntity data only once the data is different.
    @Override
    public void bTWaila$handleRequestTileEntityData(PacketRequestTileEntityData packet) {
        MinecraftServer server = MinecraftServer.getInstance();
        WorldServer worldserver = server.getDimensionWorld(this.playerEntity.dimension);
        TileEntity tileEntity = worldserver.getBlockTileEntity(packet.x, packet.y, packet.z);
        if (tileEntity != null) {
            Packet140TileEntityData newPacket = new Packet140TileEntityData(tileEntity);
            this.playerEntity.playerNetServerHandler.sendPacket(newPacket);
        }
    }

    //TODO: Future update need to send Entity data only once the data is different.
    @Override
    public void bTWaila$handleRequestEntityData(PacketRequestEntityData packet) {
        MinecraftServer server = MinecraftServer.getInstance();
        WorldServer worldserver = server.getDimensionWorld(this.playerEntity.dimension);
        Entity entity = worldserver.func_6158_a(packet.id);
        if (entity != null) {
            CompoundTag tag = new CompoundTag();
            entity.addAdditionalSaveData(tag);
            PacketEntityData newPacket = new PacketEntityData(packet.id, tag);
            this.playerEntity.playerNetServerHandler.sendPacket(newPacket);
        }
    }
}
