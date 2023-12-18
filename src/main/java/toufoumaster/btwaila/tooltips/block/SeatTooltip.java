package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntitySeat;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;

import static toufoumaster.btwaila.BTWaila.translator;

public class SeatTooltip extends TileTooltip<TileEntitySeat> {
    @Override
    public void initTooltip() {
        addClass(TileEntitySeat.class);
    }
    @Override
    public void drawAdvancedTooltip(TileEntitySeat seat, AdvancedInfoComponent advancedInfoComponent) {
        String entityName = AdvancedInfoComponent.getEntityName(seat.getPassenger());

        advancedInfoComponent.drawStringWithShadow(
                translator.translateKey("btwaila.tooltip.seat.passenger")
                        .replace("{name}", entityName), 0);
    }
}
