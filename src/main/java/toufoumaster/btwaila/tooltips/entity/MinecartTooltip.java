package toufoumaster.btwaila.tooltips.entity;

import net.minecraft.client.entity.player.EntityOtherPlayerMP;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.vehicle.EntityMinecart;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import toufoumaster.btwaila.BTWaila;
import toufoumaster.btwaila.IBTWailaCustomEntityTooltip;
import toufoumaster.btwaila.TooltipGroup;
import toufoumaster.btwaila.TooltipRegistry;
import toufoumaster.btwaila.gui.GuiBlockOverlay;

public class MinecartTooltip implements IBTWailaCustomEntityTooltip {

    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", EntityMinecart.class, this);
        tooltipGroup.addTooltip(EntityMinecart.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(Entity entity, GuiBlockOverlay guiBlockOverlay) {
        EntityMinecart entityMinecart = (EntityMinecart) entity;
        switch (entityMinecart.minecartType) {
            case 0: // Minecart
                final Entity passenger = entityMinecart.passenger;
                if (passenger == null) break;
                boolean isLivingEntity = (passenger instanceof EntityLiving);
                EntityLiving entityLiving = isLivingEntity ? (EntityLiving) passenger : null;
                String entityName = isLivingEntity ? entityLiving.getDisplayName() : null;
                if (entityName == null || entityName.equalsIgnoreCase("§0")) entityName = EntityDispatcher.getEntityString(passenger);

                guiBlockOverlay.drawStringWithShadow("Passenger: " + entityName, 0);
                break;
            case 1: // Chest
                IInventory inventory = entityMinecart;
                int max = inventory.getSizeInventory();
                int current = 0;
                for (int i = 0; i < max; i++) {
                    ItemStack itemStack = inventory.getStackInSlot(i);
                    if (itemStack != null) {
                        current += itemStack.stackSize;
                    }
                }
                guiBlockOverlay.drawStringWithShadow("Stored items: " + current + "/" + max * inventory.getInventoryStackLimit(), 0);
                guiBlockOverlay.drawInventory(inventory, 0);
                break;
            case 2: // Furnace
                guiBlockOverlay.drawStringWithShadow("Fuel: " + entityMinecart.fuel, 0);
                break;
        }
    }
}
