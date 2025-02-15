package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityFurnace;
import net.minecraft.core.block.entity.TileEntityFurnaceBlast;
import net.minecraft.core.item.ItemStack;
import toufoumaster.btwaila.demo.DemoEntry;
import toufoumaster.btwaila.demo.DemoManager;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;
import toufoumaster.btwaila.util.Colors;
import toufoumaster.btwaila.util.ProgressBarOptions;
import toufoumaster.btwaila.util.TextureOptions;

import java.util.Random;

import static toufoumaster.btwaila.BTWaila.translator;

public class FurnaceTooltip extends TileTooltip<TileEntityFurnace> {
    @Override
    public void initTooltip() {
        addClass(TileEntityFurnace.class);
        addClass(TileEntityFurnaceBlast.class);
    }
    @Override
    public void drawAdvancedTooltip(TileEntityFurnace furnace, AdvancedInfoComponent advancedInfoComponent) {
        ItemStack input = furnace.getItem(0);
        ItemStack fuel = furnace.getItem(1);
        ItemStack output = furnace.getItem(2);

        ProgressBarOptions options = new ProgressBarOptions(0, translator.translateKey("btwaila.tooltip.furnace.progress"), true, true);
        advancedInfoComponent.drawProgressBarWithText(furnace.getCookProgressScaled(100), 100, options, 0);

        advancedInfoComponent.drawStringWithShadow(translator.translateKey("btwaila.tooltip.furnace.burntime").replace("{current}", String.valueOf(furnace.currentBurnTime)), 0);
        ItemStack[] stacks = new ItemStack[] {input, fuel, output};
        advancedInfoComponent.drawItemList(stacks, 0);
    }
    @Override
    public DemoEntry tooltipDemo(Random random){
        TileEntityFurnace demoFurnace = new TileEntityFurnace();
        demoFurnace.setItem(0, DemoManager.randomStack(random));
        demoFurnace.setItem(1, DemoManager.randomStack(random));
        demoFurnace.setItem(2, DemoManager.randomStack(random));
        demoFurnace.currentBurnTime = DemoManager.getRandomFuelTime(random);
        demoFurnace.currentCookTime = random.nextInt(demoFurnace.maxCookTime);
        return new DemoEntry(Blocks.FURNACE_STONE_ACTIVE, 0, demoFurnace, new ItemStack[]{Blocks.FURNACE_STONE_ACTIVE.getDefaultStack()});
    }
}
