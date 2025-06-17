//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package toufoumaster.btwaila.demo;

import com.mojang.logging.LogUtils;
import com.mojang.nbt.tags.CompoundTag;
import com.mojang.nbt.tags.ListTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.BlockLogicChest.Type;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.motion.CarriedBlock;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventorySorter;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Random;

public class TileEntityDemoChest extends TileEntity implements Container {
    private static final Logger LOGGER = LogUtils.getLogger();
    private ItemStack[] chestContents = new ItemStack[36];

    public TileEntityDemoChest(Random random) {
        for (int i = 0; i < chestContents.length; i++) {
            chestContents[i] = DemoManager.randomStack(random);
        }
    }

    public int getContainerSize() {
        return 27;
    }

    public @Nullable ItemStack getItem(int index) {
        return this.chestContents[index];
    }

    public @Nullable ItemStack removeItem(int index, int takeAmount) {
        if (this.chestContents[index] != null) {
            if (this.chestContents[index].stackSize <= takeAmount) {
                ItemStack itemstack = this.chestContents[index];
                this.chestContents[index] = null;
                this.setChanged();
                return itemstack;
            } else {
                ItemStack itemstack1 = this.chestContents[index].splitStack(takeAmount);
                if (this.chestContents[index].stackSize <= 0) {
                    this.chestContents[index] = null;
                }

                this.setChanged();
                return itemstack1;
            }
        } else {
            return null;
        }
    }

    public void setItem(int index, @Nullable ItemStack itemstack) {
        this.chestContents[index] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getMaxStackSize()) {
            itemstack.stackSize = this.getMaxStackSize();
        }

        this.setChanged();
    }

    public String getNameTranslationKey() {
        return "container.chest.name";
    }

    public void readFromNBT(CompoundTag nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        ListTag nbttaglist = nbttagcompound.getList("Items");
        this.chestContents = new ItemStack[this.getContainerSize()];

        for(int i = 0; i < nbttaglist.tagCount(); ++i) {
            CompoundTag nbttagcompound1 = (CompoundTag)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;
            if (j >= 0 && j < this.chestContents.length) {
                this.chestContents[j] = ItemStack.readItemStackFromNbt(nbttagcompound1);
            }
        }

    }

    public void writeToNBT(CompoundTag nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        ListTag nbttaglist = new ListTag();

        for(int i = 0; i < this.chestContents.length; ++i) {
            if (this.chestContents[i] != null) {
                CompoundTag nbttagcompound1 = new CompoundTag();
                nbttagcompound1.putByte("Slot", (byte)i);
                this.chestContents[i].writeToNBT(nbttagcompound1);
                nbttaglist.addTag(nbttagcompound1);
            }
        }

        nbttagcompound.put("Items", nbttaglist);
    }

    public int getMaxStackSize() {
        return 64;
    }

    public boolean stillValid(Player entityplayer) {
        if (this.worldObj != null && this.worldObj.getTileEntity(this.x, this.y, this.z) == this) {
            return entityplayer.distanceToSqr((double)this.x + (double)0.5F, (double)this.y + (double)0.5F, (double)this.z + (double)0.5F) <= (double)64.0F;
        } else {
            return false;
        }
    }

    public void sortContainer() {
        InventorySorter.sortInventory(this.chestContents);
    }

    public void dropContents(World world, int x, int y, int z) {
        super.dropContents(world, x, y, z);

        for(int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemStack = this.getItem(i);
            if (itemStack != null) {
                EntityItem item = world.dropItem(x, y, z, itemStack);
                item.xd *= (double)0.5F;
                item.yd *= (double)0.5F;
                item.zd *= (double)0.5F;
                item.pickupDelay = 0;
            }
        }

    }

    public boolean canBeCarried(World world, Entity potentialHolder) {
        return true;
    }

    public CarriedBlock getCarriedEntry(World world, Entity holder, Block<?> currentBlock, int currentMeta) {
        return super.getCarriedEntry(world, holder, currentBlock, BlockLogicChest.getMetaWithDirection(BlockLogicChest.getMetaWithType(currentMeta, Type.SINGLE), Direction.NORTH));
    }
}
