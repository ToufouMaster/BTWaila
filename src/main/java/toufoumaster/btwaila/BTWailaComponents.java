package toufoumaster.btwaila;

import net.minecraft.client.gui.hud.*;
import toufoumaster.btwaila.gui.components.*;
import turniplabs.halplibe.util.ClientStartEntrypoint;

public class BTWailaComponents implements ClientStartEntrypoint {
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
        BlockBaseInfoComp = HudComponents.register(
                new BaseInfoComponent("wailaInfoBase",
                        new AbsoluteLayout(0.5f, 0.0f, ComponentAnchor.TOP_CENTER)));
        BlockBreakComp = HudComponents.register(
                new HarvestInfoComponent("wailaInfoHarvest",
                        new SnapLayout(BlockBaseInfoComp, ComponentAnchor.BOTTOM_CENTER, ComponentAnchor.TOP_CENTER)));
        BlockAdvancedInfoComp = HudComponents.register(
                new AdvancedInfoComponent("wailaInfoAdvanced",
                        new SnapLayout(BlockBreakComp, ComponentAnchor.BOTTOM_CENTER, ComponentAnchor.TOP_CENTER)));
        BlockDisplayedComp = HudComponents.register(
                new DropIconComponent("wailaInfoIcon",
                        new SnapLayout(BlockBaseInfoComp, ComponentAnchor.TOP_LEFT, ComponentAnchor.TOP_RIGHT)));
        BlockToolComp = HudComponents.register(
                new HarvestToolComponent("wailaInfoTool",
                        new SnapLayout(BlockDisplayedComp, ComponentAnchor.BOTTOM_CENTER, ComponentAnchor.TOP_CENTER)));
    }
}
