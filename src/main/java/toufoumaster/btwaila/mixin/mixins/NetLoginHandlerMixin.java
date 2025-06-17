package toufoumaster.btwaila.mixin.mixins;

import net.minecraft.core.net.NetworkManager;
import net.minecraft.core.net.packet.PacketLogin;
import net.minecraft.server.net.handler.PacketHandlerLogin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toufoumaster.btwaila.BTWaila;

@Mixin(
        value = PacketHandlerLogin.class,
        remap = false
)
public class NetLoginHandlerMixin {

    @Shadow public NetworkManager netManager;

    @Inject( method = "doLogin", at = @At("HEAD"))
    public void doLogin(PacketLogin packet1login, CallbackInfo ci) {
        this.netManager.addToSendQueue(BTWaila.modVersion.getPacket());
    }
}
