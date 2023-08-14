package toufoumaster.btwaila;

import net.minecraft.core.block.entity.TileEntity;
import toufoumaster.btwaila.gui.GuiBlockOverlay;

public interface IBTWailaCustomBlockTooltip extends IBTWailaCustomTooltip {
    void addTooltip();

    void drawAdvancedTooltip(TileEntity tileEntity, GuiBlockOverlay guiBlockOverlay);
}
