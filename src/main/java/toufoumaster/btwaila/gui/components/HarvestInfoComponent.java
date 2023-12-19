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
import toufoumaster.btwaila.BTWailaClient;
import toufoumaster.btwaila.demo.DemoManager;
import toufoumaster.btwaila.mixin.interfaces.IOptions;
import toufoumaster.btwaila.mixin.mixins.accessors.IPlayerControllerAccessor;
import toufoumaster.btwaila.util.Colors;

import static toufoumaster.btwaila.BTWaila.translator;

public class HarvestInfoComponent extends WailaTextComponent {
    public HarvestInfoComponent(String key, Layout layout) {
        super(key, 8, layout);
    }
    @Override
    public int getAnchorY(ComponentAnchor anchor) {
        return (int)(anchor.yPosition * getYSize(Minecraft.getMinecraft(this)));
    }
    @Override
    public int getYSize(Minecraft mc) {
        if (!(mc.currentScreen instanceof GuiHudDesigner) && !this.isVisible(mc)) {
            return 0;
        }
        return height();
    }
    @Override
    public boolean isVisible(Minecraft minecraft) {
        return modSettings.getShowHarvestText().value && minecraft.gameSettings.immersiveMode.drawHotbar() && minecraft.objectMouseOver != null && minecraft.objectMouseOver.hitType == HitResult.HitType.TILE && minecraft.thePlayer != null && minecraft.thePlayer.gamemode == Gamemode.survival;
    }

    @Override
    public void renderPost(Minecraft minecraft, GuiIngame guiIngame, int xScreenSize, int yScreenSize, float f) {
        EntityPlayer player = minecraft.thePlayer;
        HitResult hitResult = minecraft.objectMouseOver;
        if (hitResult == null || player == null) {
            renderHarvestInfo(Colors.RED, "You shouldn't ever see this message.");
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
            renderHarvestInfo(miningLevelColor, harvestString);
        }
    }

    @Override
    public void renderPreviewPost(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
        if (modSettings.getShowHarvestText().value && DemoManager.getCurrentEntry().block != null){
            renderHarvestInfo(Colors.RED, translator.translateKey("btwaila.component.harvest.info.notharvestable"));
        }
    }
    protected void renderHarvestInfo(int miningLevelColor, String harvestString){
        drawStringJustified(harvestString, 0, getXSize(minecraft), miningLevelColor);
    }
}
