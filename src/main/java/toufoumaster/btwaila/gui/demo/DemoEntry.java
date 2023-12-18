package toufoumaster.btwaila.gui.demo;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityFlag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.EntityPig;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import toufoumaster.btwaila.demo.TileEntityDemoChest;

import java.util.ArrayList;

public class DemoEntry {
    public static int demoOffset = 0;
    public static ArrayList<DemoEntry> demoBlocks = new ArrayList<>();
    static {
        TileEntityDemoChest demoChest = TileEntityDemoChest.getDemoChest((int) (System.currentTimeMillis() / (1000 * 60 * 60 * 24)));
        Block chest = Block.chestPlanksOakPainted;
        demoBlocks.add(new DemoEntry(chest, 8 * 16, demoChest, new ItemStack[]{new ItemStack(chest, 1, 8 * 16)}));

        demoBlocks.add(new DemoEntry(Block.flag, 0, new TileEntityFlag(), new ItemStack[]{Item.flag.getDefaultStack()}));

        EntityPig pig = new EntityPig(null);
        pig.health = 10;
        demoBlocks.add(new DemoEntry(pig));
    }
    public static DemoEntry getCurrentEntry(){
        return demoBlocks.get(demoOffset % demoBlocks.size());
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
