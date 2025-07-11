package toufoumaster.btwaila.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.client.render.Lighting;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.util.phys.HitResult;
import org.lwjgl.opengl.GL11;
import toufoumaster.btwaila.BTWailaClient;
import toufoumaster.btwaila.demo.DemoManager;

import static toufoumaster.btwaila.gui.components.AdvancedInfoComponent.entityIconMap;

public class DropIconComponent extends HudComponentMovable {
    public DropIconComponent(String key, Layout layout) {
        super(key, 18, 18, layout);
    }

    @Override
    public boolean isVisible(Minecraft minecraft) {
        return minecraft.gameSettings.immersiveMode.drawHotbar();
    }

    @Override
    public void render(Minecraft minecraft, HudIngame HudIngame, int xScreenSize, int yScreenSize, float partialTick) {
        if (!BTWailaClient.modSettings.bTWaila$getBlockTooltips().value) return;

        HitResult hitResult = minecraft.objectMouseOver;
        if (hitResult == null) {return;}
        if (hitResult.hitType == HitResult.HitType.TILE) {
            Block<?> block = minecraft.currentWorld.getBlock(hitResult.x, hitResult.y, hitResult.z);
            ItemStack[] drops = null;
            if (block != null) {
                drops = block.getBreakResult(minecraft.currentWorld, EnumDropCause.PICK_BLOCK, hitResult.x, hitResult.y, hitResult.z, minecraft.currentWorld.getBlockMetadata(hitResult.x, hitResult.y, hitResult.z), null);
                ItemStack icon = block.getDefaultStack();
                if (drops != null && drops.length > 0){
                    icon = drops[0];
                }
                icon.stackSize = 1;
                renderItemDisplayer(minecraft,icon, xScreenSize, yScreenSize);
            }
        } else if (hitResult.hitType == HitResult.HitType.ENTITY) {
            ItemStack itemToRender = getEntityIcon(hitResult.entity);
            itemToRender.stackSize = 1;
            renderItemDisplayer(minecraft, itemToRender, xScreenSize, yScreenSize);
        }
    }
    @Override
    public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
        ItemStack icon = null;
        if (DemoManager.getCurrentEntry().block != null){
            icon = DemoManager.getCurrentEntry().drops[0];
        } else if (DemoManager.getCurrentEntry().entity != null) {
            icon = getEntityIcon(DemoManager.getCurrentEntry().entity);
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

        Tessellator t = Tessellator.instance;
        ItemModel model = ItemModelDispatcher.getInstance().getDispatch(blockResult);
        model.renderItemIntoGui(t, minecraft.font, minecraft.textureManager, blockResult, x + (getXSize(minecraft) - 16)/2, y + (getYSize(minecraft) - 16)/2, 1f, 1.0F);
        model.renderItemOverlayIntoGUI(t, minecraft.font, minecraft.textureManager, blockResult, x + (getXSize(minecraft) - 16)/2, y + (getYSize(minecraft) - 16)/2, 1f);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        Lighting.disable();
    }
    public ItemStack getEntityIcon(Entity entity){
        ItemStack icon = entityIconMap.get(entity.getClass());
        if (icon == null && entity instanceof Mob){
            Mob living = (Mob)entity;
            if (!living.mobDrops.isEmpty()){
                WeightedRandomLootObject lootObject = living.mobDrops.get(0);
                icon = lootObject.getItemStack();
            }

        }
        if (icon != null){
            return icon;
        } else {
            return Items.EGG_CHICKEN.getDefaultStack();
        }
    }
}
