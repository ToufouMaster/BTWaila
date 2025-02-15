package toufoumaster.btwaila.mixin.mixins;

import net.minecraft.client.net.handler.PacketHandlerClient;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.net.packet.PacketCustomPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toufoumaster.btwaila.mixin.interfaces.IPacketHandlerClient;
import toufoumaster.btwaila.network.packet.PacketEntityData;
import toufoumaster.btwaila.util.VersionHelper;

@Mixin(value = PacketHandlerClient.class, remap = false)
public abstract class PacketHandlerClientMixin implements IPacketHandlerClient {
    @Shadow protected abstract Entity getEntityByID(int i);

    @Override
    public void bTWaila$handleEntityData(PacketEntityData packet) {
        Entity entity = getEntityByID(packet.id);
        if (entity != null) {
            if (entity instanceof Mob){
                byte skinVar = (byte) ((Mob) entity).getSkinVariant();
                packet.tag.putByte("SkinVariant", skinVar);
            }
            entity.readAdditionalSaveData(packet.tag);
        }
    }

    @Inject( method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
    public void BTWaila$handleCustomPayload(PacketCustomPayload packet, CallbackInfo ci){
        if (packet.channel.equals("BTWaila|VersionCheck")){
            VersionHelper.handlePacket(packet);
            ci.cancel();
        }
    }
}
