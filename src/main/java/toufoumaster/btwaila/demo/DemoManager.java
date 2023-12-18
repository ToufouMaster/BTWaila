package toufoumaster.btwaila.demo;

import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DemoManager {
    private static final DemoManager INSTANCE = new DemoManager();
    public static List<DemoEntry> demoEntries = new ArrayList<>();
    public static DemoManager getInstance(){
        return INSTANCE;
    }
    public DemoEntry register(DemoEntry entry){
        if (entry == null) return null;
        demoEntries.add(entry);
        return entry;
    }
    public static final Random random = new Random(System.currentTimeMillis() / (1000 * 60 * 60 * 24));
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

    public static final Map<Integer, Integer> fuelList = LookupFuelFurnace.instance.getFuelList();
    public static DemoEntry getCurrentEntry(){
        if (demoOffset < 0){
            demoOffset += DemoManager.demoEntries.size();
        }
        return DemoManager.demoEntries.get(demoOffset % DemoManager.demoEntries.size());
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
    public static int getRandomFuelTime(Random random){
        return random.nextInt((int) fuelList.values().toArray()[random.nextInt(fuelList.size())]);
    }
}
