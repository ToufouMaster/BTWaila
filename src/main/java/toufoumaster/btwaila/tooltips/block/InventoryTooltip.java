package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.BlockChest;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.entity.TileEntityDispenser;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import toufoumaster.btwaila.*;
import toufoumaster.btwaila.demo.TileEntityDemoChest;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;

public class InventoryTooltip implements IBTWailaCustomBlockTooltip {

    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", IInventory.class, this);
        tooltipGroup.addTooltip(TileEntityChest.class);
        tooltipGroup.addTooltip(TileEntityDispenser.class);
        tooltipGroup.addTooltip(TileEntityDemoChest.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(TileEntity tileEntity, AdvancedInfoComponent advancedInfoComponent) {
        IInventory inventory = (IInventory) tileEntity;
        if (tileEntity instanceof TileEntityChest){
            inventory = BlockChest.getInventory(tileEntity.worldObj, tileEntity.x, tileEntity.y, tileEntity.z);
        }
        int max = inventory.getSizeInventory();
        int current = 0;
        for (int i = 0; i < max; i++) {
            ItemStack itemStack = inventory.getStackInSlot(i);
            if (itemStack != null) {
                current += itemStack.stackSize;
            }
        }
        advancedInfoComponent.drawStringWithShadow("Stored items: " + current + "/" + max * inventory.getInventoryStackLimit(), 0);
        advancedInfoComponent.drawInventory(inventory, 0);
    }
}
