package toufoumaster.btwaila.tooltips.entity;

import net.minecraft.core.entity.animal.EntityChicken;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.EntityTooltip;

import static toufoumaster.btwaila.BTWaila.translator;

public class ChickenTooltip extends EntityTooltip<EntityChicken> {
    @Override
    public void initTooltip() {
        addClass(EntityChicken.class);
    }

    @Override
    public void drawAdvancedTooltip(EntityChicken chicken, AdvancedInfoComponent advancedInfoComponent) {
        String text = translator.translateKey("btwaila.tooltip.chicken.egg").replace("{timer}", String.valueOf(chicken.timeUntilNextEgg/20));
        advancedInfoComponent.drawStringWithShadow(text, 0);
    }
}
