package toufoumaster.btwaila;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.options.GuiOptions;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.item.Item;
import toufoumaster.btwaila.mixin.interfaces.IOptions;

import java.util.HashMap;
import java.util.Map;

public class BTWailaClient {
    public static GameSettings gameSettings;
    public static IOptions modSettings;
    public static OptionsPage wailaOptions;
    public static Map<String, String > modIds = new HashMap<>();
    public static void onLoad(){
        gameSettings = Minecraft.getMinecraft(Minecraft.class).gameSettings;
        modSettings = (IOptions) gameSettings;
        wailaOptions = new OptionsPage("btwaila.options.title", Item.basket.getDefaultStack())
                .withComponent(new OptionsCategory("btwaila.options.category.general")
                        .withComponent(new ToggleableOptionComponent<>(modSettings.bTWaila$getTooltipFormatting())))
                .withComponent(new OptionsCategory("btwaila.options.category.block")
                        .withComponent(new BooleanOptionComponent(modSettings.bTWaila$getBlockTooltips()))
                        .withComponent(new BooleanOptionComponent(modSettings.bTWaila$getBlockAdvancedTooltips()))
                        .withComponent(new BooleanOptionComponent(modSettings.bTWaila$getShowBlockId()))
                        .withComponent(new BooleanOptionComponent(modSettings.bTWaila$getShowBlockDesc()))
                        .withComponent(new BooleanOptionComponent(modSettings.bTWaila$getShowHarvestText())))
                .withComponent(new OptionsCategory("btwaila.options.category.entity")
                        .withComponent(new BooleanOptionComponent(modSettings.bTWaila$getEntityTooltips()))
                        .withComponent(new BooleanOptionComponent(modSettings.bTWaila$getEntityAdvancedTooltips()))
                        .withComponent(new ToggleableOptionComponent<>(modSettings.bTWaila$getSmallEntityHealthBar())))
                .withComponent(new OptionsCategory("btwaila.options.category.keybinds")
                        .withComponent(new KeyBindingComponent(modSettings.bTWaila$getKeyOpenBTWailaMenu()))
                        .withComponent(new KeyBindingComponent(modSettings.bTWaila$getKeyDemoCycle())));

        OptionsPages.register(wailaOptions);
        for (ModContainer container : FabricLoader.getInstance().getAllMods()) {
            modIds.put(container.getMetadata().getId(), container.getMetadata().getName());
        }

        OptionsPages.CONTROLS.withComponent(
                new OptionsCategory("btwaila.options.category.keybinds.explicit")
                        .withComponent(new KeyBindingComponent(modSettings.bTWaila$getKeyOpenBTWailaMenu()))
                        .withComponent(new KeyBindingComponent(modSettings.bTWaila$getKeyDemoCycle())));

    }
    public static GuiOptions getOptionsPage(GuiScreen parent){
        return new GuiOptions(parent, wailaOptions);
    }
}
