package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntityFlag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import toufoumaster.btwaila.demo.DemoEntry;
import toufoumaster.btwaila.demo.DemoManager;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;

import java.util.Random;

import static toufoumaster.btwaila.BTWaila.translator;

public class FlagTooltip extends TileTooltip<TileEntityFlag> {
    @Override
    public void initTooltip() {
        addClass(TileEntityFlag.class);
    }
    @Override
    public void drawAdvancedTooltip(TileEntityFlag flag, AdvancedInfoComponent advancedInfoComponent) {
        ItemStack color1 = flag.items[0];
        ItemStack color2 = flag.items[1];
        ItemStack color3 = flag.items[2];
        advancedInfoComponent.drawStringWithShadow(translator.translateKey("btwaila.tooltip.flag.owner").replace("{name}", flag.owner.isEmpty() ? translator.translateKey("btwaila.tooltip.flag.owner.none") : flag.owner), 0);
        advancedInfoComponent.addOffY(2);
        renderStringAndStack(advancedInfoComponent,translator.translateKey("btwaila.tooltip.flag.color").replace("{id}", "1") + "    " +  ((color1 != null) ? translator.translateNameKey(color1.getItemName()) : translator.translateKey("btwaila.tooltip.flag.empty")), 0, color1);
        renderStringAndStack(advancedInfoComponent,translator.translateKey("btwaila.tooltip.flag.color").replace("{id}", "2") + "    " +  ((color2 != null) ? translator.translateNameKey(color2.getItemName()) : translator.translateKey("btwaila.tooltip.flag.empty")), 0, color2);
        renderStringAndStack(advancedInfoComponent,translator.translateKey("btwaila.tooltip.flag.color").replace("{id}", "3") + "    " +  ((color3 != null) ? translator.translateNameKey(color3.getItemName()) : translator.translateKey("btwaila.tooltip.flag.empty")), 0, color3);
    }
    @SuppressWarnings("SameParameterValue")
    protected void renderStringAndStack(AdvancedInfoComponent advancedInfoComponent, String s, int offX, ItemStack stack){
        if (stack != null){
            int y = advancedInfoComponent.getOffY() - 1;
            int x = advancedInfoComponent.getPosX() - 16 + advancedInfoComponent.minecraft.fontRenderer.getStringWidth(translator.translateKey("btwaila.tooltip.flag.color").replace("{id}", "1") + "    ");
            y -= 3;
            AdvancedInfoComponent.itemRender.renderItemIntoGUI(advancedInfoComponent.getGame().fontRenderer, advancedInfoComponent.getGame().renderEngine, stack, x, y, 1.0F);
            AdvancedInfoComponent.itemRender.renderItemOverlayIntoGUI(advancedInfoComponent.getGame().fontRenderer, advancedInfoComponent.getGame().renderEngine, stack, x, y, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
        }
        advancedInfoComponent.drawStringWithShadow(s, offX);
        advancedInfoComponent.addOffY(4);
    }
    @Override
    public DemoEntry tooltipDemo(Random random){
        TileEntityFlag demoFlag = new TileEntityFlag();
        demoFlag.items = new ItemStack[]{new ItemStack(Item.dye, 1, random.nextInt(16)), new ItemStack(Item.dye, 1, random.nextInt(16)), new ItemStack(Item.dye, 1, random.nextInt(16))};
        demoFlag.owner = DemoManager.getRandomName(random);
        return new DemoEntry(Block.flag, 0, demoFlag, new ItemStack[]{Item.flag.getDefaultStack()});
    }
}
