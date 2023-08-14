package toufoumaster.btwaila;

import com.b100.utils.ReflectUtils;
import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Option;
import toufoumaster.btwaila.mixin.GameSettingsMixin;

public interface IOptions {
    BooleanOption getBlockTooltips();
    BooleanOption getBlockAdvancedTooltips();
    BooleanOption getEntityTooltips();
    BooleanOption getEntityAdvancedTooltips();
}
