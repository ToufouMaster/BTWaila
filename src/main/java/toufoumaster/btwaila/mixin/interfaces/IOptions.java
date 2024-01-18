package toufoumaster.btwaila.mixin.interfaces;

import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.EnumOption;
import net.minecraft.client.option.FloatOption;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.RangeOption;
import toufoumaster.btwaila.util.TooltipFormatting;

public interface IOptions {
    BooleanOption bTWaila$getBlockTooltips();
    BooleanOption bTWaila$getBlockAdvancedTooltips();
    BooleanOption bTWaila$getEntityTooltips();
    BooleanOption bTWaila$getEntityAdvancedTooltips();
    RangeOption bTWaila$getSmallEntityHealthBar();
    BooleanOption bTWaila$getShowBlockId();
    BooleanOption bTWaila$getShowBlockDesc();
    BooleanOption bTWaila$getShowHarvestText();
    FloatOption bTWaila$getScaleTooltips();
    EnumOption<TooltipFormatting> bTWaila$getTooltipFormatting();
    KeyBinding bTWaila$getKeyOpenBTWailaMenu();
    KeyBinding bTWaila$getKeyDemoCycle();
}
