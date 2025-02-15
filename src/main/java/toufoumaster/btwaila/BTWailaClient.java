package toufoumaster.btwaila;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.options.ScreenOptions;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.GameSettings;
import net.minecraft.core.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import toufoumaster.btwaila.entryplugins.waila.BTWailaCustomTooltipPlugin;
import toufoumaster.btwaila.entryplugins.waila.BTWailaPlugin;
import toufoumaster.btwaila.gui.components.WailaTextComponent;
import toufoumaster.btwaila.mixin.interfaces.IOptions;
import toufoumaster.btwaila.tooltips.TooltipRegistry;
import turniplabs.halplibe.util.ClientStartEntrypoint;

import java.util.HashMap;
import java.util.Map;

public class BTWailaClient implements ClientModInitializer, ClientStartEntrypoint {
    public static GameSettings gameSettings;
    public static IOptions modSettings;
    public static OptionsPage wailaOptions;
    public static Map<String, String > modIds = new HashMap<>();

    public static final String MOD_ID = "btwaila|client";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void onLoad(){
        gameSettings = Minecraft.getMinecraft().gameSettings;
        modSettings = (IOptions) gameSettings;
        wailaOptions = new OptionsPage("btwaila.options.title", Items.BASKET.getDefaultStack())
                .withComponent(new OptionsCategory("btwaila.options.category.general")
                        .withComponent(new ToggleableOptionComponent<>(modSettings.bTWaila$getTooltipFormatting())))
                .withComponent(new OptionsCategory("btwaila.options.category.block")
                        .withComponent(new BooleanOptionComponent(modSettings.bTWaila$getBlockTooltips()))
                        .withComponent(new BooleanOptionComponent(modSettings.bTWaila$getBlockAdvancedTooltips()))
                        .withComponent(new BooleanOptionComponent(modSettings.bTWaila$getShowBlockId()))
                        .withComponent(new BooleanOptionComponent(modSettings.bTWaila$getShowBlockDesc()))
                        .withComponent(new BooleanOptionComponent(modSettings.bTWaila$getShowHarvestText()))
                        .withComponent(new ToggleableOptionComponent<>(modSettings.bTWaila$getBarStyle())))
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
    public static Screen getOptionsPage(Screen parent){
        return new ScreenOptions(parent, wailaOptions);
    }

    @Override
    public void onInitializeClient() {

    }

    @Override
    public void beforeClientStart() {

    }

    @Override
    public void afterClientStart() {
        onLoad();
        WailaTextComponent.init();
        LOGGER.info("Loading implementations.");
        new BTWailaPlugin().initializePlugin(TooltipRegistry.getInstance(), LOGGER); // Load BTWaila tooltips first
        FabricLoader.getInstance().getEntrypointContainers("btwaila", BTWailaCustomTooltipPlugin.class).forEach(plugin -> plugin.getEntrypoint().initializePlugin(TooltipRegistry.getInstance(), LOGGER));
    }
}
