package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntitySeat;
import toufoumaster.btwaila.BTWaila;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TooltipGroup;
import toufoumaster.btwaila.tooltips.TooltipRegistry;
import toufoumaster.btwaila.tooltips.interfaces.IBTWailaCustomBlockTooltip;

import static toufoumaster.btwaila.BTWaila.translator;

public class SeatTooltip implements IBTWailaCustomBlockTooltip {
    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", TileEntitySeat.class, this);
        tooltipGroup.addTooltip(TileEntitySeat.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(TileEntity tileEntity, AdvancedInfoComponent advancedInfoComponent) {
        TileEntitySeat seat = (TileEntitySeat) tileEntity;
        String entityName = AdvancedInfoComponent.getEntityName(seat.getPassenger());

        advancedInfoComponent.drawStringWithShadow(
                translator.translateKey("btwaila.tooltip.minecart.passenger")
                        .replace("{name}", entityName), 0);
    }
}
