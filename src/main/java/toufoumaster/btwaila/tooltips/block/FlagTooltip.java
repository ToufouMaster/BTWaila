package toufoumaster.btwaila.tooltips.block;

import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityFlag;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.helper.UUIDHelper;
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
        //TODO: get name from uuid
        //advancedInfoComponent.drawStringWithShadow(translator.translateKey("btwaila.tooltip.flag.owner").replace("{name}", flag.owner ? translator.translateKey("btwaila.tooltip.flag.owner.none") : flag.owner), 0);
        advancedInfoComponent.addOffY(2);
        renderStringAndStack(advancedInfoComponent,translator.translateKey("btwaila.tooltip.flag.color").replace("{id}", "1") + "    " +  ((color1 != null) ? translator.translateNameKey(color1.getItemKey()) : translator.translateKey("btwaila.tooltip.flag.empty")), 0, color1);
        renderStringAndStack(advancedInfoComponent,translator.translateKey("btwaila.tooltip.flag.color").replace("{id}", "2") + "    " +  ((color2 != null) ? translator.translateNameKey(color2.getItemKey()) : translator.translateKey("btwaila.tooltip.flag.empty")), 0, color2);
        renderStringAndStack(advancedInfoComponent,translator.translateKey("btwaila.tooltip.flag.color").replace("{id}", "3") + "    " +  ((color3 != null) ? translator.translateNameKey(color3.getItemKey()) : translator.translateKey("btwaila.tooltip.flag.empty")), 0, color3);
    }
    @SuppressWarnings("SameParameterValue")
    protected void renderStringAndStack(AdvancedInfoComponent advancedInfoComponent, String s, int offX, ItemStack stack){
        if (stack != null){
            int y = advancedInfoComponent.getOffY() - 1;
            int x = advancedInfoComponent.getPosX() - 16 + advancedInfoComponent.minecraft.font.getStringWidth(translator.translateKey("btwaila.tooltip.flag.color").replace("{id}", "1") + "    ");
            y -= 3;
            Tessellator t = Tessellator.instance;
            ItemModel model = ItemModelDispatcher.getInstance().getDispatch(stack);
            model.renderItemIntoGui(t, advancedInfoComponent.getGame().font, advancedInfoComponent.getGame().textureManager, stack, x, y, 1.0F);
            model.renderItemOverlayIntoGUI(t, advancedInfoComponent.getGame().font, advancedInfoComponent.getGame().textureManager, stack, x, y, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
        }
        advancedInfoComponent.drawStringWithShadow(s, offX);
        advancedInfoComponent.addOffY(4);
    }
    @Override
    public DemoEntry tooltipDemo(Random random){
        TileEntityFlag demoFlag = new TileEntityFlag();
        demoFlag.items = new ItemStack[]{new ItemStack(Items.DYE, 1, random.nextInt(16)), new ItemStack(Items.DYE, 1, random.nextInt(16)), new ItemStack(Items.DYE, 1, random.nextInt(16))};
        //demoFlag.owner = DemoManager.getRandomName(random);
        return new DemoEntry(Blocks.FLAG, 0, demoFlag, new ItemStack[]{Items.FLAG.getDefaultStack()});
    }
}
