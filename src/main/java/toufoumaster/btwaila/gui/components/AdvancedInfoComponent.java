package toufoumaster.btwaila.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityClientPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import org.lwjgl.input.Keyboard;
import toufoumaster.btwaila.BTWaila;
import toufoumaster.btwaila.demo.DemoManager;
import toufoumaster.btwaila.network.packet.PacketRequestEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestTileEntityData;
import toufoumaster.btwaila.tooltips.EntityTooltip;
import toufoumaster.btwaila.tooltips.TileTooltip;
import toufoumaster.btwaila.tooltips.TooltipRegistry;

public class AdvancedInfoComponent extends WailaTextComponent {
    private static boolean keyPressed = false;
    public AdvancedInfoComponent(String key, Layout layout) {
        super(key,68, layout);
    }
    @Override
    public int getAnchorY(ComponentAnchor anchor) {
        return (int)(anchor.yPosition * getYSize(Minecraft.getMinecraft(this)));
    }
    @Override
    public boolean isVisible(Minecraft minecraft) {
        return minecraft.gameSettings.immersiveMode.drawHotbar();
    }

    @Override
    public void renderPost(Minecraft minecraft, GuiIngame guiIngame, int xScreenSize, int yScreenSize, float partialTick) {
        HitResult hitResult = minecraft.objectMouseOver;
        if (hitResult == null) {return;}
        if (hitResult.hitType == HitResult.HitType.TILE) {
            TileEntity tileEntity = minecraft.theWorld.getBlockTileEntity(hitResult.x, hitResult.y, hitResult.z);
            renderBlockOverlay(tileEntity);
        } else if (hitResult.hitType == HitResult.HitType.ENTITY) {
            renderEntityOverlay(hitResult.entity);
        }
    }
    @Override
    public void renderPreviewPost(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
        if (modSettings.getKeyDemoCycle().isPressed()){
            if (!keyPressed){
                if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
                    DemoManager.demoOffset -= 1;
                } else {
                    DemoManager.demoOffset += 1;
                }
                keyPressed = true;
            }
        } else {
            keyPressed = false;
        }
        TileEntity demoTileEntity = DemoManager.getCurrentEntry().tileEntity;
        Entity demoAnimal = DemoManager.getCurrentEntry().entity;
        if (demoTileEntity != null){
            renderBlockOverlay(demoTileEntity);
        } else if (demoAnimal != null) {
            renderEntityOverlay(demoAnimal);
        }
    }
    private void renderBlockOverlay(TileEntity tileEntity){
        setScale(modSettings.getScaleTooltips().value+0.5f);
        if (!modSettings.getBlockTooltips().value) return;
        if (minecraft.fontRenderer != null) {
            if (modSettings.getBlockAdvancedTooltips().value) {
                drawFunctionalBlocksData(tileEntity);
            }
        }
    }
    private void renderEntityOverlay(Entity entity){
        setScale(modSettings.getScaleTooltips().value+0.5f);
        if (!modSettings.getEntityTooltips().value) return;
        boolean isLivingEntity = (entity instanceof EntityLiving);
        EntityLiving entityLiving = isLivingEntity ? (EntityLiving) entity : null;

        if (modSettings.getEntityAdvancedTooltips().value) {
            if (minecraft.thePlayer instanceof EntityClientPlayerMP && BTWaila.canUseAdvancedTooltips) {
                EntityClientPlayerMP playerMP = (EntityClientPlayerMP) minecraft.thePlayer;
                playerMP.sendQueue.addToSendQueue(new PacketRequestEntityData(entity.id));
            }

            if (isLivingEntity) drawEntityHealth(entityLiving);
            for (EntityTooltip<?> tooltip : TooltipRegistry.entityTooltips) {
                if (tooltip.isInstance(entity) && tooltip.isInList(entity.getClass())) {
                    tooltip._drawAdvancedTooltip(entity, this);
                }
            }
        }
    }
    private void drawFunctionalBlocksData(TileEntity tileEntity) {
        if (tileEntity != null) {
            boolean askTileEntity = !(BTWaila.excludeContinuousTileEntityData.get(tileEntity.getClass()) != null ? BTWaila.excludeContinuousTileEntityData.get(tileEntity.getClass()) : false);
            if (minecraft.thePlayer instanceof EntityClientPlayerMP && BTWaila.canUseAdvancedTooltips && askTileEntity) {
                EntityClientPlayerMP playerMP = (EntityClientPlayerMP) minecraft.thePlayer;
                playerMP.sendQueue.addToSendQueue(new PacketRequestTileEntityData(tileEntity.x, tileEntity.y, tileEntity.z));
            }
            for (TileTooltip<?> tooltip : TooltipRegistry.tileTooltips) {
                if (tooltip.isInstance(tileEntity) && tooltip.isInList(tileEntity.getClass())) {
                    tooltip._drawAdvancedTooltip(tileEntity, this);
                }
            }
        }
    }
}
