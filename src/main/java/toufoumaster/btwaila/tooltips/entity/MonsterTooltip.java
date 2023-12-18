package toufoumaster.btwaila.tooltips.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.*;
import toufoumaster.btwaila.*;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TooltipGroup;
import toufoumaster.btwaila.tooltips.TooltipRegistry;
import toufoumaster.btwaila.tooltips.interfaces.IBTWailaCustomEntityTooltip;

public class MonsterTooltip implements IBTWailaCustomEntityTooltip {

    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", EntityMonster.class, this);
        tooltipGroup.addTooltip(EntityZombie.class);
        tooltipGroup.addTooltip(EntitySpider.class);
        tooltipGroup.addTooltip(EntityArmoredZombie.class);
        tooltipGroup.addTooltip(EntityCreeper.class);
        tooltipGroup.addTooltip(EntityGhast.class);
        tooltipGroup.addTooltip(EntityPigZombie.class);
        tooltipGroup.addTooltip(EntityScorpion.class);
        tooltipGroup.addTooltip(EntitySkeleton.class);
        tooltipGroup.addTooltip(EntitySlime.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(Entity entity, AdvancedInfoComponent advancedInfoComponent) {
    }
}
