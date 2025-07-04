package toufoumaster.btwaila.tooltips.entity;

import net.minecraft.core.entity.animal.MobPig;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.EntityTooltip;

import static toufoumaster.btwaila.BTWaila.translator;

public class PigTooltip extends EntityTooltip<MobPig> {
    @Override
    public void initTooltip() {
        addClass(MobPig.class);
    }

    @Override
    public void drawAdvancedTooltip(MobPig interfaceObject, AdvancedInfoComponent advancedInfoComponent) {
        if (interfaceObject.getSaddled()){
            String entityName = AdvancedInfoComponent.getEntityName(interfaceObject.getPassenger());

            advancedInfoComponent.drawStringWithShadow(
                    translator.translateKey("btwaila.tooltip.pig.passenger")
                            .replace("{name}", entityName), 0);
        }
    }
}
