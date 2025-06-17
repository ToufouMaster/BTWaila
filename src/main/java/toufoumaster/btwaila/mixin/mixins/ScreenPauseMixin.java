package toufoumaster.btwaila.mixin.mixins;

import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.ScreenPause;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toufoumaster.btwaila.BTWaila;

@Mixin(value = ScreenPause.class, remap = false)
public class ScreenPauseMixin {
    @Inject( method = "buttonClicked", at = @At("HEAD"))
    public void buttonPressed(ButtonElement guibutton, CallbackInfo ci) {
        if (guibutton.id == 1) {
            BTWaila.canUseAdvancedTooltips = false;
        }
    }
}
