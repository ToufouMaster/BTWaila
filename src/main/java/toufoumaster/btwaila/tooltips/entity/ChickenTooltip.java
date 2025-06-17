package toufoumaster.btwaila.tooltips.entity;

import net.minecraft.core.entity.animal.MobChicken;
import toufoumaster.btwaila.demo.DemoEntry;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.EntityTooltip;

import java.util.Random;

import static toufoumaster.btwaila.BTWaila.translator;

public class ChickenTooltip extends EntityTooltip<MobChicken> {
    @Override
    public void initTooltip() {
        addClass(MobChicken.class);
    }

    @Override
    public void drawAdvancedTooltip(MobChicken chicken, AdvancedInfoComponent advancedInfoComponent) {
        String text = translator.translateKey("btwaila.tooltip.chicken.egg").replace("{timer}", String.valueOf(chicken.eggTimer/20));
        advancedInfoComponent.drawStringWithShadow(text, 0);
    }
    @Override
    public DemoEntry tooltipDemo(Random random){
        return new DemoEntry(new MobChicken(null));
    }
}
