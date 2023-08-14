package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityFlag;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import toufoumaster.btwaila.*;
import toufoumaster.btwaila.gui.GuiBlockOverlay;

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
        I18n stringTranslate = I18n.getInstance();
        TileEntityFlag flag = (TileEntityFlag) tileEntity;
        ItemStack color1 = flag.items[0];
        ItemStack color2 = flag.items[1];
        ItemStack color3 = flag.items[2];
        guiBlockOverlay.drawStringWithShadow("Color 1: "+ ((color1 != null) ? stringTranslate.translateKey(color1.getItemTranslateKey()) : "No Item"), 0);
        guiBlockOverlay.drawStringWithShadow("Color 2: "+ ((color2 != null) ? stringTranslate.translateKey(color2.getItemTranslateKey()) : "No Item"), 0);
        guiBlockOverlay.drawStringWithShadow("Color 3: "+ ((color3 != null) ? stringTranslate.translateKey(color3.getItemTranslateKey()) : "No Item"), 0);
    }
}
