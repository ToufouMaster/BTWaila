package toufoumaster.btwaila.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.GuiToggleButton;
import net.minecraft.client.option.BooleanOption;
import net.minecraft.client.option.RangeOption;
import net.minecraft.client.option.ToggleableOption;
import net.minecraft.core.lang.I18n;
import org.lwjgl.opengl.GL11;
import toufoumaster.btwaila.IOptions;
import toufoumaster.btwaila.util.Colors;

public class GuiBTWailaOption extends GuiScreen {

    final int stringColor = Colors.WHITE;
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
        this.controlList.add(new GuiToggleButton(4, leftButtonPosX, buttonPosY+paddingY*2, buttonWidth, buttonHeight, ((IOptions)this.mc.gameSettings).getOffsetXTooltips()));
        this.controlList.add(new GuiToggleButton(5, rightButtonPosX, buttonPosY+paddingY*2, buttonWidth, buttonHeight, ((IOptions)this.mc.gameSettings).getOffsetYTooltips()));
        this.controlList.add(new GuiToggleButton(6, leftButtonPosX, buttonPosY+paddingY*3, buttonWidth, buttonHeight, ((IOptions)this.mc.gameSettings).getCenteredTooltips()));
        this.controlList.add(new GuiSlider(7, rightButtonPosX, buttonPosY+paddingY*3, buttonWidth, buttonHeight, ((IOptions)this.mc.gameSettings).getScaleTooltips()));
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
        ToggleableOption option = null;
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
            case 4:
                if (gameSettings.getCenteredTooltips().value) break;
                option = gameSettings.getOffsetXTooltips();
                break;
            case 5:
                option = gameSettings.getOffsetYTooltips();
                break;
            case 6:
                option = gameSettings.getCenteredTooltips();
                break;
        }
        if (option != null) {
            option.toggle();
            option.onUpdate();
        }
        initGui();
    }

    @Override
    public void drawScreen(int x, int y, float renderPartialTicks) {
        I18n i18n = I18n.getInstance();
        GL11.glPushMatrix();
        this.drawDefaultBackground();
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

        String offsetXTooltips = i18n.translateKey("btwaila.options.offsetxtooltips");
        String offsetYTooltips = i18n.translateKey("btwaila.options.offsetytooltips");
        this.drawString(this.fontRenderer, offsetXTooltips, leftButtonPosX-this.fontRenderer.getStringWidth(offsetXTooltips)-paddingX, centerTextY+buttonPosY+paddingY*2, stringColor);
        this.drawString(this.fontRenderer, offsetYTooltips, rightButtonPosX+buttonWidth+paddingX, centerTextY+buttonPosY+paddingY*2, stringColor);

        String centeredTooltips = i18n.translateKey("btwaila.options.centeredtooltips");
        String scaleTooltips = i18n.translateKey("btwaila.options.scaletooltips");
        this.drawString(this.fontRenderer, centeredTooltips, leftButtonPosX-this.fontRenderer.getStringWidth(centeredTooltips)-paddingX, centerTextY+buttonPosY+paddingY*3, stringColor);
        this.drawString(this.fontRenderer, scaleTooltips, rightButtonPosX+buttonWidth+paddingX, centerTextY+buttonPosY+paddingY*3, stringColor);

        super.drawScreen(x, y, renderPartialTicks);
        IOptions gameSettings = (IOptions)this.mc.gameSettings;
        if (!gameSettings.getBlockTooltips().value) {
            this.drawRect(rightButtonPosX, buttonPosY+paddingY*0, rightButtonPosX+buttonWidth-1, buttonPosY+paddingY*0+buttonHeight, 0x7F000000);
        }
        if (!gameSettings.getEntityTooltips().value) {
            this.drawRect(rightButtonPosX, buttonPosY+paddingY*1, rightButtonPosX+buttonWidth-1, buttonPosY+paddingY*1+buttonHeight, 0x7F000000);
        }
        if (gameSettings.getCenteredTooltips().value) {
            this.drawRect(leftButtonPosX, buttonPosY+paddingY*2, leftButtonPosX+buttonWidth-1, buttonPosY+paddingY*2+buttonHeight, 0x7F000000);
        }
        // DISABLED OPTION
        this.drawRect(rightButtonPosX, buttonPosY+paddingY*3, rightButtonPosX+buttonWidth-1, buttonPosY+paddingY*3+buttonHeight, 0x7F000000);
    }
}
