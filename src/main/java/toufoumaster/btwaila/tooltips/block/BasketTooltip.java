package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntityBasket;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;

import static toufoumaster.btwaila.BTWaila.translator;

public class BasketTooltip extends TileTooltip<TileEntityBasket> {
    @Override
    public void initTooltip() {
        addClass(TileEntityBasket.class);
    }
    @Override
    public void drawAdvancedTooltip(TileEntityBasket basket, AdvancedInfoComponent advancedInfoComponent) {
        int max = basket.getMaxUnits();
        int current = basket.getNumUnitsInside();
        advancedInfoComponent.drawStringWithShadow(translator.translateKey("btwaila.tooltip.minecart.storage")
                .replace("{current}", String.valueOf(current))
                .replace("{max}", String.valueOf(max)), 0);
    }
}
