package toufoumaster.btwaila.entryplugins.waila;

import net.minecraft.core.block.entity.*;
import net.minecraft.core.entity.EntityPainting;
import net.minecraft.core.entity.animal.MobChicken;
import net.minecraft.core.entity.animal.MobPig;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.vehicle.EntityBoat;
import net.minecraft.core.entity.vehicle.EntityMinecart;
import net.minecraft.core.player.inventory.container.Container;
import org.slf4j.Logger;
import toufoumaster.btwaila.tooltips.EntityTooltip;
import toufoumaster.btwaila.tooltips.TileTooltip;
import toufoumaster.btwaila.tooltips.TooltipRegistry;
import toufoumaster.btwaila.tooltips.block.BasketTooltip;
import toufoumaster.btwaila.tooltips.block.FlagTooltip;
import toufoumaster.btwaila.tooltips.block.FurnaceTooltip;
import toufoumaster.btwaila.tooltips.block.InventoryTooltip;
import toufoumaster.btwaila.tooltips.block.JarToolTip;
import toufoumaster.btwaila.tooltips.block.MobSpawnerTooltip;
import toufoumaster.btwaila.tooltips.block.NoteBlockTooltip;
import toufoumaster.btwaila.tooltips.block.JukeboxTooltip;
import toufoumaster.btwaila.tooltips.block.SeatTooltip;
import toufoumaster.btwaila.tooltips.block.SignTooltip;
import toufoumaster.btwaila.tooltips.block.TrommelTooltip;
import toufoumaster.btwaila.tooltips.entity.BoatTooltip;
import toufoumaster.btwaila.tooltips.entity.ChickenTooltip;
import toufoumaster.btwaila.tooltips.entity.MinecartTooltip;
import toufoumaster.btwaila.tooltips.entity.PaintingTooltip;
import toufoumaster.btwaila.tooltips.entity.PigTooltip;
import toufoumaster.btwaila.tooltips.entity.PlayerTooltip;

@SuppressWarnings("unused")
public class BTWailaPlugin implements BTWailaCustomTooltipPlugin {
    public static TooltipRegistry registry = TooltipRegistry.getInstance();
    public static TileTooltip<Container> INVENTORY = registry.register(new InventoryTooltip());
    public static TileTooltip<TileEntityBasket> BASKET = registry.register(new BasketTooltip());
    public static TileTooltip<TileEntityFlag> FLAG = registry.register(new FlagTooltip());
    public static TileTooltip<TileEntityFurnace> FURNACE = registry.register(new FurnaceTooltip());
    public static TileTooltip<TileEntityFlowerJar> JAR = registry.register(new JarToolTip());
    public static TileTooltip<TileEntityMobSpawner> MOB_SPAWNER = registry.register(new MobSpawnerTooltip());
    public static TileTooltip<TileEntityNoteblock> NOTE_BLOCK = registry.register(new NoteBlockTooltip());
    public static TileTooltip<TileEntityJukebox> JUKEBOX = registry.register(new JukeboxTooltip());
    public static TileTooltip<TileEntitySeat> SEAT = registry.register(new SeatTooltip());
    public static TileTooltip<TileEntitySign> SIGN = registry.register(new SignTooltip());
    public static TileTooltip<TileEntityTrommel> TROMMEL = registry.register(new TrommelTooltip());
    public static EntityTooltip<MobChicken> CHICKEN = registry.register(new ChickenTooltip());
    public static EntityTooltip<EntityBoat> BOAT = registry.register(new BoatTooltip());
    public static EntityTooltip<MobPig> PIG = registry.register(new PigTooltip());
    public static EntityTooltip<EntityPainting> PAINTING = registry.register(new PaintingTooltip());
    public static EntityTooltip<EntityMinecart> MINECART = registry.register(new MinecartTooltip());
    public static EntityTooltip<Player> PLAYER = registry.register(new PlayerTooltip());
    private static boolean hasLoaded = false;
    @Override
    public void initializePlugin(TooltipRegistry registry, Logger logger) {
        if (hasLoaded) return;
        logger.info("Loading default tooltips..");
        hasLoaded = true;
    }
}
