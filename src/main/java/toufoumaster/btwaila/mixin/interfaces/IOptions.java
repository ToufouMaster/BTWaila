package toufoumaster.btwaila.mixin.interfaces;

import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionEnum;
import net.minecraft.client.option.OptionFloat;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.OptionRange;
import net.minecraft.client.option.enums.TooltipStyle;
import toufoumaster.btwaila.util.BackgroundStyle;
import toufoumaster.btwaila.util.BarStyle;
import toufoumaster.btwaila.util.TooltipFormatting;

public interface IOptions {
    OptionBoolean bTWaila$getBlockTooltips();
    OptionBoolean bTWaila$getBlockAdvancedTooltips();
    OptionBoolean bTWaila$getEntityTooltips();
    OptionBoolean bTWaila$getEntityAdvancedTooltips();
    OptionRange bTWaila$getSmallEntityHealthBar();
    OptionBoolean bTWaila$getShowBlockId();
    OptionBoolean bTWaila$getShowBlockDesc();
    OptionBoolean bTWaila$getShowHarvestText();
    OptionFloat bTWaila$getScaleTooltips();
    OptionEnum<TooltipFormatting> bTWaila$getTooltipFormatting();
    OptionEnum<BarStyle> bTWaila$getBarStyle();
    KeyBinding bTWaila$getKeyOpenBTWailaMenu();
    KeyBinding bTWaila$getKeyDemoCycle();
    OptionEnum<BackgroundStyle> bTWaila$getBackgroundStyle();
    OptionFloat bTWaila$getBackgroundOpacity();
}
