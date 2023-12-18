package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntityRecordPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;

import static toufoumaster.btwaila.BTWaila.translator;
import static toufoumaster.btwaila.gui.components.AdvancedInfoComponent.itemRender;

public class RecordPlayerTooltip extends TileTooltip<TileEntityRecordPlayer> {
    @Override
    public void initTooltip() {
        addClass(TileEntityRecordPlayer.class);
    }
    @Override
    public void drawAdvancedTooltip(TileEntityRecordPlayer recordPlayer, AdvancedInfoComponent advancedInfoComponent) {
        String text = translator.translateKey("btwaila.tooltip.jukebox.disc").replace("{id}", String.valueOf(recordPlayer.record));
        int y = advancedInfoComponent.getOffY() + 1;
        advancedInfoComponent.setOffY(y);
        advancedInfoComponent.drawStringWithShadow(text, 0);
        if (Item.itemsList[recordPlayer.record] != null){
            ItemStack stack = new ItemStack(Item.itemsList[recordPlayer.record]);
            int x = advancedInfoComponent.getPosX() + advancedInfoComponent.getGame().fontRenderer.getStringWidth(text) + 2;
            y -= 4;
            itemRender.renderItemIntoGUI(advancedInfoComponent.getGame().fontRenderer, advancedInfoComponent.getGame().renderEngine, stack, x, y, 1.0F);
            itemRender.renderItemOverlayIntoGUI(advancedInfoComponent.getGame().fontRenderer, advancedInfoComponent.getGame().renderEngine, stack, x, y, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
        }

    }
}
