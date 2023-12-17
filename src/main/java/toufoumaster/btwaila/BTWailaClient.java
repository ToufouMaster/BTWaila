package toufoumaster.btwaila;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.AbsoluteLayout;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.HudComponent;
import net.minecraft.client.gui.hud.HudComponents;
import net.minecraft.client.gui.hud.SnapLayout;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPages;
import toufoumaster.btwaila.gui.GuiBlockLookedComponent;
import toufoumaster.btwaila.gui.GuiBlockOverlay;
import toufoumaster.btwaila.gui.GuiBlockToolComponent;

public class BTWailaClient {
    public static HudComponent BlockInfoComp = HudComponents.register(
            new GuiBlockOverlay("wailaInfo",
                    new AbsoluteLayout(0.5f, 0.0f, ComponentAnchor.TOP_CENTER)));
    public static HudComponent BlockDisplayedComp = HudComponents.register(
            new GuiBlockLookedComponent("wailaBlock",
                    new SnapLayout(BlockInfoComp, ComponentAnchor.TOP_LEFT, ComponentAnchor.TOP_RIGHT)));
    public static HudComponent BlockToolComp = HudComponents.register(
            new GuiBlockToolComponent("wailaTool",
                    new SnapLayout(BlockDisplayedComp, ComponentAnchor.BOTTOM_CENTER, ComponentAnchor.TOP_CENTER)));
    public static void onLoad(){
        Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
        IOptions iKeyBindings = (IOptions) mc.gameSettings;
        OptionsPages.CONTROLS.withComponent(
                new OptionsCategory("btwaila.options.category").withComponent(
                        new KeyBindingComponent(iKeyBindings.getKeyOpenBTWailaMenu())));

    }
}
