package toufoumaster.btwaila.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScreenHudDesigner;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.ComponentAnchor;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.util.phys.HitResult;
import toufoumaster.btwaila.demo.DemoManager;
import toufoumaster.btwaila.mixin.mixins.accessors.IPlayerControllerAccessor;
import toufoumaster.btwaila.util.Colors;

import static toufoumaster.btwaila.BTWaila.translator;

public class HarvestInfoComponent extends WailaTextComponent {
    public HarvestInfoComponent(String key, Layout layout) {
        super(key, 8, layout);
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
        return modSettings().bTWaila$getShowHarvestText().value && minecraft.gameSettings.immersiveMode.drawHotbar() && minecraft.objectMouseOver != null && minecraft.objectMouseOver.hitType == HitResult.HitType.TILE && minecraft.thePlayer != null && minecraft.thePlayer.gamemode == Gamemode.survival;
    }

    @Override
    public void renderPost(Minecraft minecraft, HudIngame HudIngame, int xScreenSize, int yScreenSize, float f) {
        Player player = minecraft.thePlayer;
        HitResult hitResult = minecraft.objectMouseOver;
        if (hitResult == null || player == null) {
            renderHarvestInfo(Colors.RED, "You shouldn't ever see this message.");
            return;
        }
        Block<?> block = minecraft.currentWorld.getBlock(hitResult.x, hitResult.y, hitResult.z);
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
        if (modSettings().bTWaila$getShowHarvestText().value && DemoManager.getCurrentEntry().block != null){
            renderHarvestInfo(Colors.RED, translator.translateKey("btwaila.component.harvest.info.notharvestable"));
        }
    }
    protected void renderHarvestInfo(int miningLevelColor, String harvestString){
        drawStringJustified(harvestString, 0, getXSize(minecraft), miningLevelColor);
    }
}
