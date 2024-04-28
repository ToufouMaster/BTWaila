package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntitySign;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.mixin.mixins.accessors.TileEntitySignAccessor;
import toufoumaster.btwaila.tooltips.TileTooltip;

import static toufoumaster.btwaila.BTWaila.translator;

public class SignTooltip extends TileTooltip<TileEntitySign> {
    @Override
    public void initTooltip() {
        addClass(TileEntitySign.class);
    }

    @Override
    public void drawAdvancedTooltip(TileEntitySign interfaceObject, AdvancedInfoComponent advancedInfoComponent) {
        String owner = ((TileEntitySignAccessor)interfaceObject).getOwner();
        String text = translator.translateKey("btwaila.tooltip.sign.owner").replace("{id}", owner.isEmpty() ? translator.translateKey("btwaila.tooltip.sign.owner.none") : owner);
        advancedInfoComponent.drawStringWithShadow(text, 0);
    }
}
