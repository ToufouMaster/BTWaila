package toufoumaster.btwaila.tooltips.entity;

import net.minecraft.core.entity.vehicle.EntityBoat;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.EntityTooltip;

import static toufoumaster.btwaila.BTWaila.translator;

public class BoatTooltip extends EntityTooltip<EntityBoat> {
    @Override
    public void initTooltip() {
        addClass(EntityBoat.class);
    }

    @Override
    public void drawAdvancedTooltip(EntityBoat interfaceObject, AdvancedInfoComponent advancedInfoComponent) {
        String entityName = AdvancedInfoComponent.getEntityName(interfaceObject.getPassenger());

        advancedInfoComponent.drawStringWithShadow(
                translator.translateKey("btwaila.tooltip.boat.passenger")
                        .replace("{name}", entityName), 0);
    }
}
