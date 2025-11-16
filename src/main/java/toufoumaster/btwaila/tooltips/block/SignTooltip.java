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

        String username = UUIDHelper.getNameI18nFromUUID(owner, interfaceObject.worldObj);
        String ownerStr = translator.translateKey(TileTooltip.KEY_TILE_ENTITY_OWNER);

        advancedInfoComponent.drawStringWithShadow(String.format("%s: %s", ownerStr, username), 0);
    }
}
