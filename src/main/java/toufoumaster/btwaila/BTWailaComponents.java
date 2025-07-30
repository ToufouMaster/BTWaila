package toufoumaster.btwaila;

import net.minecraft.client.gui.hud.*;
import net.minecraft.client.gui.hud.component.ComponentAnchor;
import net.minecraft.client.gui.hud.component.HudComponent;
import net.minecraft.client.gui.hud.component.HudComponents;
import net.minecraft.client.gui.hud.component.layout.LayoutAbsolute;
import net.minecraft.client.gui.hud.component.layout.LayoutSnap;
import toufoumaster.btwaila.gui.components.*;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import toufoumaster.btwaila.interfaces.HudComponentsRegisteredEntryPoint;

public class BTWailaComponents implements ClientStartEntrypoint, HudComponentsRegisteredEntryPoint {
    public static HudComponent BlockBaseInfoComp;
    public static HudComponent BlockBreakComp;
    public static HudComponent BlockAdvancedInfoComp;
    public static HudComponent BlockDisplayedComp;
    public static HudComponent BlockToolComp;

    @Override
    public void beforeClientStart() {

    }

    @Override
    public void afterClientStart() {

    }

    @Override
    public void afterComponentsRegistered() {
        BlockBaseInfoComp = HudComponents.register(
                new BaseInfoComponent("wailaInfoBase",
                        new LayoutAbsolute(0.5f, 0.0f, ComponentAnchor.TOP_CENTER)));
        BlockBreakComp = HudComponents.register(
                new HarvestInfoComponent("wailaInfoHarvest",
                        new LayoutSnap(BlockBaseInfoComp, ComponentAnchor.BOTTOM_CENTER, ComponentAnchor.TOP_CENTER)));
        BlockAdvancedInfoComp = HudComponents.register(
                new AdvancedInfoComponent("wailaInfoAdvanced",
                        new LayoutSnap(BlockBreakComp, ComponentAnchor.BOTTOM_CENTER, ComponentAnchor.TOP_CENTER)));
        BlockDisplayedComp = HudComponents.register(
                new DropIconComponent("wailaInfoIcon",
                        new LayoutSnap(BlockBaseInfoComp, ComponentAnchor.TOP_LEFT, ComponentAnchor.TOP_RIGHT)));
        BlockToolComp = HudComponents.register(
                new HarvestToolComponent("wailaInfoTool",
                        new LayoutSnap(BlockDisplayedComp, ComponentAnchor.BOTTOM_CENTER, ComponentAnchor.TOP_CENTER)));
    }
}
