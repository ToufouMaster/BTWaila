package toufoumaster.btwaila.mixin.mixins;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.hud.component.HudComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toufoumaster.btwaila.interfaces.HudComponentsRegisteredEntryPoint;

@Mixin(value = HudComponents.class, remap = false)
public class HudComponentsMixin {
    @Inject(method = "<clinit>", at = @At(value = "TAIL"))
    private static void init(CallbackInfo ci) {
        FabricLoader.getInstance().getEntrypoints("afterComponentsRegistered", HudComponentsRegisteredEntryPoint.class).forEach(HudComponentsRegisteredEntryPoint::afterComponentsRegistered);
    }
}