package toufoumaster.btwaila.mixin.mixins;

import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.net.packet.Packet3Chat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toufoumaster.btwaila.BTWaila;
import toufoumaster.btwaila.mixin.interfaces.INetClientHandler;
import toufoumaster.btwaila.network.packet.PacketEntityData;
import toufoumaster.btwaila.util.VersionHelper;

@Mixin(value = NetClientHandler.class, remap = false)
public abstract class NetClientHandlerMixin implements INetClientHandler {
    @Shadow protected abstract Entity getEntityByID(int i);

    @Override
    public void bTWaila$handleEntityData(PacketEntityData packet) {
        Entity entity = getEntityByID(packet.id);
        if (entity != null) {
            if (entity instanceof EntityLiving){
                byte skinVar = (byte) ((EntityLiving) entity).getSkinVariant();
                packet.tag.putByte("SkinVariant", skinVar);
            }
            entity.readAdditionalSaveData(packet.tag);
        }
    }


    @Inject( method = "handleChat", at = @At("HEAD"), cancellable = true)
    public void handleChat(Packet3Chat packet3chat, CallbackInfo ci) {
        if (!packet3chat.encrypted) {
            String message = packet3chat.message;
            VersionHelper version = VersionHelper.getModVersionBasedOnString(message);
            if (version != null) {
                if (VersionHelper.checkModVersion(version)) {
                    BTWaila.canUseAdvancedTooltips = true;
                }
                ci.cancel();
            }
        }
    }
}
