package toufoumaster.btwaila.entryplugins.waila;

import org.slf4j.Logger;
import toufoumaster.btwaila.tooltips.EntityTooltip;
import toufoumaster.btwaila.tooltips.TileTooltip;
import toufoumaster.btwaila.tooltips.Tooltip;
import toufoumaster.btwaila.tooltips.TooltipRegistry;
import toufoumaster.btwaila.tooltips.block.*;
import toufoumaster.btwaila.tooltips.entity.ChickenTooltip;
import toufoumaster.btwaila.tooltips.entity.MinecartTooltip;
import toufoumaster.btwaila.tooltips.entity.PlayerTooltip;

public class BTWailaPlugin implements BTWailaCustomTooltipPlugin {
    public static TooltipRegistry registry = TooltipRegistry.getInstance();
    public static TileTooltip<?> INVENTORY = registry.register(new InventoryTooltip());
    public static TileTooltip<?> BASKET = registry.register(new BasketTooltip());
    public static TileTooltip<?> FLAG = registry.register(new FlagTooltip());
    public static TileTooltip<?> FURNACE = registry.register(new FurnaceTooltip());
    public static TileTooltip<?> JAR = registry.register(new JarToolTip());
    public static TileTooltip<?> MOB_SPAWNER = registry.register(new MobSpawnerTooltip());
    public static TileTooltip<?> NOTE_BLOCK = registry.register(new NoteBlockTooltip());
    public static TileTooltip<?> JUKEBOX = registry.register(new RecordPlayerTooltip());
    public static TileTooltip<?> SEAT = registry.register(new SeatTooltip());
    public static TileTooltip<?> TROMMEL = registry.register(new TrommelTooltip());
    public static EntityTooltip<?> CHICKEN = registry.register(new ChickenTooltip());
    public static EntityTooltip<?> MINECART = registry.register(new MinecartTooltip());
    public static EntityTooltip<?> PLAYER = registry.register(new PlayerTooltip());
    private static boolean hasLoaded = false;
    @Override
    public void initializePlugin(TooltipRegistry registry, Logger logger) {
        if (hasLoaded) return;
        logger.info("Loading default tooltips..");
        hasLoaded = true;
    }
}
