package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityRecordPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import toufoumaster.btwaila.*;
import toufoumaster.btwaila.gui.GuiBlockOverlay;

public class RecordPlayerTooltip implements IBTWailaCustomBlockTooltip {

    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", TileEntityRecordPlayer.class, this);
        tooltipGroup.addTooltip(TileEntityRecordPlayer.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(TileEntity tileEntity, GuiBlockOverlay guiBlockOverlay) {
        TileEntityRecordPlayer recordPlayer = (TileEntityRecordPlayer) tileEntity;
        String text = "Disk id: "+recordPlayer.record;
        int y = guiBlockOverlay.getOffY() + 1;
        guiBlockOverlay.setOffY(y);
        guiBlockOverlay.drawStringWithShadow(text, 0);
        if (Item.itemsList[recordPlayer.record] != null){
            ItemStack stack = new ItemStack(Item.itemsList[recordPlayer.record]);
            int x = guiBlockOverlay.posX + guiBlockOverlay.getGame().fontRenderer.getStringWidth(text) + 33;
            y -= 3;
            guiBlockOverlay.itemRender.renderItemIntoGUI(guiBlockOverlay.getGame().fontRenderer, guiBlockOverlay.getGame().renderEngine, stack, x, y, 1.0F);
            guiBlockOverlay.itemRender.renderItemOverlayIntoGUI(guiBlockOverlay.getGame().fontRenderer, guiBlockOverlay.getGame().renderEngine, stack, x, y, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
        }

    }
}
