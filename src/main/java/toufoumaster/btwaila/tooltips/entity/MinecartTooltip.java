package toufoumaster.btwaila.tooltips.entity;

import net.minecraft.core.entity.vehicle.EntityMinecart;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import toufoumaster.btwaila.demo.DemoEntry;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.EntityTooltip;

import java.util.Random;

import static toufoumaster.btwaila.BTWaila.translator;

public class MinecartTooltip extends EntityTooltip<EntityMinecart> {
    @Override
    public void initTooltip() {
        addClass(EntityMinecart.class);
    }

    @Override
    public void drawAdvancedTooltip(EntityMinecart entityMinecart, AdvancedInfoComponent advancedInfoComponent) {
        switch (entityMinecart.minecartType) {
            case 0: // Minecart
                String entityName = AdvancedInfoComponent.getEntityName(entityMinecart.passenger);
                advancedInfoComponent.drawStringWithShadow(
                        translator.translateKey("btwaila.tooltip.minecart.passenger")
                                .replace("{name}", entityName), 0);
                break;
            case 1: // Chest
                int max = ((IInventory) entityMinecart).getSizeInventory();
                int current = 0;
                for (int i = 0; i < max; i++) {
                    ItemStack itemStack = ((IInventory) entityMinecart).getStackInSlot(i);
                    if (itemStack != null) {
                        current += itemStack.stackSize;
                    }
                }
                advancedInfoComponent.drawStringWithShadow(
                        translator.translateKey("btwaila.tooltip.minecart.storage")
                                .replace("{current}", String.valueOf(current))
                                .replace("{max}", String.valueOf(max * ((IInventory) entityMinecart).getInventoryStackLimit())), 0);

                advancedInfoComponent.drawInventory(entityMinecart, 0);
                break;
            case 2: // Furnace
                advancedInfoComponent.drawStringWithShadow(translator.translateKey("btwaila.tooltip.minecart.fuel")
                        .replace("{fuel}", String.valueOf(entityMinecart.fuel)), 0);
                break;
        }
    }
    @Override
    public DemoEntry tooltipDemo(Random random){
        EntityMinecart demoMinecart = new EntityMinecart(null);
        demoMinecart.minecartType = random.nextInt(3);
        return new DemoEntry(demoMinecart);
    }
}
