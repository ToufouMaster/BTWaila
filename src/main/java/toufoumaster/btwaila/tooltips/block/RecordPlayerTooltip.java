package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityRecordPlayer;
import toufoumaster.btwaila.*;
import toufoumaster.btwaila.gui.GuiBlockOverlay;

public class RecordPlayerTooltip implements IBTWailaCustomBlockTooltip {

    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", TileEntityRecordPlayer.class, this);
        tooltipGroup.addTooltip(TileEntityRecordPlayer.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(TileEntity tileEntity, GuiBlockOverlay guiBlockOverlay) {
        TileEntityRecordPlayer recordPlayer = (TileEntityRecordPlayer) tileEntity;
        guiBlockOverlay.drawStringWithShadow("Disk id: "+recordPlayer.record, 0);
    }
}
