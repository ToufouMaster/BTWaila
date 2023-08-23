package toufoumaster.btwaila.mixin;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toufoumaster.btwaila.BTWaila;

@Mixin(value = GuiIngameMenu.class, remap = false)
public class GuiIngameMenuMixin {
    @Inject( method = "buttonPressed", at = @At("HEAD"))
    public void buttonPressed(GuiButton guibutton, CallbackInfo ci) {
        if (guibutton.id == 1) {
            BTWaila.canUseAdvancedTooltips = false;
        }
    }
}
