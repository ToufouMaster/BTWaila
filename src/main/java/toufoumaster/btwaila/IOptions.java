package toufoumaster.btwaila;

import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.FloatOption;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.RangeOption;

public interface IOptions {
    BooleanOption getBlockTooltips();
    BooleanOption getBlockAdvancedTooltips();
    BooleanOption getEntityTooltips();
    BooleanOption getEntityAdvancedTooltips();
    RangeOption getOffsetXTooltips();
    RangeOption getOffsetYTooltips();
    BooleanOption getCenteredTooltips();
    FloatOption getScaleTooltips();
    KeyBinding getKeyOpenBTWailaMenu();
}
