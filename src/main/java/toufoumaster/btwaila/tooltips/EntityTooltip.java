package toufoumaster.btwaila.tooltips;

import net.minecraft.core.entity.Entity;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;


public abstract class EntityTooltip<T> extends Tooltip<T> {
    public void _drawAdvancedTooltip(Entity entity, AdvancedInfoComponent advancedInfoComponent){
        drawAdvancedTooltip((T) entity, advancedInfoComponent);
    }
}
