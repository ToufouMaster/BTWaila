package toufoumaster.btwaila.entryplugins.waila;

import org.slf4j.Logger;
import toufoumaster.btwaila.tooltips.block.*;
import toufoumaster.btwaila.tooltips.entity.ChickenTooltip;
import toufoumaster.btwaila.tooltips.entity.MinecartTooltip;
import toufoumaster.btwaila.tooltips.entity.PlayerTooltip;

public class BTWailaPlugin implements BTWailaCustomTooltipPlugin {

    @Override
    public void initializePlugin(Logger logger) {
        logger.info("Loading default tooltips..");
        new InventoryTooltip();
        new FlagTooltip();
        new BasketTooltip();
        new FurnaceTooltip();
        new MobSpawnerTooltip();
        new NoteBlockTooltip();
        new RecordPlayerTooltip();
        new TrommelTooltip();
        new SeatTooltip();
        new JarToolTip();

        new ChickenTooltip();
        new PlayerTooltip();
        new MinecartTooltip();
    }
}
