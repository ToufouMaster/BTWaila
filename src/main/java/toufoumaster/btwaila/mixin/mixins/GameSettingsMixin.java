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
    public final KeyBinding keyOpenBTWailaMenu = new KeyBinding("btwaila.key.menu").bindKeyboard(KEY_NUMPAD0);
    @Unique
    public final BooleanOption blockTooltips = new BooleanOption((GameSettings) ((Object)this), "blockTooltips", true);
    @Unique
    public final BooleanOption blockAdvancedTooltips = new BooleanOption((GameSettings) ((Object)this), "blockAdvancedTooltips", true);
    @Unique
    public final BooleanOption entityTooltips = new BooleanOption((GameSettings) ((Object)this), "blockTooltips", true);
    @Unique
    public final BooleanOption entityAdvancedTooltips = new BooleanOption((GameSettings) ((Object)this), "entityAdvancedTooltips", true);
    @Unique
    public final FloatOption scaleTooltips = new FloatOption((GameSettings) ((Object)this), "scaleTooltips", 0.5f);

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
