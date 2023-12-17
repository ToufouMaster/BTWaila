package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityFlag;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import org.lwjgl.opengl.GL11;
import toufoumaster.btwaila.*;
import toufoumaster.btwaila.gui.GuiBlockOverlay;

import static toufoumaster.btwaila.gui.GuiBlockOverlay.translator;

public class FlagTooltip implements IBTWailaCustomBlockTooltip {

    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", TileEntityFlag.class, this);
        tooltipGroup.addTooltip(TileEntityFlag.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(TileEntity tileEntity, GuiBlockOverlay guiBlockOverlay) {
        TileEntityFlag flag = (TileEntityFlag) tileEntity;
        ItemStack color1 = flag.items[0];
        ItemStack color2 = flag.items[1];
        ItemStack color3 = flag.items[2];
        renderStringAndStack(guiBlockOverlay,translator.translateKey("btwaila.tooltip.flag.color").replace("{d}", "1") + "    " +  ((color1 != null) ? translator.translateKey(color1.getItemTranslateKey()) : translator.translateKey("btwaila.noitem")), 0, color1);
        renderStringAndStack(guiBlockOverlay,translator.translateKey("btwaila.tooltip.flag.color").replace("{d}", "2") + "    " +  ((color2 != null) ? translator.translateKey(color2.getItemTranslateKey()) : translator.translateKey("btwaila.noitem")), 0, color2);
        renderStringAndStack(guiBlockOverlay,translator.translateKey("btwaila.tooltip.flag.color").replace("{d}", "3") + "    " +  ((color3 != null) ? translator.translateKey(color3.getItemTranslateKey()) : translator.translateKey("btwaila.noitem")), 0, color3);
    }
    protected void renderStringAndStack(GuiBlockOverlay guiBlockOverlay, String s, int offX, ItemStack stack){
        if (stack != null){
            int y = guiBlockOverlay.getOffY() - 1;
            int x = guiBlockOverlay.getPosX() + 16 + guiBlockOverlay.minecraft.fontRenderer.getStringWidth(translator.translateKey("btwaila.tooltip.flag.color").replace("{d}", "1") + "    ");
            y -= 3;
            GuiBlockOverlay.itemRender.renderItemIntoGUI(guiBlockOverlay.getGame().fontRenderer, guiBlockOverlay.getGame().renderEngine, stack, x, y, 1.0F);
            GuiBlockOverlay.itemRender.renderItemOverlayIntoGUI(guiBlockOverlay.getGame().fontRenderer, guiBlockOverlay.getGame().renderEngine, stack, x, y, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
        };
        guiBlockOverlay.drawStringWithShadow(s, offX);
        guiBlockOverlay.addOffY(4);
    }
}
