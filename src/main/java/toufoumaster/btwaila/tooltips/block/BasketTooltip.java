package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntityBasket;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import toufoumaster.btwaila.demo.DemoEntry;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;

import java.util.Random;

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
    @Override
    public DemoEntry tooltipDemo(Random random){
        return new DemoEntry(Block.basket, 0, new TileEntityBasket(), new ItemStack[]{Item.basket.getDefaultStack()});
    }
}
