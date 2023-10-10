package toufoumaster.btwaila.mixin;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.EntityTrackerEntry;
import net.minecraft.core.net.NetworkManager;
import net.minecraft.core.net.packet.Packet140TileEntityData;
import net.minecraft.core.net.packet.Packet40EntityMetadata;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.EntityTracker;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import net.minecraft.server.world.WorldServer;
import org.checkerframework.checker.units.qual.C;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import toufoumaster.btwaila.BTWaila;
import toufoumaster.btwaila.INetServerHandler;
import toufoumaster.btwaila.network.packet.PacketEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestTileEntityData;

@Mixin(
        value = NetServerHandler.class,
        remap = false
)
public class NetServerHandlerMixin implements INetServerHandler {
    @Shadow private EntityPlayerMP playerEntity;

    //TODO: Future update need to send tileEntity data only once the data is different.
    @Override
    public void handleRequestTileEntityData(PacketRequestTileEntityData packet) {
        MinecraftServer server = MinecraftServer.getInstance();
        WorldServer worldserver = server.getWorldManager(this.playerEntity.dimension);
        TileEntity tileEntity = worldserver.getBlockTileEntity(packet.x, packet.y, packet.z);
        if (tileEntity != null) {
            Packet140TileEntityData newPacket = new Packet140TileEntityData(tileEntity);
            this.playerEntity.playerNetServerHandler.sendPacket(newPacket);
        }
    }

    //TODO: Future update need to send Entity data only once the data is different.
    @Override
    public void handleRequestEntityData(PacketRequestEntityData packet) {
        MinecraftServer server = MinecraftServer.getInstance();
        WorldServer worldserver = server.getWorldManager(this.playerEntity.dimension);
        Entity entity = worldserver.func_6158_a(packet.id);
        if (entity != null) {
            CompoundTag tag = new CompoundTag();
            ((EntityAccessor) entity).callAddAdditionalSaveData(tag);
            PacketEntityData newPacket = new PacketEntityData(packet.id, tag);
            this.playerEntity.playerNetServerHandler.sendPacket(newPacket);
        }
    }
}
