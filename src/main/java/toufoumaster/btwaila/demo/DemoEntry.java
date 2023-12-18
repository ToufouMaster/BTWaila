package toufoumaster.btwaila.demo;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityBasket;
import net.minecraft.core.block.entity.TileEntityFlag;
import net.minecraft.core.block.entity.TileEntityFlowerJar;
import net.minecraft.core.block.entity.TileEntityFurnace;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.block.entity.TileEntityNote;
import net.minecraft.core.block.entity.TileEntityRecordPlayer;
import net.minecraft.core.block.entity.TileEntitySeat;
import net.minecraft.core.block.entity.TileEntityTrommel;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.animal.EntityChicken;
import net.minecraft.core.entity.animal.EntityPig;
import net.minecraft.core.entity.monster.EntityHuman;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.monster.EntitySkeleton;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.vehicle.EntityMinecart;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.server.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class DemoEntry {
    public static int demoOffset = 0;
    public static final ArrayList<DemoEntry> demoEntries = new ArrayList<>();
    static {
        long seed = System.currentTimeMillis() / (1000 * 60 * 60 * 24);
        Random random = new Random(seed);
        String[] names = new String[]{"Useless7695", "MaggAndGeez", "jonkadelic", "AnActualSign", "Herobrine", "Bfells"};

        TileEntityBasket demoBasket = new TileEntityBasket();
        demoEntries.add(new DemoEntry(Block.basket, 0, demoBasket, new ItemStack[]{Item.basket.getDefaultStack()}));

        TileEntityFlag demoFlag = new TileEntityFlag();
        demoFlag.items = new ItemStack[]{new ItemStack(Item.dye, 1, random.nextInt(16)), new ItemStack(Item.dye, 1, random.nextInt(16)), new ItemStack(Item.dye, 1, random.nextInt(16))};
        demoFlag.owner = names[random.nextInt(names.length)];
        demoEntries.add(new DemoEntry(Block.flag, 0, demoFlag, new ItemStack[]{Item.flag.getDefaultStack()}));

        TileEntityFurnace demoFurnace = new TileEntityFurnace();
        demoFurnace.setInventorySlotContents(0, TileEntityDemoChest.randomStack(random));
        demoFurnace.setInventorySlotContents(1, TileEntityDemoChest.randomStack(random));
        demoFurnace.setInventorySlotContents(2, TileEntityDemoChest.randomStack(random));
        Map<Integer, Integer> fuelList = LookupFuelFurnace.instance.getFuelList();
        int fuelSize = fuelList.size();
        int randomIndex = random.nextInt(fuelSize);
        demoFurnace.currentBurnTime = random.nextInt((int) fuelList.values().toArray()[randomIndex]);
        demoFurnace.currentCookTime = random.nextInt(demoFurnace.maxCookTime);
        demoEntries.add(new DemoEntry(Block.furnaceStoneActive, 0, demoFurnace, new ItemStack[]{Block.furnaceStoneIdle.getDefaultStack()}));

        TileEntityDemoChest demoChest = new TileEntityDemoChest((int) seed);
        Block chest = Block.chestPlanksOakPainted;
        demoEntries.add(new DemoEntry(chest, 8 * 16, demoChest, new ItemStack[]{new ItemStack(chest, 1, 8 * 16)}));

        TileEntityFlowerJar demoJar = new TileEntityFlowerJar();
        Block jar = Block.jarGlass;
        demoEntries.add(new DemoEntry(jar, 0, demoJar, new ItemStack[]{Item.jar.getDefaultStack()}));

        TileEntityMobSpawner demoSpawner = new TileEntityMobSpawner();
        String[] entityIds = EntityDispatcher.keyToClassMap.keySet().toArray(new String[0]);
        String id = entityIds[random.nextInt(entityIds.length)];
        demoSpawner.setMobId(id);
        Block spawner = Block.mobspawner;
        demoEntries.add(new DemoEntry(spawner, 0, demoSpawner, new ItemStack[]{spawner.getDefaultStack()}));

        TileEntityNote demoNote = new TileEntityNote();
        demoNote.note = (byte) random.nextInt(25);
        Block noteBlock = Block.noteblock;
        demoEntries.add(new DemoEntry(noteBlock, 0, demoNote, new ItemStack[]{noteBlock.getDefaultStack()}));

        TileEntityRecordPlayer demoJukeBox = new TileEntityRecordPlayer();
        demoJukeBox.record = Item.record13.id + random.nextInt(11);
        Block jukeBox = Block.jukebox;
        demoEntries.add(new DemoEntry(jukeBox, 0, demoJukeBox, new ItemStack[]{jukeBox.getDefaultStack()}));

        TileEntitySeat demoSeat = new TileEntitySeat();
        EntityLiving demoHuman = new EntityHuman(null);
        demoHuman.nickname = names[random.nextInt(names.length)];
        demoSeat.setPassenger(demoHuman);
        Block seat = Block.seat;
        demoEntries.add(new DemoEntry(seat, 0, demoSeat, new ItemStack[]{seat.getDefaultStack()}));

        TileEntityTrommel demoTrommel = new TileEntityTrommel();
        demoTrommel.setInventorySlotContents(0, TileEntityDemoChest.randomStack(random));
        demoTrommel.setInventorySlotContents(1, TileEntityDemoChest.randomStack(random));
        demoTrommel.setInventorySlotContents(2, TileEntityDemoChest.randomStack(random));
        demoTrommel.setInventorySlotContents(3, TileEntityDemoChest.randomStack(random));
        demoTrommel.setInventorySlotContents(4, TileEntityDemoChest.randomStack(random));
        demoTrommel.currentItemBurnTime = random.nextInt((int) fuelList.values().toArray()[randomIndex]);
        Block trommel = Block.trommelActive;
        demoEntries.add(new DemoEntry(trommel, 0, demoTrommel, new ItemStack[]{Block.trommelIdle.getDefaultStack()}));

        EntityPig demoPig = new EntityPig(null);
        demoEntries.add(new DemoEntry(demoPig));

        EntityChicken demoChicken = new EntityChicken(null);
        demoEntries.add(new DemoEntry(demoChicken));

        EntitySkeleton demoSkeleton = new EntitySkeleton(null);
        demoEntries.add(new DemoEntry(demoSkeleton));

        EntityMinecart demoMinecart = new EntityMinecart(null);
        demoMinecart.minecartType = random.nextInt(3);
        demoEntries.add(new DemoEntry(demoMinecart));
    }
    public static DemoEntry getCurrentEntry(){
        return demoEntries.get(demoOffset % demoEntries.size());
    }
    public Block block;
    public int meta;
    public Entity entity;
    public TileEntity tileEntity;
    public ItemStack[] drops;
    public DemoEntry(Block block, int meta, TileEntity tileEntity, ItemStack[] drops){
        this.block = block;
        this.meta = meta;
        this.tileEntity = tileEntity;
        this.drops = drops;
    }
    public DemoEntry(Entity entity){
        this.entity = entity;
    }
}
