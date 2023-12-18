package toufoumaster.btwaila.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import net.minecraft.client.render.Lighting;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import toufoumaster.btwaila.gui.demo.DemoEntry;

import static toufoumaster.btwaila.gui.components.AdvancedInfoComponent.entityIconMap;
import static toufoumaster.btwaila.gui.components.AdvancedInfoComponent.itemRender;

public class DropIconComponent extends MovableHudComponent {
    public DropIconComponent(String key, Layout layout) {
        super(key, 18, 18, layout);
    }

    @Override
    public boolean isVisible(Minecraft minecraft) {
        return minecraft.gameSettings.immersiveMode.drawOutline();
    }

    @Override
    public void render(Minecraft minecraft, GuiIngame guiIngame, int xScreenSize, int yScreenSize, float partialTick) {
        HitResult hitResult = minecraft.objectMouseOver;
        if (hitResult == null) {return;}
        if (hitResult.hitType == HitResult.HitType.TILE) {
            Block block = Block.getBlock(minecraft.theWorld.getBlockId(hitResult.x, hitResult.y, hitResult.z));
            ItemStack[] drops = block.getBreakResult(minecraft.theWorld, EnumDropCause.PICK_BLOCK, hitResult.x, hitResult.y, hitResult.z, minecraft.theWorld.getBlockMetadata(hitResult.x, hitResult.y, hitResult.z), null);
            renderItemDisplayer(minecraft,drops[0], xScreenSize, yScreenSize);
        } else if (hitResult.hitType == HitResult.HitType.ENTITY) {
            ItemStack itemToRender = entityIconMap.containsKey(hitResult.entity.getClass()) ? entityIconMap.get(hitResult.entity.getClass()) : Item.eggChicken.getDefaultStack();
            renderItemDisplayer(minecraft, itemToRender, xScreenSize, yScreenSize);
        }
    }
    @Override
    public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
        int meta = 8 * 16;
        ItemStack icon = null;
        if (DemoEntry.getCurrentEntry().block != null){
            icon = DemoEntry.getCurrentEntry().drops[0];
        } else if (DemoEntry.getCurrentEntry().entity != null) {
            Entity entity = DemoEntry.getCurrentEntry().entity;
            icon = entityIconMap.get(entity.getClass());
        }
        if (icon != null){
            renderItemDisplayer(minecraft,icon, xScreenSize, yScreenSize);
        }
    }
    protected void renderItemDisplayer(Minecraft minecraft, ItemStack blockResult, int xScreenSize, int yScreenSize){
        int x = getLayout().getComponentX(minecraft, this, xScreenSize);
        int y = getLayout().getComponentY(minecraft, this, yScreenSize);
        Lighting.enableInventoryLight();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        itemRender.renderItemIntoGUI(minecraft.fontRenderer, minecraft.renderEngine, blockResult, x + (getXSize(minecraft) - 16)/2, y + (getYSize(minecraft) - 16)/2, 1f, 1.0F);
        itemRender.renderItemOverlayIntoGUI(minecraft.fontRenderer, minecraft.renderEngine, blockResult, x + (getXSize(minecraft) - 16)/2, y + (getYSize(minecraft) - 16)/2, 1f);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        Lighting.disable();
    }
}
