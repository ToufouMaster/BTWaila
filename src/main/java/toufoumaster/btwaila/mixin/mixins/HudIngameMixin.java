package toufoumaster.btwaila.mixin.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.HudIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toufoumaster.btwaila.BTWailaClient;
import toufoumaster.btwaila.mixin.interfaces.IOptions;

@Mixin(value = HudIngame.class, remap = false)
public class HudIngameMixin extends Gui {

    @Unique
    boolean blockTooltipKeyReleased = true;
    @Unique
    boolean entityTooltipKeyReleased = true;

    @Shadow protected Minecraft mc;
    @Inject( method = "updateTick", at = @At("TAIL"))
    public void updateTick(CallbackInfo ci) {
        if (((IOptions)this.mc.gameSettings).bTWaila$getKeyOpenBTWailaMenu().isPressed() && this.mc.currentScreen == null) {
            this.mc.displayScreen(BTWailaClient.getOptionsPage(null));
        }


        if (((IOptions)this.mc.gameSettings).bTWaila$getKeyToggleEntityTooltips().isPressed() && this.mc.currentScreen == null) {
            if (entityTooltipKeyReleased)
                ((IOptions) this.mc.gameSettings).bTWaila$getEntityTooltips().toggle();
            entityTooltipKeyReleased = false;
        } else if ((((IOptions)this.mc.gameSettings).bTWaila$getKeyToggleEntityTooltips().isReleaseEvent())) entityTooltipKeyReleased = true;

        if (((IOptions)this.mc.gameSettings).bTWaila$getKeyToggleBlockTooltips().isPressed() && this.mc.currentScreen == null) {
            if (blockTooltipKeyReleased)
                ((IOptions) this.mc.gameSettings).bTWaila$getBlockTooltips().toggle();
            blockTooltipKeyReleased = false;
        } else if ((((IOptions)this.mc.gameSettings).bTWaila$getKeyToggleBlockTooltips().isReleaseEvent())) blockTooltipKeyReleased = true;

    }
}
