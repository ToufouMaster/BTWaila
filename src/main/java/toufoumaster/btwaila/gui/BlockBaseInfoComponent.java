package toufoumaster.btwaila.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import toufoumaster.btwaila.IOptions;
import toufoumaster.btwaila.util.Colors;

import static toufoumaster.btwaila.gui.GuiBlockOverlay.translator;

public class BlockBaseInfoComponent extends MovableHudComponent {
    public BlockBaseInfoComponent(String key, Layout layout) {
        super(key, 16 * 9, 24, layout);
    }

    @Override
    public int getAnchorY(ComponentAnchor anchor) {
        if (anchor.yPosition == 0.0f && !(anchor == ComponentAnchor.TOP_CENTER)){
            return super.getAnchorY(anchor) + 8;
        }
        return super.getAnchorY(anchor);
    }
    @Override
    public boolean isVisible(Minecraft minecraft) {
        return minecraft.gameSettings.immersiveMode.drawOutline();
    }

    @Override
    public void render(Minecraft minecraft, GuiIngame guiIngame, int xScreenSize, int yScreenSize, float f) {
        HitResult hitResult = minecraft.objectMouseOver;
        if (hitResult == null) {return;}
        if (hitResult.hitType == HitResult.HitType.TILE) {
            Block block = Block.getBlock(minecraft.theWorld.getBlockId(hitResult.x, hitResult.y, hitResult.z));
            int meta = minecraft.theWorld.getBlockMetadata(hitResult.x, hitResult.y, hitResult.z);
            ItemStack[] drops = block.getBreakResult(minecraft.theWorld, EnumDropCause.PICK_BLOCK, hitResult.x, hitResult.y, hitResult.z, minecraft.theWorld.getBlockMetadata(hitResult.x, hitResult.y, hitResult.z), null);
            baseBlockInfo(minecraft,block, meta, drops, xScreenSize, yScreenSize);
        } else if (hitResult.hitType == HitResult.HitType.ENTITY) {
            baseEntityInfo(minecraft, hitResult.entity, xScreenSize, yScreenSize);
        }
    }

    @Override
    public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
        int meta = 8 * 16;
        baseBlockInfo(minecraft, Block.chestPlanksOakPainted, meta, new ItemStack[]{new ItemStack(Block.chestPlanksOakPainted, 1, meta)}, xScreenSize, yScreenSize);
    }
    protected void baseBlockInfo(Minecraft minecraft, Block block, int blockMetadata, ItemStack[] blockDrops, int xScreenSize, int yScreenSize){
        int x = getLayout().getComponentX(minecraft, this, xScreenSize);
        int y = getLayout().getComponentY(minecraft, this, yScreenSize) + 8;
        IOptions modSettings = (IOptions)minecraft.gameSettings;

        if (!modSettings.getBlockTooltips().value) return;
        if (minecraft.fontRenderer == null) return;

        ItemStack renderItem = new ItemStack(block, 1, blockMetadata);
        if (blockDrops != null && blockDrops.length > 0) renderItem = blockDrops[0];

        String languageKey = renderItem.getItemName();

        String blockName = translator.translateNameKey(languageKey);
        String blockDesc = translator.translateDescKey(languageKey);

        y = drawStringJustified(minecraft,blockName, x, y,getXSize(minecraft), Colors.WHITE);
        drawStringJustified(minecraft,blockDesc, x,y, getXSize(minecraft), Colors.LIGHT_GRAY);
    }
    protected void baseEntityInfo(Minecraft minecraft, Entity entity, int xScreenSize, int yScreenSize){
        int x = getLayout().getComponentX(minecraft, this, xScreenSize);
        int y = getLayout().getComponentY(minecraft, this, yScreenSize) + 8;
        IOptions gameSettings = (IOptions)minecraft.gameSettings;
        if (!gameSettings.getEntityTooltips().value) return;
        boolean isLivingEntity = (entity instanceof EntityLiving);
        EntityLiving entityLiving = isLivingEntity ? (EntityLiving) entity : null;

        String entityName = isLivingEntity ? entityLiving.getDisplayName() : null;
        if (entityName == null || entityName.equalsIgnoreCase("§0")) {
            MobInfoRegistry.MobInfo info = MobInfoRegistry.getMobInfo(entity.getClass());
            if (info != null){
                entityName = translator.translateKey(info.getNameTranslationKey());
            } else {
                entityName = EntityDispatcher.classToKeyMap.get(entity.getClass());
            }
        }
        if (entityName == null){
            entityName = entity.getClass().getSimpleName();
        }

        int color = Colors.WHITE;
        if (isLivingEntity) {
            color = Colors.GREEN;
            if (entity instanceof EntityMonster) {
                color = Colors.RED;
            }
            else if (entity instanceof EntityPlayer) {
                color = entityLiving.chatColor;
            }
        }

        minecraft.fontRenderer.drawStringWithShadow(entityName, x, y, color);
    }

    public int drawStringJustified(Minecraft minecraft,String text, int x, int y, int maxWidth, int color){
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        StringBuilder prevline;
        int wordCount = 0;
        for (String word: words) {
            prevline = new StringBuilder(line.toString());
            line.append(word).append(" ");
            wordCount++;
            if (minecraft.fontRenderer.getStringWidth(line.toString().trim()) > maxWidth){
                if (wordCount <= 1){
                    minecraft.fontRenderer.drawStringWithShadow(line.toString(), x, y, color);
                    y += 8;
                    line = new StringBuilder(word).append(" ");
                    wordCount = 0;
                    continue;
                }
                minecraft.fontRenderer.drawStringWithShadow(prevline.toString(), x, y, color);
                y += 8;
                line = new StringBuilder(word).append(" ");
                wordCount = 0;
            }
        }
        String remainder = line.toString();
        if (!remainder.isEmpty()){
            minecraft.fontRenderer.drawStringWithShadow(remainder, x, y, color);
            y += 8;
        }
        return y;
    }
}
