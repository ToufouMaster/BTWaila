package toufoumaster.btwaila.entryplugins.waila;

import org.slf4j.Logger;
import toufoumaster.btwaila.tooltips.TooltipRegistry;
import toufoumaster.btwaila.tooltips.block.*;
import toufoumaster.btwaila.tooltips.entity.ChickenTooltip;
import toufoumaster.btwaila.tooltips.entity.MinecartTooltip;
import toufoumaster.btwaila.tooltips.entity.PlayerTooltip;

public class BTWailaPlugin implements BTWailaCustomTooltipPlugin {

    @Override
    public void initializePlugin(TooltipRegistry registry, Logger logger) {
        logger.info("Loading default tooltips..");
        registry.register(new InventoryTooltip());
        registry.register(new BasketTooltip());
        registry.register(new FlagTooltip());
        registry.register(new FurnaceTooltip());
        registry.register(new JarToolTip());
        registry.register(new MobSpawnerTooltip());
        registry.register(new NoteBlockTooltip());
        registry.register(new RecordPlayerTooltip());
        registry.register(new SeatTooltip());
        registry.register(new TrommelTooltip());

        registry.register(new ChickenTooltip());
        registry.register(new MinecartTooltip());
        registry.register(new PlayerTooltip());
    }
}
