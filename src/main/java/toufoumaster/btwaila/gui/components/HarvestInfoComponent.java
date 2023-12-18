package toufoumaster.btwaila.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiHudDesigner;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.gamemode.Gamemode;
import toufoumaster.btwaila.demo.DemoEntry;
import toufoumaster.btwaila.mixin.mixins.accessors.IPlayerControllerAccessor;
import toufoumaster.btwaila.util.Colors;

public class HarvestInfoComponent extends MovableHudComponent {
    private int ySize;
    public HarvestInfoComponent(String key, Layout layout) {
        super(key, 16 * 9, 8, layout);
    }
    @Override
    public int getAnchorY(ComponentAnchor anchor) {
        return (int)(anchor.yPosition * getYSize(Minecraft.getMinecraft(this)));
    }
    @Override
    public int getXSize(Minecraft mc) {
        return 16 * 9;
    }
    @Override
    public int getYSize(Minecraft mc) {
        if (!(mc.currentScreen instanceof GuiHudDesigner) && !this.isVisible(mc)) {
            return 0;
        }
        return ySize;
    }

    @Override
    public boolean isVisible(Minecraft minecraft) {
        return minecraft.gameSettings.immersiveMode.drawHotbar() && minecraft.objectMouseOver != null && minecraft.objectMouseOver.hitType == HitResult.HitType.TILE && minecraft.thePlayer != null && minecraft.thePlayer.gamemode == Gamemode.survival;
    }

    @Override
    public void render(Minecraft minecraft, GuiIngame guiIngame, int xScreenSize, int yScreenSize, float f) {
        ySize = 0;
        EntityPlayer player = minecraft.thePlayer;
        HitResult hitResult = minecraft.objectMouseOver;
        if (hitResult == null || player == null) {
            renderHarvestInfo(minecraft, Colors.RED, "You shouldn't ever see this message.", 2f, xScreenSize, yScreenSize);
            return;
        }
        Block block = Block.getBlock(minecraft.theWorld.getBlockId(hitResult.x, hitResult.y, hitResult.z));
        if (player.getGamemode() == Gamemode.survival) {
            int miningLevelColor = Colors.LIGHT_GREEN;
            String harvestString = "Harvestable with current tool";
            if (!player.canHarvestBlock(block)) {
                harvestString = "Cannot be harvested with current tool";
                miningLevelColor = Colors.LIGHT_RED;
            }
            float damage = ((IPlayerControllerAccessor)minecraft.playerController).getCurrentDamage();
            if (damage != 0) {
                harvestString = "Harvesting: "+(int)(damage*100)+"%";
            }
            renderHarvestInfo(minecraft, miningLevelColor, harvestString, damage, xScreenSize, yScreenSize);
        }
    }

    @Override
    public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
        ySize = 3;
        if (DemoEntry.getCurrentEntry().block != null){
            renderHarvestInfo(minecraft, Colors.RED, "Harvestable with current tool", 0.5f, xScreenSize, yScreenSize);
        }
    }
    protected void renderHarvestInfo(Minecraft minecraft, int miningLevelColor, String harvestString, float damage, int xScreenSize, int yScreenSize){
        int x = getLayout().getComponentX(minecraft, this, xScreenSize);
        int y = getLayout().getComponentY(minecraft, this, yScreenSize);
        if (damage != 0) {
            harvestString = "Harvesting: "+(int)(damage*100)+"%";
        }
        minecraft.fontRenderer.drawStringWithShadow(harvestString, x, y, miningLevelColor);
        ySize = 8;
    }
}
