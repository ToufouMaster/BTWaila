package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import toufoumaster.btwaila.*;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TooltipGroup;
import toufoumaster.btwaila.tooltips.TooltipRegistry;
import toufoumaster.btwaila.tooltips.interfaces.IBTWailaCustomBlockTooltip;
import toufoumaster.btwaila.util.Colors;

public class MobSpawnerTooltip implements IBTWailaCustomBlockTooltip {

    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", TileEntityMobSpawner.class, this);
        tooltipGroup.addTooltip(TileEntityMobSpawner.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(TileEntity tileEntity, AdvancedInfoComponent advancedInfoComponent) {
        TileEntityMobSpawner mobSpawner = (TileEntityMobSpawner) tileEntity;
        boolean canSpawn = true;
        int canSpawnColor = Colors.LIGHT_GREEN;
        if ((advancedInfoComponent.getGame().theWorld.difficultySetting == 0)
                || (mobSpawner.getMobId() == null)
                || (mobSpawner.getMobId().equalsIgnoreCase("none"))
        ) {
            canSpawn = false;
            canSpawnColor = Colors.LIGHT_RED;
        }
        int delay = mobSpawner.delay;
        String entityName = mobSpawner.getMobId();
        advancedInfoComponent.drawStringWithShadow("Binded entity: "+entityName, 0);
        advancedInfoComponent.drawStringWithShadow("Can spawn: "+canSpawn, 0, canSpawnColor);
        advancedInfoComponent.drawStringWithShadow("Delay before spawn: "+delay+"t", 0, canSpawnColor);
    }
}
