package toufoumaster.btwaila.mixin.mixins;

import net.minecraft.client.input.InputDevice;
import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.EnumOption;
import net.minecraft.client.option.FloatOption;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Option;
import net.minecraft.client.option.RangeOption;
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
    public final BooleanOption blockTooltips = new BooleanOption(thisAs, "blockTooltips", true);
    @Unique
    public final BooleanOption blockAdvancedTooltips = new BooleanOption(thisAs, "blockAdvancedTooltips", true);
    @Unique
    public final BooleanOption entityTooltips = new BooleanOption(thisAs, "entityTooltips", true);
    @Unique
    public final BooleanOption entityAdvancedTooltips = new BooleanOption(thisAs, "entityAdvancedTooltips", true);
    @Unique
    public final RangeOption smallEntityHealthBar = new RangeOption(thisAs, "smallHealthBar", 0, 6);
    @Unique
    public final BooleanOption showBlockId = new BooleanOption(thisAs, "showBlockId", false);
    @Unique
    public final BooleanOption showBlockDescriptions = new BooleanOption(thisAs, "showBlockDesc", true);
    @Unique
    public final BooleanOption showHarvestText = new BooleanOption(thisAs, "showHarvestText", true);
    @Unique
    public final EnumOption<TooltipFormatting> tooltipFormatting = new EnumOption<>(thisAs, "tooltipFormatting",TooltipFormatting.class,TooltipFormatting.LEFT);
    @Unique
    public final EnumOption<BarStyle> barStyle = new EnumOption<>(thisAs, "barStyle", BarStyle.class, BarStyle.PLAIN);
    @Unique
    public final FloatOption scaleTooltips = new FloatOption(thisAs, "scaleTooltips", 0.5f);

    public KeyBinding bTWaila$getKeyOpenBTWailaMenu() {
        return keyOpenBTWailaMenu;
    }
    public KeyBinding bTWaila$getKeyDemoCycle() {return keyDemoCycle;}
    public BooleanOption bTWaila$getBlockTooltips() {
        return blockTooltips;
    }
    public BooleanOption bTWaila$getBlockAdvancedTooltips() {
        return blockAdvancedTooltips;
    }
    public BooleanOption bTWaila$getEntityTooltips() {
        return entityTooltips;
    }
    public BooleanOption bTWaila$getEntityAdvancedTooltips() {
        return entityAdvancedTooltips;
    }
    public RangeOption bTWaila$getSmallEntityHealthBar() {
        return smallEntityHealthBar;
    }
    public BooleanOption bTWaila$getShowBlockId() {return showBlockId;}
    public BooleanOption bTWaila$getShowBlockDesc() {return showBlockDescriptions;}
    public BooleanOption bTWaila$getShowHarvestText() {return showHarvestText;}
    public EnumOption<TooltipFormatting> bTWaila$getTooltipFormatting() {return tooltipFormatting;}
    public EnumOption<BarStyle> bTWaila$getBarStyle() {return barStyle;}
    public FloatOption bTWaila$getScaleTooltips() {return scaleTooltips;}
    @Inject(method = "getDisplayString(Lnet/minecraft/client/option/Option;)Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
    private void displayStrings(Option<?> option, CallbackInfoReturnable<String> cir){
        I18n translator = I18n.getInstance();
        if (option == smallEntityHealthBar){
            cir.setReturnValue(translator.translateKey("options.rowAmount").replace("{x}", String.valueOf(smallEntityHealthBar.value)));
        }
    }
}
