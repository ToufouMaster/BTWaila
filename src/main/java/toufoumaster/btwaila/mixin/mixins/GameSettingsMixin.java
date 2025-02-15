package toufoumaster.btwaila.mixin.mixins;

import net.minecraft.client.input.InputDevice;
import net.minecraft.client.option.*;
import net.minecraft.core.lang.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import toufoumaster.btwaila.mixin.interfaces.IOptions;
import toufoumaster.btwaila.util.TooltipFormatting;
import toufoumaster.btwaila.util.BarStyle;

import static org.lwjgl.input.Keyboard.KEY_F9;
import static org.lwjgl.input.Keyboard.KEY_NUMPAD0;

@Mixin(value = GameSettings.class, remap = false)
public class GameSettingsMixin implements IOptions {
    @Unique
    private final GameSettings thisAs = (GameSettings)(Object)this;
    @Unique
    public final KeyBinding keyOpenBTWailaMenu = new KeyBinding("btwaila.key.menu").bind(InputDevice.keyboard, KEY_NUMPAD0);
    @Unique
    public final KeyBinding keyDemoCycle = new KeyBinding("btwaila.key.democycle").bind(InputDevice.keyboard, KEY_F9);
    @Unique
    public final OptionBoolean blockTooltips = new OptionBoolean(thisAs, "blockTooltips", true);
    @Unique
    public final OptionBoolean blockAdvancedTooltips = new OptionBoolean(thisAs, "blockAdvancedTooltips", true);
    @Unique
    public final OptionBoolean entityTooltips = new OptionBoolean(thisAs, "entityTooltips", true);
    @Unique
    public final OptionBoolean entityAdvancedTooltips = new OptionBoolean(thisAs, "entityAdvancedTooltips", true);
    @Unique
    public final OptionRange smallEntityHealthBar = new OptionRange(thisAs, "smallHealthBar", 0, 6);
    @Unique
    public final OptionBoolean showBlockId = new OptionBoolean(thisAs, "showBlockId", false);
    @Unique
    public final OptionBoolean showBlockDescriptions = new OptionBoolean(thisAs, "showBlockDesc", true);
    @Unique
    public final OptionBoolean showHarvestText = new OptionBoolean(thisAs, "showHarvestText", true);
    @Unique
    public final OptionEnum<TooltipFormatting> tooltipFormatting = new OptionEnum<>(thisAs, "tooltipFormatting",TooltipFormatting.class,TooltipFormatting.LEFT);
    @Unique
    public final OptionEnum<BarStyle> barStyle = new OptionEnum<>(thisAs, "barStyle", BarStyle.class, BarStyle.PLAIN);
    @Unique
    public final OptionFloat scaleTooltips = new OptionFloat(thisAs, "scaleTooltips", 0.5f);

    public KeyBinding bTWaila$getKeyOpenBTWailaMenu() {
        return keyOpenBTWailaMenu;
    }
    public KeyBinding bTWaila$getKeyDemoCycle() {return keyDemoCycle;}
    public OptionBoolean bTWaila$getBlockTooltips() {
        return blockTooltips;
    }
    public OptionBoolean bTWaila$getBlockAdvancedTooltips() {
        return blockAdvancedTooltips;
    }
    public OptionBoolean bTWaila$getEntityTooltips() {
        return entityTooltips;
    }
    public OptionBoolean bTWaila$getEntityAdvancedTooltips() {
        return entityAdvancedTooltips;
    }
    public OptionRange bTWaila$getSmallEntityHealthBar() {
        return smallEntityHealthBar;
    }
    public OptionBoolean bTWaila$getShowBlockId() {return showBlockId;}
    public OptionBoolean bTWaila$getShowBlockDesc() {return showBlockDescriptions;}
    public OptionBoolean bTWaila$getShowHarvestText() {return showHarvestText;}
    public OptionEnum<TooltipFormatting> bTWaila$getTooltipFormatting() {return tooltipFormatting;}
    public OptionEnum<BarStyle> bTWaila$getBarStyle() {return barStyle;}
    public OptionFloat bTWaila$getScaleTooltips() {return scaleTooltips;}
    @Inject(method = "getDisplayString(Lnet/minecraft/client/option/Option;)Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
    private void displayStrings(Option<?> option, CallbackInfoReturnable<String> cir){
        I18n translator = I18n.getInstance();
        if (option == smallEntityHealthBar){
            cir.setReturnValue(translator.translateKey("options.rowAmount").replace("{x}", String.valueOf(smallEntityHealthBar.value)));
        }
    }
}
