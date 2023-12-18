package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityBasket;
import toufoumaster.btwaila.BTWaila;
import toufoumaster.btwaila.IBTWailaCustomBlockTooltip;
import toufoumaster.btwaila.TooltipGroup;
import toufoumaster.btwaila.TooltipRegistry;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;

public class BasketTooltip implements IBTWailaCustomBlockTooltip {

    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", TileEntityBasket.class, this);
        tooltipGroup.addTooltip(TileEntityBasket.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(TileEntity tileEntity, AdvancedInfoComponent advancedInfoComponent) {
        TileEntityBasket basket = (TileEntityBasket) tileEntity;
        int max = basket.getMaxUnits();
        int current = basket.getNumUnitsInside();
        advancedInfoComponent.drawStringWithShadow("Stored items: "+current+"/"+max, 0);
    }
}
