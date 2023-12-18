package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityBlastFurnace;
import net.minecraft.core.block.entity.TileEntityFurnace;
import net.minecraft.core.item.ItemStack;
import toufoumaster.btwaila.*;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.util.ProgressBarOptions;

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
    public void drawAdvancedTooltip(TileEntity tileEntity, AdvancedInfoComponent advancedInfoComponent) {
        TileEntityFurnace furnace = (TileEntityFurnace) tileEntity;
        ItemStack input = furnace.getStackInSlot(0);
        ItemStack fuel = furnace.getStackInSlot(1);
        ItemStack output = furnace.getStackInSlot(2);

        ProgressBarOptions options = new ProgressBarOptions().setText("Progress: ");
        advancedInfoComponent.drawProgressBarWithText(furnace.getCookProgressScaled(100), 100, options, 32);

        advancedInfoComponent.drawStringWithShadow("Burn time: "+furnace.currentBurnTime+"t", 0);
        ItemStack[] stacks = new ItemStack[] {input, fuel, output};
        advancedInfoComponent.drawItemList(stacks, 0);
    }
}
