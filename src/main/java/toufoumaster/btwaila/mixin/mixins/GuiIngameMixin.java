package toufoumaster.btwaila.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toufoumaster.btwaila.mixin.interfaces.IOptions;
import toufoumaster.btwaila.gui.WailaOptionPageHolder;

@Mixin(value = GuiIngame.class, remap = false)
public class GuiIngameMixin extends Gui {

    @Shadow protected Minecraft mc;
    @Inject( method = "updateTick", at = @At("TAIL"))
    public void updateTick(CallbackInfo ci) {
        if (((IOptions)this.mc.gameSettings).getKeyOpenBTWailaMenu().isPressed() && this.mc.currentScreen == null) {
            this.mc.displayGuiScreen(WailaOptionPageHolder.getOptionsPage(null));
        }
    }
}
