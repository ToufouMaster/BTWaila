package toufoumaster.btwaila.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toufoumaster.btwaila.BTWaila;
import toufoumaster.btwaila.BTWailaClient;
import toufoumaster.btwaila.IOptions;
import toufoumaster.btwaila.gui.GuiBTWailaOption;

@Mixin(value = GuiIngame.class, remap = false)
public class GuiIngameMixin extends Gui {

    @Shadow protected Minecraft mc;

    @Inject( method = "renderGameOverlay", at = @At("TAIL"))
    public void renderGameOverlay(float partialTicks, boolean flag, int mouseX, int mouseY, CallbackInfo ci) {
        if (BTWaila.showEntityOverlay) {
            BTWailaClient.blockOverlay.updateEntityOverlayWindow();
        } else if (BTWaila.showBlockOverlay) {
            BTWailaClient.blockOverlay.updateBlockOverlayWindow();
        }
        BTWaila.showBlockOverlay = false;
        BTWaila.showEntityOverlay = false;
    }

    @Inject( method = "updateTick", at = @At("TAIL"))
    public void updateTick(CallbackInfo ci) {
        if (((IOptions)this.mc.gameSettings).getKeyOpenBTWailaMenu().isPressed() && this.mc.currentScreen == null) {
            this.mc.displayGuiScreen(new GuiBTWailaOption());
        }
    }
}
