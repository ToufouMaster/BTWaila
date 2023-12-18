package toufoumaster.btwaila.tooltips.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.EntityChicken;
import toufoumaster.btwaila.BTWaila;
import toufoumaster.btwaila.tooltips.interfaces.IBTWailaCustomEntityTooltip;
import toufoumaster.btwaila.tooltips.TooltipGroup;
import toufoumaster.btwaila.tooltips.TooltipRegistry;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;

public class ChickenTooltip implements IBTWailaCustomEntityTooltip {
    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", EntityChicken.class, this);
        tooltipGroup.addTooltip(EntityChicken.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(Entity entity, AdvancedInfoComponent advancedInfoComponent) {
        EntityChicken chicken = (EntityChicken) entity;
        String text = String.format("Next egg: %d seconds", chicken.timeUntilNextEgg/20);
        advancedInfoComponent.drawStringWithShadow(text, 0);
    }
}
