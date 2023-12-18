package toufoumaster.btwaila.tooltips.interfaces;

import net.minecraft.core.entity.Entity;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;

public interface IBTWailaCustomEntityTooltip extends IBTWailaCustomTooltip {
    void addTooltip();

    void drawAdvancedTooltip(Entity entity, AdvancedInfoComponent advancedInfoComponent);
}
