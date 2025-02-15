package toufoumaster.btwaila.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.util.phys.HitResult;
import org.lwjgl.opengl.GL11;
import toufoumaster.btwaila.demo.DemoManager;

public class HarvestToolComponent extends HudComponentMovable {
    public HarvestToolComponent(String key, Layout layout) {
        super(key, 18, 18, layout);
    }

    @Override
    public boolean isVisible(Minecraft minecraft) {
        return minecraft.gameSettings.immersiveMode.drawHotbar() && minecraft.thePlayer.gamemode == Gamemode.survival;
    }

    @Override
    public void render(Minecraft minecraft, HudIngame HudIngame, int xScreenSize, int yScreenSize, float f) {
        if (minecraft.objectMouseOver == null) return;
        if (minecraft.objectMouseOver.hitType != HitResult.HitType.TILE) return;
        Block block = minecraft.currentWorld.getBlock(minecraft.objectMouseOver.x, minecraft.objectMouseOver.y, minecraft.objectMouseOver.z);
        renderTool(minecraft, block, xScreenSize, yScreenSize);
    }

    @Override
    public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
        Block block = DemoManager.getCurrentEntry().block;
        if (block != null){
            renderTool(minecraft, block, xScreenSize, yScreenSize);
        }
    }
    protected void renderTool(Minecraft minecraft, Block block, int xScreenSize, int yScreenSize){
        int x = getLayout().getComponentX(minecraft, this, xScreenSize);
        int y = getLayout().getComponentY(minecraft, this, yScreenSize);
        Item itemHarvestTool = null;
        if (Items.TOOL_PICKAXE_STEEL.canHarvestBlock(minecraft.thePlayer, new ItemStack(block), block)) {
            itemHarvestTool = Items.TOOL_PICKAXE_STEEL;
        } else if (Items.TOOL_SHEARS_STEEL.canHarvestBlock(minecraft.thePlayer,new ItemStack(block), block)) {
            itemHarvestTool = Items.TOOL_SHEARS_STEEL;
        } else if (Items.TOOL_AXE_STEEL.canHarvestBlock(minecraft.thePlayer,new ItemStack(block), block)) {
            itemHarvestTool = Items.TOOL_AXE_STEEL;
        } else if (Items.TOOL_SWORD_STEEL.canHarvestBlock(minecraft.thePlayer,new ItemStack(block),block)) {
            itemHarvestTool = Items.TOOL_SWORD_STEEL;
        } else if (Items.TOOL_SHOVEL_STEEL.canHarvestBlock(minecraft.thePlayer,new ItemStack(block),block)) {
            itemHarvestTool = Items.TOOL_SHOVEL_STEEL;
        } else if (Items.TOOL_HOE_STEEL.canHarvestBlock(minecraft.thePlayer,new ItemStack(block),block)) {
            itemHarvestTool = Items.TOOL_HOE_STEEL;
        }
        if (itemHarvestTool == null) return;

        GL11.glEnable(GL11.GL_DEPTH_TEST);

        Tessellator t = Tessellator.instance;
        ItemModel model = ItemModelDispatcher.getInstance().getDispatch(itemHarvestTool);
        model.renderItemIntoGui(t, minecraft.font, minecraft.textureManager, itemHarvestTool.getDefaultStack(), x + (getXSize(minecraft) - 16)/2, y + (getYSize(minecraft) - 16)/2, 1.0F);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
    }
}
