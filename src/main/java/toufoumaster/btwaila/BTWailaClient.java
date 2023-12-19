package toufoumaster.btwaila;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.hud.AbsoluteLayout;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.HudComponent;
import net.minecraft.client.gui.hud.HudComponents;
import net.minecraft.client.gui.hud.SnapLayout;
import net.minecraft.client.gui.options.GuiOptions;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.ToggleableOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.GameSettings;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.gui.components.BaseInfoComponent;
import toufoumaster.btwaila.gui.components.DropIconComponent;
import toufoumaster.btwaila.gui.components.HarvestInfoComponent;
import toufoumaster.btwaila.gui.components.HarvestToolComponent;
import toufoumaster.btwaila.mixin.interfaces.IOptions;

import java.util.HashMap;
import java.util.Map;

public class BTWailaClient {
    public static final HudComponent BlockBaseInfoComp = HudComponents.register(
            new BaseInfoComponent("wailaInfoBase",
                    new AbsoluteLayout(0.5f, 0.0f, ComponentAnchor.TOP_CENTER)));
    public static final HudComponent BlockBreakComp = HudComponents.register(
            new HarvestInfoComponent("wailaInfoHarvest",
                    new SnapLayout(BlockBaseInfoComp,ComponentAnchor.BOTTOM_CENTER, ComponentAnchor.TOP_CENTER)));
    public static final HudComponent BlockAdvancedInfoComp = HudComponents.register(
            new AdvancedInfoComponent("wailaInfoAdvanced",
                    new SnapLayout(BlockBreakComp,ComponentAnchor.BOTTOM_CENTER, ComponentAnchor.TOP_CENTER)));
    public static final HudComponent BlockDisplayedComp = HudComponents.register(
            new DropIconComponent("wailaInfoIcon",
                    new SnapLayout(BlockBaseInfoComp, ComponentAnchor.TOP_LEFT, ComponentAnchor.TOP_RIGHT)));
    public static final HudComponent BlockToolComp = HudComponents.register(
            new HarvestToolComponent("wailaInfoTool",
                    new SnapLayout(BlockDisplayedComp, ComponentAnchor.BOTTOM_CENTER, ComponentAnchor.TOP_CENTER)));
    public static final GameSettings gameSettings = Minecraft.getMinecraft(Minecraft.class).gameSettings;
    public static final IOptions modSettings = (IOptions) gameSettings;
    public static final OptionsPage wailaOptions = new OptionsPage("btwaila.options.title")
            .withComponent(new OptionsCategory("btwaila.options.category.general")
                    .withComponent(new ToggleableOptionComponent<>(modSettings.getTooltipFormatting())))
            .withComponent(new OptionsCategory("btwaila.options.category.block")
                    .withComponent(new BooleanOptionComponent(modSettings.getBlockTooltips()))
                    .withComponent(new BooleanOptionComponent(modSettings.getBlockAdvancedTooltips()))
                    .withComponent(new BooleanOptionComponent(modSettings.getShowBlockId()))
                    .withComponent(new BooleanOptionComponent(modSettings.getShowBlockDesc()))
                    .withComponent(new BooleanOptionComponent(modSettings.getShowHarvestText())))
            .withComponent(new OptionsCategory("btwaila.options.category.entity")
                    .withComponent(new BooleanOptionComponent(modSettings.getEntityTooltips()))
                    .withComponent(new BooleanOptionComponent(modSettings.getEntityAdvancedTooltips())))
            .withComponent(new OptionsCategory("btwaila.options.category.keybinds")
                    .withComponent(new KeyBindingComponent(modSettings.getKeyOpenBTWailaMenu()))
                    .withComponent(new KeyBindingComponent(modSettings.getKeyDemoCycle())));
    public static Map<String, String > modIds = new HashMap<>();
    static {
        OptionsPages.register(wailaOptions);
        for (ModContainer container : FabricLoader.getInstance().getAllMods()) {
            modIds.put(container.getMetadata().getId(), container.getMetadata().getName());
        }
    }
    public static void onLoad(){
        OptionsPages.CONTROLS.withComponent(
                new OptionsCategory("btwaila.options.category.keybinds.explicit")
                        .withComponent(new KeyBindingComponent(modSettings.getKeyOpenBTWailaMenu()))
                        .withComponent(new KeyBindingComponent(modSettings.getKeyDemoCycle())));

    }
    public static GuiOptions getOptionsPage(GuiScreen parent){
        return new GuiOptions(parent, gameSettings, wailaOptions);
    }
}
