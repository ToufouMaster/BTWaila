package toufoumaster.btwaila.tooltips.entity;

import net.minecraft.client.entity.player.EntityOtherPlayerMP;
import net.minecraft.core.item.ItemStack;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.EntityTooltip;

public class PlayerTooltip extends EntityTooltip<EntityOtherPlayerMP> {
    @Override
    public void drawAdvancedTooltip(EntityOtherPlayerMP entityPlayer, AdvancedInfoComponent advancedInfoComponent) {
        ItemStack[] stack = new ItemStack[] {entityPlayer.getCurrentEquippedItem()};
        advancedInfoComponent.drawItemList(stack, -24);
        advancedInfoComponent.addOffY(-8);
        advancedInfoComponent.drawItemList(entityPlayer.inventory.armorInventory, 0);
    }

    @Override
    public void initTooltip() {
        addClass(EntityOtherPlayerMP.class);
    }
}
