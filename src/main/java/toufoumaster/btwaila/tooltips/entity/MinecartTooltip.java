package toufoumaster.btwaila.tooltips.entity;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.vehicle.EntityMinecart;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import toufoumaster.btwaila.BTWaila;
import toufoumaster.btwaila.tooltips.interfaces.IBTWailaCustomEntityTooltip;
import toufoumaster.btwaila.tooltips.TooltipGroup;
import toufoumaster.btwaila.tooltips.TooltipRegistry;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;

import static toufoumaster.btwaila.BTWaila.translator;

public class MinecartTooltip implements IBTWailaCustomEntityTooltip {

    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", EntityMinecart.class, this);
        tooltipGroup.addTooltip(EntityMinecart.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(Entity entity, AdvancedInfoComponent advancedInfoComponent) {
        EntityMinecart entityMinecart = (EntityMinecart) entity;
        switch (entityMinecart.minecartType) {
            case 0: // Minecart
                final Entity passenger = entityMinecart.passenger;
                if (passenger == null) break;
                boolean isLivingEntity = (passenger instanceof EntityLiving);
                EntityLiving entityLiving = isLivingEntity ? (EntityLiving) passenger : null;
                String entityName = isLivingEntity ? entityLiving.getDisplayName() : null;
                if (entityName == null || entityName.equalsIgnoreCase("§0")) entityName = EntityDispatcher.getEntityString(passenger);

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
}
