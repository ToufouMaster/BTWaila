package toufoumaster.btwaila.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import net.minecraft.client.render.Lighting;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;
import toufoumaster.btwaila.demo.TileEntityDemoChest;

import net.minecraft.core.entity.Entity;

import static toufoumaster.btwaila.gui.GuiBlockOverlay.entityIconMap;
import static toufoumaster.btwaila.gui.GuiBlockOverlay.itemRender;

public class GuiBlockLookedComponent extends MovableHudComponent {
    public Minecraft minecraft;

    public GuiBlockLookedComponent(String key, int xSize, int ySize, Layout layout) {
        super(key, xSize, ySize, layout);
    }

    @Override
    public boolean isVisible(Minecraft minecraft) {
        return minecraft.gameSettings.immersiveMode.drawOutline();
    }

    @Override
    public void render(Minecraft minecraft, GuiIngame guiIngame, int xScreenSize, int yScreenSize, float partialTick) {
        this.minecraft = minecraft;
//        this.activeGUI = guiIngame;
//        this.xScreenSize = xScreenSize;
//        this.yScreenSize = yScreenSize;
        HitResult hitResult = minecraft.objectMouseOver;
        if (hitResult == null) {return;}
        if (hitResult.hitType == HitResult.HitType.TILE) {
            Block block = Block.getBlock(minecraft.theWorld.getBlockId(hitResult.x, hitResult.y, hitResult.z));
            ItemStack[] drops = block.getBreakResult(minecraft.theWorld, EnumDropCause.PICK_BLOCK, hitResult.x, hitResult.y, hitResult.z, minecraft.theWorld.getBlockMetadata(hitResult.x, hitResult.y, hitResult.z), null);
            renderBlockDisplayer(minecraft,drops[0], xScreenSize, yScreenSize);
        } else if (hitResult.hitType == HitResult.HitType.ENTITY) {
            renderEntityDisplayer(minecraft, hitResult.entity, xScreenSize, yScreenSize);
        }
    }
    @Override
    public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
        this.minecraft = minecraft;
//        this.activeGUI = gui;
//        this.xScreenSize = xScreenSize;
//        this.yScreenSize = yScreenSize;
        TileEntityDemoChest demoChest = TileEntityDemoChest.getDemoChest((int) (System.currentTimeMillis() / (1000 * 60 * 60 * 24)));
        int meta = 8 * 16;
        renderBlockDisplayer(minecraft,new ItemStack(Block.chestPlanksOakPainted, 1, meta), xScreenSize, yScreenSize);
    }
    protected void renderBlockDisplayer(Minecraft minecraft, ItemStack blockResult, int xScreenSize, int yScreenSize){
        int x = getLayout().getComponentX(minecraft, this, xScreenSize);
        int y = getLayout().getComponentY(minecraft, this, yScreenSize);
        Lighting.enableInventoryLight();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        itemRender.renderItemIntoGUI(minecraft.fontRenderer, minecraft.renderEngine, blockResult, x + 8, y + 8, 1f, 1.0F);
        itemRender.renderItemOverlayIntoGUI(minecraft.fontRenderer, minecraft.renderEngine, blockResult, x + 8, y + 8, 1f);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        Lighting.disable();
    }
    protected void renderEntityDisplayer(Minecraft minecraft, Entity entity, int xScreenSize, int yScreenSize){
        int x = getLayout().getComponentX(minecraft, this, xScreenSize);
        int y = getLayout().getComponentY(minecraft, this, yScreenSize);
        ItemStack itemToRender = entityIconMap.containsKey(entity.getClass()) ? entityIconMap.get(entity.getClass()) : Item.eggChicken.getDefaultStack();
        itemRender.renderItemIntoGUI(minecraft.fontRenderer, minecraft.renderEngine, itemToRender, x+8, y + 8, 1.0F);
    }
}
