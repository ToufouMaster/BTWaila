package toufoumaster.btwaila.mixin.mixins;

import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.FloatOption;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import toufoumaster.btwaila.mixin.interfaces.IOptions;

import static org.lwjgl.input.Keyboard.KEY_NUMPAD0;

@Mixin(value = GameSettings.class, remap = false)
public abstract class GameSettingsMixin implements IOptions {
    @Unique
    public KeyBinding keyOpenBTWailaMenu = new KeyBinding("btwaila.key.menu").bindKeyboard(KEY_NUMPAD0);
    @Unique
    public BooleanOption blockTooltips = new BooleanOption((GameSettings) ((Object)this), "blockTooltips", true);
    @Unique
    public BooleanOption blockAdvancedTooltips = new BooleanOption((GameSettings) ((Object)this), "blockAdvancedTooltips", true);
    @Unique
    public BooleanOption entityTooltips = new BooleanOption((GameSettings) ((Object)this), "blockTooltips", true);
    @Unique
    public BooleanOption entityAdvancedTooltips = new BooleanOption((GameSettings) ((Object)this), "entityAdvancedTooltips", true);
    @Unique
    public FloatOption scaleTooltips = new FloatOption((GameSettings) ((Object)this), "scaleTooltips", 0.5f);

    public KeyBinding getKeyOpenBTWailaMenu() {
        return keyOpenBTWailaMenu;
    }
    public BooleanOption getBlockTooltips() {
        return blockTooltips;
    }
    public BooleanOption getBlockAdvancedTooltips() {
        return blockAdvancedTooltips;
    }
    public BooleanOption getEntityTooltips() {
        return entityTooltips;
    }
    public BooleanOption getEntityAdvancedTooltips() {
        return entityAdvancedTooltips;
    }
    public FloatOption getScaleTooltips() {
        return scaleTooltips;
    }
}
