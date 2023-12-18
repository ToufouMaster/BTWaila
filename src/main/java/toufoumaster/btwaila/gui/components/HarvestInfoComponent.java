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
import toufoumaster.btwaila.demo.DemoManager;
import toufoumaster.btwaila.mixin.mixins.accessors.IPlayerControllerAccessor;
import toufoumaster.btwaila.util.Colors;

import static toufoumaster.btwaila.BTWaila.translator;

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
            renderHarvestInfo(minecraft, Colors.RED, "You shouldn't ever see this message.", xScreenSize, yScreenSize);
            return;
        }
        Block block = Block.getBlock(minecraft.theWorld.getBlockId(hitResult.x, hitResult.y, hitResult.z));
        if (player.getGamemode() == Gamemode.survival) {
            int miningLevelColor = Colors.LIGHT_GREEN;
            String harvestString = translator.translateKey("btwaila.component.harvest.info.harvestable");
            if (!player.canHarvestBlock(block)) {
                harvestString = translator.translateKey("btwaila.component.harvest.info.notharvestable");
                miningLevelColor = Colors.LIGHT_RED;
            }
            float damage = ((IPlayerControllerAccessor)minecraft.playerController).getCurrentDamage();
            if (damage != 0) {
                harvestString = translator.translateKey("btwaila.component.harvest.info.harvesting").replace("{progress}", String.valueOf((int)(damage*100)));
            }
            renderHarvestInfo(minecraft, miningLevelColor, harvestString, xScreenSize, yScreenSize);
        }
    }

    @Override
    public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
        ySize = 3;
        if (DemoManager.getCurrentEntry().block != null){
            renderHarvestInfo(minecraft, Colors.RED, translator.translateKey("btwaila.component.harvest.info.notharvestable"), xScreenSize, yScreenSize);
        }
    }
    protected void renderHarvestInfo(Minecraft minecraft, int miningLevelColor, String harvestString, int xScreenSize, int yScreenSize){
        int x = getLayout().getComponentX(minecraft, this, xScreenSize);
        int y = getLayout().getComponentY(minecraft, this, yScreenSize);
        minecraft.fontRenderer.drawStringWithShadow(harvestString, x, y, miningLevelColor);
        ySize = 8;
    }
}
