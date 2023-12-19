package toufoumaster.btwaila.mixin.interfaces;

import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.EnumOption;
import net.minecraft.client.option.FloatOption;
import net.minecraft.client.option.KeyBinding;
import toufoumaster.btwaila.util.TooltipFormatting;

public interface IOptions {
    BooleanOption getBlockTooltips();
    BooleanOption getBlockAdvancedTooltips();
    BooleanOption getEntityTooltips();
    BooleanOption getEntityAdvancedTooltips();
    BooleanOption getShowBlockId();
    BooleanOption getShowBlockDesc();
    BooleanOption getShowHarvestText();
    FloatOption getScaleTooltips();
    EnumOption<TooltipFormatting> getTooltipFormatting();
    KeyBinding getKeyOpenBTWailaMenu();
    KeyBinding getKeyDemoCycle();
}
