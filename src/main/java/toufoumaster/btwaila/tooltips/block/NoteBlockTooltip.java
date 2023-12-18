package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityNote;
import net.minecraft.core.block.material.Material;
import toufoumaster.btwaila.*;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TooltipGroup;
import toufoumaster.btwaila.tooltips.TooltipRegistry;
import toufoumaster.btwaila.tooltips.interfaces.IBTWailaCustomBlockTooltip;

import java.util.HashMap;

import static toufoumaster.btwaila.BTWaila.translator;

public class NoteBlockTooltip implements IBTWailaCustomBlockTooltip {

    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", TileEntityNote.class, this);
        tooltipGroup.addTooltip(TileEntityNote.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(TileEntity tileEntity, AdvancedInfoComponent advancedInfoComponent) {
        TileEntityNote noteEntity = (TileEntityNote) tileEntity;
        int note = noteEntity.note;
        String blockModifier = translator.translateKey("btwaila.tooltip.noteblock.material.none");
        HashMap<Material, String> materialList = new HashMap<Material, String>() {{
            put(Material.stone, translator.translateKey("btwaila.tooltip.noteblock.material.stone"));
            put(Material.sand, translator.translateKey("btwaila.tooltip.noteblock.material.sand"));
            put(Material.glass, translator.translateKey("btwaila.tooltip.noteblock.material.glass"));
            put(Material.wood, translator.translateKey("btwaila.tooltip.noteblock.material.wood"));
        }};
        Material material = advancedInfoComponent.getGame().theWorld.getBlockMaterial(noteEntity.x, noteEntity.y - 1, noteEntity.z);
        if (material != null && materialList.containsKey(material)) {
            blockModifier = materialList.get(material);
        }
        String noteString = (Integer.toHexString(note).length() == 1) ? "0"+Integer.toHexString(note) : Integer.toHexString(note);
        advancedInfoComponent.drawStringWithShadow(translator.translateKey("btwaila.tooltip.noteblock.pitch").replace("{note}", noteString), 0);
        advancedInfoComponent.drawStringWithShadow(translator.translateKey("btwaila.tooltip.noteblock.modifier").replace("{modifier}", blockModifier), 0);
    }
}
