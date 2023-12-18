package toufoumaster.btwaila.tooltips.interfaces;

import net.minecraft.core.block.entity.TileEntity;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;

public interface IBTWailaCustomBlockTooltip extends IBTWailaCustomTooltip {
    void addTooltip();

    void drawAdvancedTooltip(TileEntity tileEntity, AdvancedInfoComponent advancedInfoComponent);
}
