package toufoumaster.btwaila.tooltips.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntityNote;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import toufoumaster.btwaila.demo.DemoEntry;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;
import toufoumaster.btwaila.tooltips.TileTooltip;

import java.util.HashMap;
import java.util.Random;

import static toufoumaster.btwaila.BTWaila.translator;

public class NoteBlockTooltip extends TileTooltip<TileEntityNote> {
    public static HashMap<Material, String> materialList = new HashMap<Material, String>() {{
        put(Material.stone, translator.translateKey("btwaila.tooltip.noteblock.material.stone"));
        put(Material.sand, translator.translateKey("btwaila.tooltip.noteblock.material.sand"));
        put(Material.glass, translator.translateKey("btwaila.tooltip.noteblock.material.glass"));
        put(Material.wood, translator.translateKey("btwaila.tooltip.noteblock.material.wood"));
    }};
    @Override
    public void initTooltip() {
        addClass(TileEntityNote.class);
    }
    @Override
    public void drawAdvancedTooltip(TileEntityNote entityNote, AdvancedInfoComponent advancedInfoComponent) {
        int note = entityNote.note;
        String blockModifier = translator.translateKey("btwaila.tooltip.noteblock.material.none");
        World world = advancedInfoComponent.getGame().theWorld;
        if (world != null){
            Material material = advancedInfoComponent.getGame().theWorld.getBlockMaterial(entityNote.x, entityNote.y - 1, entityNote.z);
            if (material != null && materialList.containsKey(material)) {
                blockModifier = materialList.get(material);
            }
        }
        String noteString = (Integer.toHexString(note).length() == 1) ? "0"+Integer.toHexString(note) : Integer.toHexString(note);
        advancedInfoComponent.drawStringWithShadow(translator.translateKey("btwaila.tooltip.noteblock.pitch").replace("{note}", noteString), 0);
        advancedInfoComponent.drawStringWithShadow(translator.translateKey("btwaila.tooltip.noteblock.modifier").replace("{modifier}", blockModifier), 0);
    }
    @Override
    public DemoEntry tooltipDemo(Random random){
        TileEntityNote demoNote = new TileEntityNote();
        demoNote.note = (byte) random.nextInt(25);
        Block noteBlock = Block.noteblock;
        return new DemoEntry(noteBlock, 0, demoNote, new ItemStack[]{noteBlock.getDefaultStack()});
    }
}
