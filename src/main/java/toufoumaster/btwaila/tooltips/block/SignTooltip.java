package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntitySign;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.mixin.mixins.accessors.TileEntitySignAccessor;
import toufoumaster.btwaila.tooltips.TileTooltip;
import toufoumaster.btwaila.util.UUIDHelper;

import java.util.UUID;

import static toufoumaster.btwaila.BTWaila.translator;

public class SignTooltip extends TileTooltip<TileEntitySign> {
    @Override
    public void initTooltip() {
        addClass(TileEntitySign.class);
    }

    @Override
    public void drawAdvancedTooltip(TileEntitySign interfaceObject, AdvancedInfoComponent advancedInfoComponent) {
        UUID owner = ((TileEntitySignAccessor)interfaceObject).getOwner();
        String text = translator.translateKey("btwaila.tooltip.sign.owner").replace("{id}", owner == null ? translator.translateKey("btwaila.tooltip.sign.owner.none") : String.valueOf(UUIDHelper.getNameFromUUID(owner)));
        advancedInfoComponent.drawStringWithShadow(text, 0);
    }
}
