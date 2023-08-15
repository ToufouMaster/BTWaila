package toufoumaster.btwaila;

import org.slf4j.Logger;
import toufoumaster.btwaila.tooltips.block.*;
import toufoumaster.btwaila.tooltips.entity.MonsterTooltip;
import toufoumaster.btwaila.tooltips.entity.PlayerTooltip;

public class BTWailaPlugin implements BTWailaCustomTootltipPlugin {

    @Override
    public void initializePlugin(Logger logger) {
        logger.info("Loading default tooltips..");
        new InventoryTooltip().addTooltip();
        new FlagTooltip().addTooltip();
        new BasketTooltip().addTooltip();
        new FurnaceTooltip().addTooltip();
        new MobSpawnerTooltip().addTooltip();
        new NoteBlockTooltip().addTooltip();
        new RecordPlayerTooltip().addTooltip();
        new TrommelTooltip().addTooltip();

        new MonsterTooltip().addTooltip();
        new PlayerTooltip().addTooltip();
    }
}
