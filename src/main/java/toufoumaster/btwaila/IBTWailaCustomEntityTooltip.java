package toufoumaster.btwaila;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import toufoumaster.btwaila.gui.GuiBlockOverlay;

public interface IBTWailaCustomEntityTooltip extends IBTWailaCustomTooltip {
    void addTooltip();

    void drawAdvancedTooltip(Entity entity, GuiBlockOverlay guiBlockOverlay);
}
