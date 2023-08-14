package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import toufoumaster.btwaila.*;
import toufoumaster.btwaila.gui.GuiBlockOverlay;

public class MobSpawnerTooltip implements IBTWailaCustomBlockTooltip {

    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", TileEntityMobSpawner.class, this);
        tooltipGroup.addTooltip(TileEntityMobSpawner.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(TileEntity tileEntity, GuiBlockOverlay guiBlockOverlay) {
        TileEntityMobSpawner mobSpawner = (TileEntityMobSpawner) tileEntity;
        boolean canSpawn = true;
        int canSpawnColor = GuiBlockOverlay.Colors.LIGHT_GREEN;
        if ((guiBlockOverlay.getGame().theWorld.difficultySetting == 0)
                || (mobSpawner.getMobID() == null)
                || (mobSpawner.getMobID().equalsIgnoreCase("none"))
        ) {
            canSpawn = false;
            canSpawnColor = GuiBlockOverlay.Colors.LIGHT_RED;
        }
        int delay = mobSpawner.delay;
        String entityName = mobSpawner.getMobID();
        guiBlockOverlay.drawStringWithShadow("Binded entity: "+entityName, 0);
        guiBlockOverlay.drawStringWithShadow("Can spawn: "+canSpawn, 0, canSpawnColor);
        guiBlockOverlay.drawStringWithShadow("Delay before spawn: "+delay+"t", 0, canSpawnColor);
    }
}
