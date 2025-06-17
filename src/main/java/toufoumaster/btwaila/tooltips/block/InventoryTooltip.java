package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.entity.TileEntityDispenser;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.container.Container;
import toufoumaster.btwaila.demo.DemoEntry;
import toufoumaster.btwaila.demo.TileEntityDemoChest;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;

import java.util.Random;

import static toufoumaster.btwaila.BTWaila.translator;

public class InventoryTooltip extends TileTooltip<Container> {
    @Override
    public void initTooltip() {
        addClass(TileEntityChest.class);
        addClass(TileEntityDispenser.class);
        addClass(TileEntityDemoChest.class);
    }
    @Override
    public void drawAdvancedTooltip(Container inventory, AdvancedInfoComponent advancedInfoComponent) {
        if (inventory instanceof TileEntityChest){
            TileEntityChest chest = (TileEntityChest)inventory;
            if (chest.worldObj != null) {
                inventory = BlockLogicChest.getInventory(chest.worldObj, chest.x, chest.y, chest.z);
            }
        }
        int max = inventory.getContainerSize();
        int current = 0;
        for (int i = 0; i < max; i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack != null) {
                current += itemStack.stackSize;
            }
        }
        advancedInfoComponent.drawStringWithShadow(
                translator.translateKey("btwaila.tooltip.inventory.storage")
                        .replace("{current}", String.valueOf(current))
                        .replace("{max}", String.valueOf(max * inventory.getMaxStackSize())), 0);

        advancedInfoComponent.drawInventory(inventory, 0);
    }
    @Override
    public DemoEntry tooltipDemo(Random random){
        Block<?> chest = Blocks.CHEST_PLANKS_OAK_PAINTED;
        return new DemoEntry(chest, 8 * 16, new TileEntityDemoChest(random), new ItemStack[]{new ItemStack(chest, 1, 8 * 16)});
    }
}
