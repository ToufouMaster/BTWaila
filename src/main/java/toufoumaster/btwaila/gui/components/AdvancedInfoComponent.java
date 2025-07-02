package toufoumaster.btwaila.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocalMultiplayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScreenHudDesigner;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.ComponentAnchor;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.core.Global;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicChest;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.world.World;
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
        return (int)(anchor.yPosition * getYSize(Minecraft.getMinecraft()));
    }
    @Override
    public int getYSize(Minecraft mc) {
        if (!(mc.currentScreen instanceof ScreenHudDesigner) && !this.isVisible(mc)) {
            return 0;
        }
        return height();
    }
    @Override
    public boolean isVisible(Minecraft minecraft) {
        return minecraft.gameSettings.immersiveMode.drawHotbar();
    }

    @Override
    public void renderPost(Minecraft minecraft, HudIngame HudIngame, int xScreenSize, int yScreenSize, float partialTick) {
        HitResult hitResult = minecraft.objectMouseOver;
        if (hitResult == null) {return;}
        if (hitResult.hitType == HitResult.HitType.TILE) {
            TileEntity tileEntity = minecraft.currentWorld.getTileEntity(hitResult.x, hitResult.y, hitResult.z);
            renderBlockOverlay(tileEntity);
        } else if (hitResult.hitType == HitResult.HitType.ENTITY) {
            renderEntityOverlay(hitResult.entity);
        }
    }
    @Override
    public void renderPreviewPost(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
        if (modSettings().bTWaila$getKeyDemoCycle().isPressed()){
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
        setScale(modSettings().bTWaila$getScaleTooltips().value+0.5f);
        if (!modSettings().bTWaila$getBlockTooltips().value) return;
        if (minecraft.font != null) {
            if (modSettings().bTWaila$getBlockAdvancedTooltips().value) {
                drawFunctionalBlocksData(tileEntity);
            }
        }
    }
    private void renderEntityOverlay(Entity entity){
        setScale(modSettings().bTWaila$getScaleTooltips().value+0.5f);
        if (!modSettings().bTWaila$getEntityTooltips().value) return;
        boolean isLivingEntity = (entity instanceof Mob);
        Mob Mob = isLivingEntity ? (Mob) entity : null;

        if (modSettings().bTWaila$getEntityAdvancedTooltips().value) {
            if (minecraft.thePlayer instanceof PlayerLocalMultiplayer && BTWaila.canUseAdvancedTooltips) {
                PlayerLocalMultiplayer playerMP = (PlayerLocalMultiplayer) minecraft.thePlayer;
                playerMP.sendQueue.addToSendQueue(new PacketRequestEntityData(entity.id));
            }

            if (isLivingEntity) drawEntityHealth(Mob);
            for (EntityTooltip<?> tooltip : TooltipRegistry.entityTooltips) {
                if (tooltip.isInstance(entity) && tooltip.isInList(entity.getClass())) {
                    tooltip._drawAdvancedTooltip(entity, this);
                }
            }
        }
    }
    private void drawFunctionalBlocksData(TileEntity tileEntity) {
        if (tileEntity != null && tileEntity.worldObj != null) {
            boolean askTileEntity = !(BTWaila.excludeContinuousTileEntityData.get(tileEntity.getClass()) != null ? BTWaila.excludeContinuousTileEntityData.get(tileEntity.getClass()) : false);
            if (tileEntity.worldObj == null) return;
            if (!Global.isServer && BTWaila.canUseAdvancedTooltips && askTileEntity) {
                PlayerLocalMultiplayer playerMP = (PlayerLocalMultiplayer) minecraft.thePlayer;
                playerMP.sendQueue.addToSendQueue(new PacketRequestTileEntityData(tileEntity.x, tileEntity.y, tileEntity.z));
                if (tileEntity instanceof TileEntityChest){
                    requestOtherHalfOfChest(playerMP.world,tileEntity.x, tileEntity.y, tileEntity.z, playerMP);
                }
            }
            for (TileTooltip<?> tooltip : TooltipRegistry.tileTooltips) {
                if (tooltip.isInstance(tileEntity) && tooltip.isInList(tileEntity.getClass())) {
                    tooltip._drawAdvancedTooltip(tileEntity, this);
                }
            }
        }
    }
    private void requestOtherHalfOfChest(World world, int x, int y, int z, PlayerLocalMultiplayer playerMP) {
        int meta = world.getBlockMetadata(x, y, z);
        BlockLogicChest.Type type = BlockLogicChest.getTypeFromMeta(meta);
        if (type != BlockLogicChest.Type.SINGLE) {
            int otherMeta;
            Direction direction = BlockLogicChest.getDirectionFromMeta(meta);
            int otherChestX = x;
            int otherChestZ = z;
            if (direction == Direction.NORTH) {
                if (type == BlockLogicChest.Type.LEFT) {
                    --otherChestX;
                }
                if (type == BlockLogicChest.Type.RIGHT) {
                    ++otherChestX;
                }
            }
            if (direction == Direction.EAST) {
                if (type == BlockLogicChest.Type.LEFT) {
                    --otherChestZ;
                }
                if (type == BlockLogicChest.Type.RIGHT) {
                    ++otherChestZ;
                }
            }
            if (direction == Direction.SOUTH) {
                if (type == BlockLogicChest.Type.LEFT) {
                    ++otherChestX;
                }
                if (type == BlockLogicChest.Type.RIGHT) {
                    --otherChestX;
                }
            }
            if (direction == Direction.WEST) {
                if (type == BlockLogicChest.Type.LEFT) {
                    ++otherChestZ;
                }
                if (type == BlockLogicChest.Type.RIGHT) {
                    --otherChestZ;
                }
            }
            if (BlockLogicChest.isChest(world, otherChestX, y, otherChestZ) && BlockLogicChest.getDirectionFromMeta(otherMeta = world.getBlockMetadata(otherChestX, y, otherChestZ)) == direction) {
                BlockLogicChest.Type otherType = BlockLogicChest.getTypeFromMeta(otherMeta);
                if (type == BlockLogicChest.Type.LEFT && otherType == BlockLogicChest.Type.RIGHT || type == BlockLogicChest.Type.RIGHT && otherType == BlockLogicChest.Type.LEFT) {
                    playerMP.sendQueue.addToSendQueue(new PacketRequestTileEntityData(otherChestX, y, otherChestZ));
                }
            }
        }
    }
}
