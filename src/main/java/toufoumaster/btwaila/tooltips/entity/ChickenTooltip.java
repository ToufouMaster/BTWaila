package toufoumaster.btwaila.tooltips.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.EntityChicken;
import toufoumaster.btwaila.BTWaila;
import toufoumaster.btwaila.IBTWailaCustomEntityTooltip;
import toufoumaster.btwaila.TooltipGroup;
import toufoumaster.btwaila.TooltipRegistry;
import toufoumaster.btwaila.gui.GuiBlockOverlay;

public class ChickenTooltip implements IBTWailaCustomEntityTooltip {
    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", EntityChicken.class, this);
        tooltipGroup.addTooltip(EntityChicken.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(Entity entity, GuiBlockOverlay guiBlockOverlay) {
        EntityChicken chicken = (EntityChicken) entity;
        String text = String.format("Egg Delay: %d", chicken.timeUntilNextEgg);
        guiBlockOverlay.drawStringWithShadow(text, 0);
    }
}
