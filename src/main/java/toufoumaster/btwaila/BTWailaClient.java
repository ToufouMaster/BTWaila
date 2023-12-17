package toufoumaster.btwaila;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.AbsoluteLayout;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.HudComponents;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPages;
import toufoumaster.btwaila.gui.GuiBlockOverlay;

public class BTWailaClient {
    public static void onLoad(){
        Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
        IOptions iKeyBindings = (IOptions) mc.gameSettings;
        OptionsPages.CONTROLS.withComponent(
                new OptionsCategory("btwaila.options.category").withComponent(
                        new KeyBindingComponent(iKeyBindings.getKeyOpenBTWailaMenu())));
        HudComponents.register(new GuiBlockOverlay("waila", 32 + 16 * 9 + 32, 100, new AbsoluteLayout(0.5f, 0.0f, ComponentAnchor.TOP_CENTER)));
    }
}
