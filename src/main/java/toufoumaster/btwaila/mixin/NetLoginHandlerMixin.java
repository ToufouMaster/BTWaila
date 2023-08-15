package toufoumaster.btwaila.mixin;

import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.core.net.NetworkManager;
import net.minecraft.core.net.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetLoginHandler;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toufoumaster.btwaila.network.packet.PacketServerCheck;

@Mixin(
        value = NetLoginHandler.class,
        remap = false
)
public class NetLoginHandlerMixin {

    @Shadow public NetworkManager netManager;

    @Inject( method = "doLogin", at = @At("HEAD"))
    public void doLogin(Packet1Login packet1login, CallbackInfo ci) {
        this.netManager.addToSendQueue(new PacketServerCheck());
    }
}
