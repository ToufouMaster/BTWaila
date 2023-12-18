package toufoumaster.btwaila.tooltips.entity;

import net.minecraft.client.entity.player.EntityOtherPlayerMP;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.item.ItemStack;
import toufoumaster.btwaila.*;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TooltipGroup;
import toufoumaster.btwaila.tooltips.TooltipRegistry;
import toufoumaster.btwaila.tooltips.interfaces.IBTWailaCustomEntityTooltip;

public class PlayerTooltip implements IBTWailaCustomEntityTooltip {

    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", EntityOtherPlayerMP.class, this);
        tooltipGroup.addTooltip(EntityOtherPlayerMP.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(Entity entity, AdvancedInfoComponent advancedInfoComponent) {
        EntityOtherPlayerMP entityPlayer = (EntityOtherPlayerMP) entity;
        ItemStack[] stack = new ItemStack[] {entityPlayer.getCurrentEquippedItem()};
        advancedInfoComponent.drawItemList(stack, -24);
        advancedInfoComponent.addOffY(-8);
        advancedInfoComponent.drawItemList(entityPlayer.inventory.armorInventory, 0);
    }
}
