package toufoumaster.btwaila.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.player.gamemode.Gamemode;
import org.lwjgl.opengl.GL11;
import toufoumaster.btwaila.demo.DemoEntry;
import toufoumaster.btwaila.util.DemoUtil;

import static toufoumaster.btwaila.gui.components.AdvancedInfoComponent.itemRender;

public class HarvestToolComponent extends MovableHudComponent {
    public HarvestToolComponent(String key, Layout layout) {
        super(key, 18, 18, layout);
    }

    @Override
    public boolean isVisible(Minecraft minecraft) {
        return minecraft.gameSettings.immersiveMode.drawHotbar() && minecraft.thePlayer.gamemode == Gamemode.survival;
    }

    @Override
    public void render(Minecraft minecraft, GuiIngame guiIngame, int xScreenSize, int yScreenSize, float f) {
        if (minecraft.objectMouseOver == null) return;
        if (minecraft.objectMouseOver.hitType != HitResult.HitType.TILE) return;
        Block block = minecraft.theWorld.getBlock(minecraft.objectMouseOver.x, minecraft.objectMouseOver.y, minecraft.objectMouseOver.z);
        renderTool(minecraft, block, xScreenSize, yScreenSize);
    }

    @Override
    public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
        Block block = DemoUtil.getCurrentEntry().block;
        if (block != null){
            renderTool(minecraft, block, xScreenSize, yScreenSize);
        }
    }
    protected void renderTool(Minecraft minecraft, Block block, int xScreenSize, int yScreenSize){
        int x = getLayout().getComponentX(minecraft, this, xScreenSize);
        int y = getLayout().getComponentY(minecraft, this, yScreenSize);
        Item itemHarvestTool = null;
        if (Item.toolPickaxeSteel.canHarvestBlock(block)) {
            itemHarvestTool = Item.toolPickaxeSteel;
        } else if (Item.toolShearsSteel.canHarvestBlock(block)) {
            itemHarvestTool = Item.toolShearsSteel;
        } else if (Item.toolAxeSteel.canHarvestBlock(block)) {
            itemHarvestTool = Item.toolAxeSteel;
        } else if (Item.toolSwordSteel.canHarvestBlock(block)) {
            itemHarvestTool = Item.toolSwordSteel;
        } else if (Item.toolShovelSteel.canHarvestBlock(block)) {
            itemHarvestTool = Item.toolShovelSteel;
        } else if (Item.toolHoeSteel.canHarvestBlock(block)) {
            itemHarvestTool = Item.toolHoeSteel;
        }
        if (itemHarvestTool == null) return;

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        itemRender.renderItemIntoGUI(minecraft.fontRenderer, minecraft.renderEngine, itemHarvestTool.getDefaultStack(), x + (getXSize(minecraft) - 16)/2, y + (getYSize(minecraft) - 16)/2, 1.0F);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
    }
}
