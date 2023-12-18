package toufoumaster.btwaila.tooltips.block;

import net.minecraft.client.render.Lighting;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntityFlowerJar;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import toufoumaster.btwaila.demo.DemoEntry;
import toufoumaster.btwaila.demo.DemoManager;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;

import java.util.Random;

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

        Lighting.enableInventoryLight();
        if (flower != null){
            int x = advancedInfoComponent.getPosX() + advancedInfoComponent.getGame().fontRenderer.getStringWidth(text) + 2;
            y -= 4;
            itemRender.renderItemIntoGUI(advancedInfoComponent.getGame().fontRenderer, advancedInfoComponent.getGame().renderEngine, flower, x, y, 1.0F);
            itemRender.renderItemOverlayIntoGUI(advancedInfoComponent.getGame().fontRenderer, advancedInfoComponent.getGame().renderEngine, flower, x, y, 1.0F);
        }
        Lighting.disable();
    }
    @Override
    public DemoEntry tooltipDemo(Random random){
        TileEntityFlowerJar flowerJar = new TileEntityFlowerJar();
        flowerJar.flowerInPot = DemoManager.randomStack(random).getItem().id;
        return new DemoEntry(Block.jarGlass, 0, flowerJar, new ItemStack[]{Item.jar.getDefaultStack()});
    }
}
