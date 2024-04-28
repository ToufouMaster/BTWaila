package toufoumaster.btwaila.entryplugins.waila;

import net.minecraft.core.block.entity.TileEntityBasket;
import net.minecraft.core.block.entity.TileEntityFlag;
import net.minecraft.core.block.entity.TileEntityFlowerJar;
import net.minecraft.core.block.entity.TileEntityFurnace;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.block.entity.TileEntityNote;
import net.minecraft.core.block.entity.TileEntityRecordPlayer;
import net.minecraft.core.block.entity.TileEntitySeat;
import net.minecraft.core.block.entity.TileEntitySign;
import net.minecraft.core.block.entity.TileEntityTrommel;
import net.minecraft.core.entity.animal.EntityChicken;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.vehicle.EntityMinecart;
import net.minecraft.core.player.inventory.IInventory;
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
import toufoumaster.btwaila.tooltips.block.RecordPlayerTooltip;
import toufoumaster.btwaila.tooltips.block.SeatTooltip;
import toufoumaster.btwaila.tooltips.block.SignTooltip;
import toufoumaster.btwaila.tooltips.block.TrommelTooltip;
import toufoumaster.btwaila.tooltips.entity.ChickenTooltip;
import toufoumaster.btwaila.tooltips.entity.MinecartTooltip;
import toufoumaster.btwaila.tooltips.entity.PlayerTooltip;

public class BTWailaPlugin implements BTWailaCustomTooltipPlugin {
    public static TooltipRegistry registry = TooltipRegistry.getInstance();
    public static TileTooltip<IInventory> INVENTORY = registry.register(new InventoryTooltip());
    public static TileTooltip<TileEntityBasket> BASKET = registry.register(new BasketTooltip());
    public static TileTooltip<TileEntityFlag> FLAG = registry.register(new FlagTooltip());
    public static TileTooltip<TileEntityFurnace> FURNACE = registry.register(new FurnaceTooltip());
    public static TileTooltip<TileEntityFlowerJar> JAR = registry.register(new JarToolTip());
    public static TileTooltip<TileEntityMobSpawner> MOB_SPAWNER = registry.register(new MobSpawnerTooltip());
    public static TileTooltip<TileEntityNote> NOTE_BLOCK = registry.register(new NoteBlockTooltip());
    public static TileTooltip<TileEntityRecordPlayer> JUKEBOX = registry.register(new RecordPlayerTooltip());
    public static TileTooltip<TileEntitySeat> SEAT = registry.register(new SeatTooltip());
    public static TileTooltip<TileEntitySign> SIGN = registry.register(new SignTooltip());
    public static TileTooltip<TileEntityTrommel> TROMMEL = registry.register(new TrommelTooltip());
    public static EntityTooltip<EntityChicken> CHICKEN = registry.register(new ChickenTooltip());
    public static EntityTooltip<EntityMinecart> MINECART = registry.register(new MinecartTooltip());
    public static EntityTooltip<EntityPlayer> PLAYER = registry.register(new PlayerTooltip());
    private static boolean hasLoaded = false;
    @Override
    public void initializePlugin(TooltipRegistry registry, Logger logger) {
        if (hasLoaded) return;
        logger.info("Loading default tooltips..");
        hasLoaded = true;
    }
}
