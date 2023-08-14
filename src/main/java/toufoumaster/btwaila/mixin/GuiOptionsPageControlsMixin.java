package toufoumaster.btwaila.mixin;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.options.GuiOptionsPageControls;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import toufoumaster.btwaila.IKeyBindings;

@Mixin(value = GuiOptionsPageControls.class, remap = false)
public abstract class GuiOptionsPageControlsMixin {

    @Shadow public abstract void addKeyBindingsCategory(String languageKey, KeyBinding... bindings);

    @Inject( method = "<init>", at = @At("TAIL"))
    public void GuiOptionsPageControls(GuiScreen parent, GameSettings settings, CallbackInfo ci) {
        this.addKeyBindingsCategory(
                "btwaila.options.category",
                ((IKeyBindings)settings).getKeyOpenBTWailaMenu()
        );
    }
}
