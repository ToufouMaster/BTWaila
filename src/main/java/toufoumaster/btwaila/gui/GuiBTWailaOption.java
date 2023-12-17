package toufoumaster.btwaila.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.options.GuiOptions;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.GameSettings;
import toufoumaster.btwaila.IOptions;

public class GuiBTWailaOption extends GuiScreen {
    public static GameSettings gameSettings = Minecraft.getMinecraft(Minecraft.class).gameSettings;
    public static IOptions modSettings = (IOptions) gameSettings;
    public static final OptionsPage wailaOptions = new OptionsPage("btwaila.options.title")
            .withComponent(new OptionsCategory("btwaila.options.category")
                    .withComponent(new BooleanOptionComponent(modSettings.getBlockTooltips()))
                    .withComponent(new BooleanOptionComponent(modSettings.getBlockAdvancedTooltips()))
                    .withComponent(new BooleanOptionComponent(modSettings.getEntityTooltips()))
                    .withComponent(new BooleanOptionComponent(modSettings.getEntityAdvancedTooltips()))
                    .withComponent(new KeyBindingComponent(modSettings.getKeyOpenBTWailaMenu())));
    static {
        OptionsPages.register(wailaOptions);
    }
    public static GuiOptions getOptionsPage(GuiScreen parent){
        return new GuiOptions(parent, gameSettings, wailaOptions);
    }
}
