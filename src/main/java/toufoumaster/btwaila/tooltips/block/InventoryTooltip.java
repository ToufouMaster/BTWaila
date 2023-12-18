package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.BlockChest;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.entity.TileEntityDispenser;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import toufoumaster.btwaila.demo.TileEntityDemoChest;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;

import static toufoumaster.btwaila.BTWaila.translator;

public class InventoryTooltip extends TileTooltip<IInventory> {
    @Override
    public void initTooltip() {
        addClass(TileEntityChest.class);
        addClass(TileEntityDispenser.class);
        addClass(TileEntityDemoChest.class);
    }
    @Override
    public void drawAdvancedTooltip(IInventory inventory, AdvancedInfoComponent advancedInfoComponent) {
        if (inventory instanceof TileEntityChest){
            TileEntityChest chest = (TileEntityChest)inventory;
            inventory = BlockChest.getInventory(chest.worldObj, chest.x, chest.y, chest.z);
        }
        int max = inventory.getSizeInventory();
        int current = 0;
        for (int i = 0; i < max; i++) {
            ItemStack itemStack = inventory.getStackInSlot(i);
            if (itemStack != null) {
                current += itemStack.stackSize;
            }
        }
        advancedInfoComponent.drawStringWithShadow(
                translator.translateKey("btwaila.tooltip.inventory.storage")
                        .replace("{current}", String.valueOf(current))
                        .replace("{max}", String.valueOf(max * inventory.getInventoryStackLimit())), 0);

        advancedInfoComponent.drawInventory(inventory, 0);
    }
}
