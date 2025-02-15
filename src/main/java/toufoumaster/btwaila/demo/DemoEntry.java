package toufoumaster.btwaila.demo;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemStack;

public class DemoEntry {
    public Block<?> block;
    public int meta;
    public Entity entity;
    public TileEntity tileEntity;
    public ItemStack[] drops;
    public DemoEntry(Block<?> block, int meta, TileEntity tileEntity, ItemStack[] drops){
        this.block = block;
        this.meta = meta;
        this.tileEntity = tileEntity;
        this.drops = drops;
    }
    public DemoEntry(Entity entity){
        this.entity = entity;
    }
}
