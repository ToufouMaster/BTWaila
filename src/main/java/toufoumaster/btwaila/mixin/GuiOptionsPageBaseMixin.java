package toufoumaster.btwaila.mixin;

import net.minecraft.client.gui.options.GuiOptionsPageBase;
import net.minecraft.client.option.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = GuiOptionsPageBase.class, remap = false)
public interface GuiOptionsPageBaseMixin {
    @Accessor GameSettings getGameSettings();
}
