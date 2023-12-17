package toufoumaster.btwaila.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;

public class BlockBaseInfoComponent extends MovableHudComponent {
    public BlockBaseInfoComponent(String key, int xSize, int ySize, Layout layout) {
        super(key, xSize, ySize, layout);
    }

    @Override
    public boolean isVisible(Minecraft minecraft) {
        return false;
    }

    @Override
    public void render(Minecraft minecraft, GuiIngame guiIngame, int i, int j, float f) {

    }

    @Override
    public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int i, int j) {

    }
}
