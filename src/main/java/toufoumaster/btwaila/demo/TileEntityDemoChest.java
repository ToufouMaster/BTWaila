package toufoumaster.btwaila.demo;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.InventorySorter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileEntityDemoChest extends TileEntity implements IInventory {
    private ItemStack[] chestContents = new ItemStack[36 * 2];
    public static final List<Item> allRealItems = new ArrayList<>();
    static {
        for (int i = 0; i < Item.itemsList.length; i++) {
            if (Item.itemsList[i] != null){
                allRealItems.add(Item.itemsList[i]);
            }
        }
    }
    public TileEntityDemoChest(int seed){
        Random rand = new Random(seed);
        for (int i = 0; i < chestContents.length; i++) {
            chestContents[i] = randomStack(rand);
        }
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

    @Override
    public int getSizeInventory() {
        return 27 * 2;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.chestContents[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if (this.chestContents[i] != null) {
            if (this.chestContents[i].stackSize <= j) {
                ItemStack itemstack = this.chestContents[i];
                this.chestContents[i] = null;
                this.onInventoryChanged();
                return itemstack;
            }
            ItemStack itemstack1 = this.chestContents[i].splitStack(j);
            if (this.chestContents[i].stackSize <= 0) {
                this.chestContents[i] = null;
            }
            this.onInventoryChanged();
            return itemstack1;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.chestContents[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }
        this.onInventoryChanged();
    }

    @Override
    public String getInvName() {
        return "Chest";
    }

    @Override
    public void readFromNBT(CompoundTag nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        ListTag nbttaglist = nbttagcompound.getList("Items");
        this.chestContents = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            CompoundTag nbttagcompound1 = (CompoundTag)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 0xFF;
            if (j >= this.chestContents.length) continue;
            this.chestContents[j] = ItemStack.readItemStackFromNbt(nbttagcompound1);
        }
    }

    @Override
    public void writeToNBT(CompoundTag nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        ListTag nbttaglist = new ListTag();
        for (int i = 0; i < this.chestContents.length; ++i) {
            if (this.chestContents[i] == null) continue;
            CompoundTag nbttagcompound1 = new CompoundTag();
            nbttagcompound1.putByte("Slot", (byte)i);
            this.chestContents[i].writeToNBT(nbttagcompound1);
            nbttaglist.addTag(nbttagcompound1);
        }
        nbttagcompound.put("Items", nbttaglist);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        if (this.worldObj.getBlockTileEntity(this.x, this.y, this.z) != this) {
            return false;
        }
        return entityplayer.distanceToSqr((double)this.x + 0.5, (double)this.y + 0.5, (double)this.z + 0.5) <= 64.0;
    }

    @Override
    public void sortInventory() {
        InventorySorter.sortInventory(this.chestContents);
    }
}
