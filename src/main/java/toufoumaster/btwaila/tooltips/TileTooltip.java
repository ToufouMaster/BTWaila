package toufoumaster.btwaila.tooltips;

import net.minecraft.core.block.entity.TileEntity;
import toufoumaster.btwaila.BTWaila;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;

public abstract class TileTooltip<T> extends Tooltip<T>{
    public TileTooltip(){
        TooltipRegistry.tileTooltips.add(this);
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        initTooltip();
    }
    @Override
    abstract public void initTooltip();

    @Override
    abstract public void drawAdvancedTooltip(T tileEntityInterface, AdvancedInfoComponent advancedInfoComponent);
    public void _drawAdvancedTooltip(TileEntity tileEntity, AdvancedInfoComponent advancedInfoComponent){
        drawAdvancedTooltip((T) tileEntity, advancedInfoComponent);
    }
}
