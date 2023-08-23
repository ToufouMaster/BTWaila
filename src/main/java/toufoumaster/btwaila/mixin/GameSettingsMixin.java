package toufoumaster.btwaila.mixin;

import net.minecraft.client.option.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import toufoumaster.btwaila.IKeyBindings;
import toufoumaster.btwaila.IOptions;

@Mixin(value = GameSettings.class, remap = false)
public abstract class GameSettingsMixin implements IKeyBindings, IOptions {

    @Unique
    public KeyBinding keyOpenBTWailaMenu = new KeyBinding("btwaila.key.menu", 82);

    @Unique
    public BooleanOption blockTooltips = new BooleanOption((GameSettings) ((Object)this), "blockTooltips", true);
    @Unique
    public BooleanOption blockAdvancedTooltips = new BooleanOption((GameSettings) ((Object)this), "blockAdvancedTooltips", true);
    @Unique
    public BooleanOption entityTooltips = new BooleanOption((GameSettings) ((Object)this), "blockTooltips", true);
    @Unique
    public BooleanOption entityAdvancedTooltips = new BooleanOption((GameSettings) ((Object)this), "entityAdvancedTooltips", true);
    @Unique
    public RangeOption offsetXTooltips = new RangeOption((GameSettings) ((Object)this), "offsetXTooltips", 0, 5);
    @Unique
    public RangeOption offsetYTooltips = new RangeOption((GameSettings) ((Object)this), "offsetYTooltips", 1, 5);
    @Unique
    public BooleanOption centeredTooltips = new BooleanOption((GameSettings) ((Object)this), "centeredTooltips", true);
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

    public RangeOption getOffsetXTooltips() {
        return offsetXTooltips;
    }

    public RangeOption getOffsetYTooltips() {
        return offsetYTooltips;
    }

    public BooleanOption getCenteredTooltips() {
        return centeredTooltips;
    }

    public FloatOption getScaleTooltips() {
        return scaleTooltips;
    }
}
