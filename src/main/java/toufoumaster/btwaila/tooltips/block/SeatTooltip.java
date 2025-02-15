package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntitySeat;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.MobHuman;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import toufoumaster.btwaila.demo.DemoEntry;
import toufoumaster.btwaila.demo.DemoManager;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;

import java.util.Random;

import static toufoumaster.btwaila.BTWaila.translator;

public class SeatTooltip extends TileTooltip<TileEntitySeat> {
    @Override
    public void initTooltip() {
        addClass(TileEntitySeat.class);
    }
    @Override
    public void drawAdvancedTooltip(TileEntitySeat seat, AdvancedInfoComponent advancedInfoComponent) {
        String entityName = AdvancedInfoComponent.getEntityName(seat.getPassenger());

        advancedInfoComponent.drawStringWithShadow(
                translator.translateKey("btwaila.tooltip.seat.passenger")
                        .replace("{name}", entityName), 0);
    }
    @Override
    public DemoEntry tooltipDemo(Random random){
        TileEntitySeat demoSeat = new TileEntitySeat();
        Mob demoHuman = new MobHuman(null);
        demoHuman.nickname = DemoManager.getRandomName(random);
        demoSeat.setPassenger(demoHuman);
        Block seat = Blocks.SEAT;
        return new DemoEntry(seat, 0, demoSeat, new ItemStack[]{Items.SEAT.getDefaultStack()});
    }
}
