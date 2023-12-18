package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntityTrommel;
import net.minecraft.core.item.ItemStack;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;
import toufoumaster.btwaila.util.ProgressBarOptions;

import static toufoumaster.btwaila.BTWaila.translator;

public class TrommelTooltip extends TileTooltip<TileEntityTrommel> {
    @Override
    public void initTooltip() {
        addClass(TileEntityTrommel.class);
    }
    @Override
    public void drawAdvancedTooltip(TileEntityTrommel trommel, AdvancedInfoComponent advancedInfoComponent) {
        ProgressBarOptions options = new ProgressBarOptions().setText(translator.translateKey("btwaila.tooltip.trommel.progress"));
        advancedInfoComponent.drawProgressBarWithText((int) trommel.getCookProgressPercent(100), 100, options, 0);
        advancedInfoComponent.drawItemList(new ItemStack[] {trommel.getStackInSlot(4), trommel.getStackInSlot(0), trommel.getStackInSlot(1), trommel.getStackInSlot(2), trommel.getStackInSlot(3)}, 0);
    }
}
