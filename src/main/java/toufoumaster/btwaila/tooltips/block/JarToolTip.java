package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntityFlowerJar;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;

import static toufoumaster.btwaila.BTWaila.translator;
import static toufoumaster.btwaila.gui.components.AdvancedInfoComponent.itemRender;

public class JarToolTip extends TileTooltip<TileEntityFlowerJar> {
    @Override
    public void initTooltip() {
        addClass(TileEntityFlowerJar.class);
    }

    @Override
    public void drawAdvancedTooltip(TileEntityFlowerJar flowerJar, AdvancedInfoComponent advancedInfoComponent) {
        Item itemFlower = Item.itemsList[flowerJar.flowerInPot];
        ItemStack flower = null;
        String flowerName = translator.translateKey("btwaila.tooltip.jar.item.none");
        if (itemFlower != null){
            flower = itemFlower.getDefaultStack();
            flowerName = flower.getDisplayName();
        }
        String text = translator.translateKey("btwaila.tooltip.jar.planted").replace("{name}", flowerName);

        int y = advancedInfoComponent.getOffY() + 1;
        advancedInfoComponent.drawStringWithShadow(text, 0);

        if (flower != null){
            int x = advancedInfoComponent.getPosX() + advancedInfoComponent.getGame().fontRenderer.getStringWidth(text) + 2;
            y -= 4;
            itemRender.renderItemIntoGUI(advancedInfoComponent.getGame().fontRenderer, advancedInfoComponent.getGame().renderEngine, flower, x, y, 1.0F);
            itemRender.renderItemOverlayIntoGUI(advancedInfoComponent.getGame().fontRenderer, advancedInfoComponent.getGame().renderEngine, flower, x, y, 1.0F);
        }
    }
}
