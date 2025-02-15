package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityTrommel;
import net.minecraft.core.item.ItemStack;
import toufoumaster.btwaila.demo.DemoEntry;
import toufoumaster.btwaila.demo.DemoManager;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;
import toufoumaster.btwaila.util.ProgressBarOptions;

import java.util.Random;

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
        advancedInfoComponent.drawItemList(new ItemStack[] {trommel.getItem(4), trommel.getItem(0), trommel.getItem(1), trommel.getItem(2), trommel.getItem(3)}, 0);
    }
    @Override
    public DemoEntry tooltipDemo(Random random){
        TileEntityTrommel demoTrommel = new TileEntityTrommel();
        demoTrommel.setItem(0, DemoManager.randomStack(random));
        demoTrommel.setItem(1, DemoManager.randomStack(random));
        demoTrommel.setItem(2, DemoManager.randomStack(random));
        demoTrommel.setItem(3, DemoManager.randomStack(random));
        demoTrommel.setItem(4, DemoManager.randomStack(random));
        demoTrommel.currentItemBurnTime = DemoManager.getRandomFuelTime(random);
        return new DemoEntry(Blocks.TROMMEL_ACTIVE, 0, demoTrommel, new ItemStack[]{Blocks.TROMMEL_IDLE.getDefaultStack()});
    }
}
