package toufoumaster.btwaila.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiToggleButton;
import net.minecraft.client.option.BooleanOption;
import net.minecraft.core.lang.I18n;
import org.lwjgl.opengl.GL11;
import toufoumaster.btwaila.IOptions;
import toufoumaster.btwaila.util.Colors;

public class GuiBTWailaOption extends GuiScreen {

    final int stringColor = 16777215;
    final int buttonWidth = 75;
    final int buttonHeight = 20;
    final int paddingX = 10;
    final int paddingY = 30;
    final int centerTextY = 7;
    int leftButtonPosX;
    int rightButtonPosX;
    int buttonPosY;

    @Override
    public void initGui() {
        leftButtonPosX = this.width / 2 - 100;
        rightButtonPosX = this.width / 2 + 100-buttonWidth;
        buttonPosY = this.height / 4 + 72;
        this.controlList.clear();
        this.controlList.add(new GuiToggleButton(0, leftButtonPosX, buttonPosY+paddingY*0, buttonWidth, buttonHeight, ((IOptions)this.mc.gameSettings).getBlockTooltips()));
        this.controlList.add(new GuiToggleButton(1, rightButtonPosX, buttonPosY+paddingY*0, buttonWidth, buttonHeight, ((IOptions)this.mc.gameSettings).getBlockAdvancedTooltips()));
        this.controlList.add(new GuiToggleButton(2, leftButtonPosX, buttonPosY+paddingY*1, buttonWidth, buttonHeight, ((IOptions)this.mc.gameSettings).getEntityTooltips()));
        this.controlList.add(new GuiToggleButton(3, rightButtonPosX, buttonPosY+paddingY*1, buttonWidth, buttonHeight, ((IOptions)this.mc.gameSettings).getEntityAdvancedTooltips()));
    }

    @Override
    public void keyTyped(char c, int i, int mouseX, int mouseY) {
        if (i != 14) {
            super.keyTyped(c, i, mouseX, mouseY);
        }
    }

    @Override
    protected void buttonPressed(GuiButton guibutton) {
        IOptions gameSettings = (IOptions)this.mc.gameSettings;
        BooleanOption option = null;
        switch (guibutton.id) {
            case 0:
                option = gameSettings.getBlockTooltips();
                break;
            case 1:
                if (!gameSettings.getBlockTooltips().value) break;
                option = gameSettings.getBlockAdvancedTooltips();
                break;
            case 2:
                option = gameSettings.getEntityTooltips();
                break;
            case 3:
                if (!gameSettings.getEntityTooltips().value) break;
                option = gameSettings.getEntityAdvancedTooltips();
                break;
        }
        if (option != null) {
            option.toggle();
        }
    }

    @Override
    public void drawScreen(int x, int y, float renderPartialTicks) {
        I18n i18n = I18n.getInstance();
        GL11.glPushMatrix();
        this.drawRect(0, 0, width, height, 0x7F000000);
        this.drawString(this.fontRenderer, "*if text is not displayed correctly, download halplibe*", 10, 10, Colors.LIGHT_GRAY);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        this.drawStringCentered(this.fontRenderer, i18n.translateKey("btwaila.options.title"), this.width / 2 / 2, 30, stringColor);
        GL11.glPopMatrix();

        String blockTooltips = i18n.translateKey("btwaila.options.blocktooltips");
        String entityTooltips = i18n.translateKey("btwaila.options.entitytooltips");
        this.drawString(this.fontRenderer, blockTooltips, leftButtonPosX-this.fontRenderer.getStringWidth(blockTooltips)-paddingX, centerTextY+buttonPosY+paddingY*0, stringColor);
        this.drawString(this.fontRenderer, entityTooltips, leftButtonPosX-this.fontRenderer.getStringWidth(entityTooltips)-paddingX, centerTextY+buttonPosY+paddingY*1, stringColor);

        String blockAdvancedTooltips = i18n.translateKey("btwaila.options.blockadvancedtooltips");
        String entityAdvancedTooltips = i18n.translateKey("btwaila.options.entityadvancedtooltips");
        this.drawString(this.fontRenderer, blockAdvancedTooltips, rightButtonPosX+buttonWidth+paddingX, centerTextY+buttonPosY+paddingY*0, stringColor);
        this.drawString(this.fontRenderer, entityAdvancedTooltips, rightButtonPosX+buttonWidth+paddingX, centerTextY+buttonPosY+paddingY*1, stringColor);

        super.drawScreen(x, y, renderPartialTicks);
        IOptions gameSettings = (IOptions)this.mc.gameSettings;
        if (!gameSettings.getBlockTooltips().value) {
            this.drawRect(rightButtonPosX, buttonPosY+paddingY*0, rightButtonPosX+buttonWidth-1, buttonPosY+paddingY*0+buttonHeight, 0x7F000000);
        }
        if (!gameSettings.getEntityTooltips().value) {
            this.drawRect(rightButtonPosX, buttonPosY+paddingY*1, rightButtonPosX+buttonWidth-1, buttonPosY+paddingY*1+buttonHeight, 0x7F000000);
        }
    }
}
