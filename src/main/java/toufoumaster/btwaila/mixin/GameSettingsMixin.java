package toufoumaster.btwaila.mixin;

import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
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
}
