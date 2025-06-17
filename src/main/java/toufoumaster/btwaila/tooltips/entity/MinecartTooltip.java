package toufoumaster.btwaila.tooltips.entity;

import net.minecraft.core.entity.vehicle.EntityMinecart;
import net.minecraft.core.item.ItemStack;
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
        switch (entityMinecart.getType()) {
            case 0: // Minecart
                String entityName = AdvancedInfoComponent.getEntityName(entityMinecart.passenger);
                advancedInfoComponent.drawStringWithShadow(
                        translator.translateKey("btwaila.tooltip.minecart.passenger")
                                .replace("{name}", entityName), 0);
                break;
            case 1: // Chest
                int max = entityMinecart.getContainerSize();
                int current = 0;
                for (int i = 0; i < max; i++) {
                    ItemStack itemStack = entityMinecart.getItem(i);
                    if (itemStack != null) {
                        current += itemStack.stackSize;
                    }
                }
                advancedInfoComponent.drawStringWithShadow(
                        translator.translateKey("btwaila.tooltip.minecart.storage")
                                .replace("{current}", String.valueOf(current))
                                .replace("{max}", String.valueOf(max * entityMinecart.getMaxStackSize())), 0);

                advancedInfoComponent.drawInventory(entityMinecart, 0);
                break;
            case 2: // Furnace
                advancedInfoComponent.drawStringWithShadow(translator.translateKey("btwaila.tooltip.minecart.fuel")
                        .replace("{fuel}", String.valueOf(entityMinecart.getFuel())), 0);
                break;
        }
    }
    @Override
    public DemoEntry tooltipDemo(Random random){
        EntityMinecart demoMinecart = new EntityMinecart(null);
        demoMinecart.setType((byte) random.nextInt(3));
        return new DemoEntry(demoMinecart);
    }
}
