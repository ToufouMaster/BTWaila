package toufoumaster.btwaila.tooltips;

import net.minecraft.core.block.entity.TileEntity;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;

public abstract class TileTooltip<T> extends Tooltip<T>{
    public void _drawAdvancedTooltip(TileEntity tileEntity, AdvancedInfoComponent advancedInfoComponent){
        drawAdvancedTooltip((T) tileEntity, advancedInfoComponent);
    }
}
