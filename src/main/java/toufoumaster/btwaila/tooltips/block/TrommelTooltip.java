package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityTrommel;
import net.minecraft.core.item.ItemStack;
import toufoumaster.btwaila.BTWaila;
import toufoumaster.btwaila.tooltips.interfaces.IBTWailaCustomBlockTooltip;
import toufoumaster.btwaila.tooltips.TooltipGroup;
import toufoumaster.btwaila.tooltips.TooltipRegistry;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.util.ProgressBarOptions;

import static toufoumaster.btwaila.BTWaila.translator;

public class TrommelTooltip implements IBTWailaCustomBlockTooltip {

    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", TileEntityTrommel.class, this);
        tooltipGroup.addTooltip(TileEntityTrommel.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(TileEntity tileEntity, AdvancedInfoComponent advancedInfoComponent) {
        TileEntityTrommel trommel = (TileEntityTrommel) tileEntity;

        ProgressBarOptions options = new ProgressBarOptions().setText(translator.translateKey("btwaila.tooltip.trommel.progress"));
        advancedInfoComponent.drawProgressBarWithText((int) trommel.getCookProgressPercent(100), 100, options, 0);
        advancedInfoComponent.drawItemList(new ItemStack[] {trommel.getStackInSlot(4), trommel.getStackInSlot(0), trommel.getStackInSlot(1), trommel.getStackInSlot(2), trommel.getStackInSlot(3)}, 0);
    }
}
