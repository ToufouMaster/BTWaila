package toufoumaster.btwaila.mixin;

import net.minecraft.core.net.NetworkManager;
import net.minecraft.core.net.packet.Packet1Login;
import net.minecraft.core.net.packet.Packet3Chat;
import net.minecraft.server.net.handler.NetLoginHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toufoumaster.btwaila.BTWaila;

@Mixin(
        value = NetLoginHandler.class,
        remap = false
)
public class NetLoginHandlerMixin {

    @Shadow public NetworkManager netManager;

    @Inject( method = "doLogin", at = @At("HEAD"))
    public void doLogin(Packet1Login packet1login, CallbackInfo ci) {
        this.netManager.addToSendQueue(new Packet3Chat(BTWaila.checkString));
    }
}
