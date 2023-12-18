package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntityBlastFurnace;
import net.minecraft.core.block.entity.TileEntityFurnace;
import net.minecraft.core.item.ItemStack;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;
import toufoumaster.btwaila.util.ProgressBarOptions;

import static toufoumaster.btwaila.BTWaila.translator;

public class FurnaceTooltip extends TileTooltip<TileEntityFurnace> {
    @Override
    public void initTooltip() {
        addClass(TileEntityFurnace.class);
        addClass(TileEntityBlastFurnace.class);
    }
    @Override
    public void drawAdvancedTooltip(TileEntityFurnace furnace, AdvancedInfoComponent advancedInfoComponent) {
        ItemStack input = furnace.getStackInSlot(0);
        ItemStack fuel = furnace.getStackInSlot(1);
        ItemStack output = furnace.getStackInSlot(2);

        ProgressBarOptions options = new ProgressBarOptions().setText(translator.translateKey("btwaila.tooltip.furnace.progress"));
        advancedInfoComponent.drawProgressBarWithText(furnace.getCookProgressScaled(100), 100, options, 0);

        advancedInfoComponent.drawStringWithShadow(translator.translateKey("btwaila.tooltip.furnace.burntime").replace("{current}", String.valueOf(furnace.currentBurnTime)), 0);
        ItemStack[] stacks = new ItemStack[] {input, fuel, output};
        advancedInfoComponent.drawItemList(stacks, 0);
    }
}
