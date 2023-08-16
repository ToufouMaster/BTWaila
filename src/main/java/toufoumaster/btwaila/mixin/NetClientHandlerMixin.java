package toufoumaster.btwaila.mixin;

import com.mojang.nbt.CompoundTag;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.net.packet.Packet3Chat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import toufoumaster.btwaila.BTWaila;
import toufoumaster.btwaila.INetClientHandler;
import toufoumaster.btwaila.network.packet.PacketEntityData;

@Mixin(
        value = NetClientHandler.class,
        remap = false
)
public abstract class NetClientHandlerMixin implements INetClientHandler {

    @Shadow protected abstract Entity getEntityByID(int i);

    @Override
    public void handleEntityData(PacketEntityData packet) {
        EntityLiving entity = (EntityLiving) getEntityByID(packet.id);
        if (entity != null) {
            CompoundTag entityTag = new CompoundTag();
            entity.addAdditionalSaveData(entityTag);
            // TODO: find a way to avoid using CompoundTag to get SkinVariant
            packet.tag.putByte("SkinVariant", entityTag.getByte("SkinVariant"));
            entity.readAdditionalSaveData(packet.tag);
        }
    }


    @Inject( method = "handleChat", at = @At("HEAD"), cancellable = true)
    public void handleChat(Packet3Chat packet3chat, CallbackInfo ci) {
        if (!packet3chat.encrypted && packet3chat.message.equals(BTWaila.checkString)) {
            BTWaila.canUseAdvancedTooltips = true;
            ci.cancel();
        }
    }
}
