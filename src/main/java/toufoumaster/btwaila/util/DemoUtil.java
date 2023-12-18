package toufoumaster.btwaila.util;

import net.minecraft.core.block.Block;
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
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.animal.EntityChicken;
import net.minecraft.core.entity.animal.EntityPig;
import net.minecraft.core.entity.monster.EntityHuman;
import net.minecraft.core.entity.monster.EntitySkeleton;
import net.minecraft.core.entity.vehicle.EntityMinecart;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import toufoumaster.btwaila.demo.DemoEntry;
import toufoumaster.btwaila.demo.TileEntityDemoChest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DemoUtil {
    public static final List<Item> allRealItems = new ArrayList<>();
    static {
        for (int i = 0; i < Item.itemsList.length; i++) {
            if (Item.itemsList[i] != null){
                allRealItems.add(Item.itemsList[i]);
            }
        }
    }
    public static int demoOffset = 0;
    public static final List<String> names = new ArrayList<String>(){{
        add("Useless7695");
        add("MaggAndGeez");
        add("jonkadelic");
        add("AnActualSign");
        add("Herobrine");
        add("Bfells");
    }};
    public static final ArrayList<DemoEntry> demoEntries = new ArrayList<>();
    public static final Map<Integer, Integer> fuelList = LookupFuelFurnace.instance.getFuelList();
    static {
        long seed = System.currentTimeMillis() / (1000 * 60 * 60 * 24);
        Random random = new Random(seed);


        TileEntityBasket demoBasket = new TileEntityBasket();
        demoEntries.add(new DemoEntry(Block.basket, 0, demoBasket, new ItemStack[]{Item.basket.getDefaultStack()}));

        TileEntityFlag demoFlag = new TileEntityFlag();
        demoFlag.items = new ItemStack[]{new ItemStack(Item.dye, 1, random.nextInt(16)), new ItemStack(Item.dye, 1, random.nextInt(16)), new ItemStack(Item.dye, 1, random.nextInt(16))};
        demoFlag.owner = getRandomName(random);
        demoEntries.add(new DemoEntry(Block.flag, 0, demoFlag, new ItemStack[]{Item.flag.getDefaultStack()}));

        TileEntityFurnace demoFurnace = new TileEntityFurnace();
        demoFurnace.setInventorySlotContents(0, DemoUtil.randomStack(random));
        demoFurnace.setInventorySlotContents(1, DemoUtil.randomStack(random));
        demoFurnace.setInventorySlotContents(2, DemoUtil.randomStack(random));
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
        demoHuman.nickname = getRandomName(random);
        demoSeat.setPassenger(demoHuman);
        Block seat = Block.seat;
        demoEntries.add(new DemoEntry(seat, 0, demoSeat, new ItemStack[]{seat.getDefaultStack()}));

        TileEntityTrommel demoTrommel = new TileEntityTrommel();
        demoTrommel.setInventorySlotContents(0, randomStack(random));
        demoTrommel.setInventorySlotContents(1, randomStack(random));
        demoTrommel.setInventorySlotContents(2, randomStack(random));
        demoTrommel.setInventorySlotContents(3, randomStack(random));
        demoTrommel.setInventorySlotContents(4, randomStack(random));
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
    public static ItemStack randomStack(Random random){
        Item item = allRealItems.get(random.nextInt(allRealItems.size()));
        int stackSize;
        int meta;
        if (item.getItemStackLimit() > 1){
            stackSize = random.nextInt(item.getItemStackLimit() - 1) + 1;
        } else {
            stackSize = 1;
        }
        if (item.getMaxDamage() > 0){
            meta = random.nextInt(item.getMaxDamage());
        } else {
            meta = 0;
        }
        return new ItemStack(item, stackSize, meta);
    }
    public static String getRandomName(Random random){
        return names.get(random.nextInt(names.size()));
    }

}
