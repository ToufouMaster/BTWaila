package toufoumaster.btwaila.tooltips.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.client.entity.player.EntityOtherPlayerMP;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.monster.*;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import toufoumaster.btwaila.*;
import toufoumaster.btwaila.gui.GuiBlockOverlay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerTooltip implements IBTWailaCustomEntityTooltip {

    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", EntityOtherPlayerMP.class, this);
        tooltipGroup.addTooltip(EntityOtherPlayerMP.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(Entity entity, GuiBlockOverlay guiBlockOverlay) {
        EntityOtherPlayerMP entityPlayer = (EntityOtherPlayerMP) entity;
        ItemStack[] stack = new ItemStack[] {entityPlayer.getCurrentEquippedItem()};
        guiBlockOverlay.drawItemList(stack, -24);
        guiBlockOverlay.addOffY(-8);
        guiBlockOverlay.drawItemList(entityPlayer.inventory.armorInventory, 0);
    }
}
