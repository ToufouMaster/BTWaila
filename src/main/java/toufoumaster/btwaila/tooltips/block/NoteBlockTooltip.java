package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityNote;
import net.minecraft.core.block.material.Material;
import toufoumaster.btwaila.*;
import toufoumaster.btwaila.gui.GuiBlockOverlay;

import java.util.HashMap;

public class NoteBlockTooltip implements IBTWailaCustomBlockTooltip {

    @Override
    public void addTooltip() {
        BTWaila.LOGGER.info("Adding tooltips for: " + this.getClass().getSimpleName());
        TooltipGroup tooltipGroup = new TooltipGroup("minecraft", TileEntityNote.class, this);
        tooltipGroup.addTooltip(TileEntityNote.class);
        TooltipRegistry.tooltipMap.add(tooltipGroup);
    }

    @Override
    public void drawAdvancedTooltip(TileEntity tileEntity, GuiBlockOverlay guiBlockOverlay) {
        TileEntityNote noteEntity = (TileEntityNote) tileEntity;
        int note = noteEntity.note;
        String blockModifier = "No block";
        HashMap<Material, String> materialList = new HashMap<Material, String>() {{
            put(Material.stone, "Stone");
            put(Material.sand, "Sand");
            put(Material.glass, "Glass");
            put(Material.wood, "Wood");
        }};
        Material material = guiBlockOverlay.getGame().theWorld.getBlockMaterial(noteEntity.x, noteEntity.y - 1, noteEntity.z);
        if (material != null && materialList.containsKey(material)) {
            blockModifier = materialList.get(material);
        }
        String noteString = (Integer.toHexString(note).length() == 1) ? "0"+Integer.toHexString(note) : Integer.toHexString(note);
        guiBlockOverlay.drawStringWithShadow("Note pitch: #"+noteString, 0);
        guiBlockOverlay.drawStringWithShadow("Block modifier: "+blockModifier, 0);
    }
}
