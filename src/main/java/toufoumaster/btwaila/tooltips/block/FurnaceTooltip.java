package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityBlastFurnace;
import net.minecraft.core.block.entity.TileEntityFurnace;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import toufoumaster.btwaila.*;
import toufoumaster.btwaila.gui.GuiBlockOverlay;

public class FurnaceTooltip implements IBTWailaCustomBlockTooltip {

    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", TileEntityFurnace.class, this);
        tooltipGroup.addTooltip(TileEntityFurnace.class);
        tooltipGroup.addTooltip(TileEntityBlastFurnace.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(TileEntity tileEntity, GuiBlockOverlay guiBlockOverlay) {
        I18n stringTranslate = I18n.getInstance();
        TileEntityFurnace furnace = (TileEntityFurnace) tileEntity;
        ItemStack input = furnace.getStackInSlot(0);
        ItemStack fuel = furnace.getStackInSlot(1);
        ItemStack output = furnace.getStackInSlot(2);
        guiBlockOverlay.drawStringWithShadow("Burn time: "+furnace.currentBurnTime+"t", 0);
        guiBlockOverlay.drawStringWithShadow("Progress: "+furnace.getCookProgressScaled(100)+"%", 0);
        guiBlockOverlay.drawStringWithShadow("Input item: "+((input != null) ? stringTranslate.translateKey(input.getItemTranslateKey()) : "No item"), 0);
        guiBlockOverlay.drawStringWithShadow("Fuel item: "+((fuel != null) ? stringTranslate.translateKey(fuel.getItemTranslateKey()) : "No item"), 0);
        guiBlockOverlay.drawStringWithShadow("Output item: "+ ((output != null) ? stringTranslate.translateKey(output.getItemTranslateKey()) : "No item"), 0);
    }
}
