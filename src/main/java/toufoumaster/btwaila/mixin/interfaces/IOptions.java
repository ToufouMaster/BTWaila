package toufoumaster.btwaila.mixin.interfaces;

import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.FloatOption;
import net.minecraft.client.option.KeyBinding;

public interface IOptions {
    BooleanOption getBlockTooltips();
    BooleanOption getBlockAdvancedTooltips();
    BooleanOption getEntityTooltips();
    BooleanOption getEntityAdvancedTooltips();
    FloatOption getScaleTooltips();
    KeyBinding getKeyOpenBTWailaMenu();
}