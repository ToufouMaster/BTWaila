package toufoumaster.btwaila.tooltips;

import net.minecraft.core.block.entity.TileEntity;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;

public abstract class TileTooltip<T> extends Tooltip<T> {
    protected static final String KEY_TILE_ENTITY_OWNER = "btwaila.tooltip.tile_entity.owner";
    public void _drawAdvancedTooltip(TileEntity tileEntity, AdvancedInfoComponent advancedInfoComponent){
        drawAdvancedTooltip((T) tileEntity, advancedInfoComponent);
    }
}
