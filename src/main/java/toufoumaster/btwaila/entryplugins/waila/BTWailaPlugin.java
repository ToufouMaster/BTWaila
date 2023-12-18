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
        registry.registerTooltip(new InventoryTooltip());
        registry.registerTooltip(new FlagTooltip());
        registry.registerTooltip(new BasketTooltip());
        registry.registerTooltip(new FurnaceTooltip());
        registry.registerTooltip(new MobSpawnerTooltip());
        registry.registerTooltip(new NoteBlockTooltip());
        registry.registerTooltip(new RecordPlayerTooltip());
        registry.registerTooltip(new TrommelTooltip());
        registry.registerTooltip(new SeatTooltip());
        registry.registerTooltip(new JarToolTip());

        registry.registerTooltip(new ChickenTooltip());
        registry.registerTooltip(new PlayerTooltip());
        registry.registerTooltip(new MinecartTooltip());
    }
}
