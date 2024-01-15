package toufoumaster.btwaila;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.hud.AbsoluteLayout;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.HudComponent;
import net.minecraft.client.gui.hud.HudComponents;
import net.minecraft.client.gui.hud.SnapLayout;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.gui.components.BaseInfoComponent;
import toufoumaster.btwaila.gui.components.DropIconComponent;
import toufoumaster.btwaila.gui.components.HarvestInfoComponent;
import toufoumaster.btwaila.gui.components.HarvestToolComponent;

public class BTWailaComponents implements ClientModInitializer {
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
    @Override
    public void onInitializeClient() {

    }
}
