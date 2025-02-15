package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import toufoumaster.btwaila.demo.DemoEntry;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;
import toufoumaster.btwaila.util.Colors;

import java.util.Random;

import static toufoumaster.btwaila.BTWaila.translator;

public class MobSpawnerTooltip extends TileTooltip<TileEntityMobSpawner> {
    @Override
    public void initTooltip() {
        addClass(TileEntityMobSpawner.class);
    }

    @Override
    public void drawAdvancedTooltip(TileEntityMobSpawner mobSpawner, AdvancedInfoComponent advancedInfoComponent) {
        boolean canSpawn = true;
        int canSpawnColor = Colors.LIGHT_GREEN;
        World world = advancedInfoComponent.getGame().currentWorld;
        if (world == null || world.getDifficulty().id() == 0 || (mobSpawner.getMobId() == null) || (mobSpawner.getMobId().equalsIgnoreCase("none"))) {
            canSpawn = false;
            canSpawnColor = Colors.LIGHT_RED;
        }
        int delay = mobSpawner.delay;
        String entityName = mobSpawner.getMobId();
        advancedInfoComponent.drawStringWithShadow(
                translator.translateKey("btwaila.tooltip.spawner.bound")
                        .replace("{name}", entityName != null ? entityName : translator.translateKey("btwaila.tooltip.spawner.name.default")), 0);
        advancedInfoComponent.drawStringWithShadow(translator.translateKey("btwaila.tooltip.spawner.canspawn").replace("{flag}", String.valueOf(canSpawn)), 0, canSpawnColor);
        advancedInfoComponent.drawStringWithShadow(translator.translateKey("btwaila.tooltip.spawner.spawndelay").replace("{delay}", String.valueOf(delay)), 0, canSpawnColor);
    }
    @Override
    public DemoEntry tooltipDemo(Random random){
        TileEntityMobSpawner demoSpawner = new TileEntityMobSpawner();
        NamespaceID[] entityIds = EntityDispatcher.idToClassMap.keySet().toArray(new NamespaceID[0]);
        demoSpawner.setMobId(entityIds[random.nextInt(entityIds.length)].toString());
        Block<?> spawner = Blocks.MOBSPAWNER;
        return new DemoEntry(spawner, 0, demoSpawner, new ItemStack[]{spawner.getDefaultStack()});
    }
}
