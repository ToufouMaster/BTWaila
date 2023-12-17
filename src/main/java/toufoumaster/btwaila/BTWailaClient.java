package toufoumaster.btwaila;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPages;
import toufoumaster.btwaila.gui.GuiBlockOverlay;

public class BTWailaClient {
    public static final GuiBlockOverlay blockOverlay = new GuiBlockOverlay();
    public static void onLoad(){
        Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
        IOptions iKeyBindings = (IOptions) mc.gameSettings;
        BTWailaClient.blockOverlay.setMinecraftInstance(mc);
        OptionsPages.CONTROLS.withComponent(
                new OptionsCategory("btwaila.options.category").withComponent(
                        new KeyBindingComponent(iKeyBindings.getKeyOpenBTWailaMenu())));
    }
}
