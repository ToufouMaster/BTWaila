package toufoumaster.btwaila.tooltips.entity;

import net.minecraft.core.entity.EntityPainting;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.EntityTooltip;

import static toufoumaster.btwaila.BTWaila.translator;

public class PaintingTooltip extends EntityTooltip<EntityPainting> {
    @Override
    public void initTooltip() {
        addClass(EntityPainting.class);
    }

    @Override
    public void drawAdvancedTooltip(EntityPainting painting, AdvancedInfoComponent advancedInfoComponent) {
        advancedInfoComponent.drawStringWithShadow(translator.translateKey("btwaila.tooltip.painting.title").replace("{id}", painting.art.title), 0);
        advancedInfoComponent.drawStringWithShadow(translator.translateKey("btwaila.tooltip.painting.artist").replace("{id}", painting.art.artist), 0);
    }
}
