package toufoumaster.btwaila.tooltips;

import net.minecraft.core.entity.Entity;
import toufoumaster.btwaila.BTWaila;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;


public abstract class EntityTooltip<T> extends Tooltip<T> {
    public EntityTooltip(){
        TooltipRegistry.entityTooltips.add(this);
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        initTooltip();
    }
    @Override
    abstract public void initTooltip();
    @Override
    abstract public void drawAdvancedTooltip(T entityInterface, AdvancedInfoComponent advancedInfoComponent);
    public void _drawAdvancedTooltip(Entity entity, AdvancedInfoComponent advancedInfoComponent){
        drawAdvancedTooltip((T) entity, advancedInfoComponent);
    }
}
