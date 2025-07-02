package toufoumaster.btwaila.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScreenHudDesigner;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.ComponentAnchor;
import net.minecraft.client.gui.hud.component.HudComponent;
import net.minecraft.client.gui.hud.component.HudComponents;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.client.option.enums.TooltipStyle;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.Texture;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.phys.HitResult;
import org.lwjgl.opengl.GL11;
import toufoumaster.btwaila.BTWailaClient;
import toufoumaster.btwaila.demo.DemoManager;
import toufoumaster.btwaila.util.Colors;

import static toufoumaster.btwaila.BTWaila.translator;
import static toufoumaster.btwaila.BTWailaClient.gameSettings;
import static toufoumaster.btwaila.BTWailaClient.modSettings;

public class BaseInfoComponent extends WailaTextComponent {
    private boolean drawing = false;
    private final int backgroundHPadding = 4;
    private final int backgroundVPadding = 4;
    private final int topPadding = backgroundVPadding;
    public BaseInfoComponent(String key, Layout layout) {
        super(key, 24, layout);
    }

    @Override
    public int getAnchorY(ComponentAnchor anchor) {
        if (anchor.yPosition == 0.0f && !(anchor == ComponentAnchor.TOP_CENTER)){
            return (int)(anchor.yPosition * getYSize(minecraft)) + topPadding;
        }
        return (int)(anchor.yPosition * getYSize(minecraft));
    }
    @Override
    public int getYSize(Minecraft mc) {
        if (!(mc.currentScreen instanceof ScreenHudDesigner) && !this.isVisible(mc)) {
            return 0;
        }
        return height();
    }

    public void drawBoxyTexture(int minX, int minY, double width, double height, float opacity) {
        GL11.glColor4f(1, 1, 1 , opacity);
        Tessellator tl = Tessellator.instance;
        int maxX = (int) (minX+width);
        int maxY = (int) (minY+height);
        int bottomLeftCornerY = maxY - 7;
        int topRightCornerX = maxX - 7;
        int bottomRightCornerX = maxX - 7;
        int bottomRightCornerY = maxY - 7;
        int horWidth = maxX - minX - 14;
        int vertHeight = maxY - minY - 14;
        int horWidth2 = maxX - minX - 6;
        int vertHeight2 = maxY - minY - 6;
        tl.startDrawingQuads();
        tl.drawRectangleWithUV(minX, minY, 7, 7, 0.0F, 0.0F, 0.21875F, 0.21875F);
        tl.drawRectangleWithUV(minX, bottomLeftCornerY, 7, 7, 0.0F, 0.21875F, 0.21875F, 0.21875F);
        tl.drawRectangleWithUV(topRightCornerX, minY, 7, 7, 0.21875F, 0.0F, 0.21875F, 0.21875F);
        tl.drawRectangleWithUV(bottomRightCornerX, bottomRightCornerY, 7, 7, 0.21875F, 0.21875F, 0.21875F, 0.21875F);

        for(int x = minX + 7; x < minX + 7 + horWidth / 11 * 11; x += 11) {
            tl.drawRectangleWithUV(x, minY, 11, 3, 0.4375F, 0.0F, 0.34375F, 0.09375F);
        }

        int finalWidth = horWidth - horWidth / 11 * 11;
        tl.drawRectangleWithUV(topRightCornerX - finalWidth, minY, finalWidth, 3, 0.4375F, 0.0F, ((float)finalWidth / 32.0F), 0.09375F);

        for(int x = minX + 7; x < minX + 7 + horWidth / 11 * 11; x += 11) {
            tl.drawRectangleWithUV(x, maxY - 3, 11, 3, 0.4375F, 0.34375F, 0.34375F, 0.09375F);
        }

        tl.drawRectangleWithUV(bottomRightCornerX - finalWidth, maxY - 3, finalWidth, 3, 0.4375F, 0.34375F, ((float)finalWidth / 32.0F), 0.09375F);

        for(int y = minY + 7; y < minY + 7 + vertHeight / 11 * 11; y += 11) {
            tl.drawRectangleWithUV(minX, y, 3, 11, 0.0F, 0.4375F, 0.09375F, 0.34375F);
        }

        int finalHeight = vertHeight - vertHeight / 11 * 11;
        tl.drawRectangleWithUV(minX, bottomLeftCornerY - finalHeight, 3, finalHeight, 0.0F, 0.4375F, 0.09375F, ((float)finalHeight / 32.0F));

        for(int y = minY + 7; y < minY + 7 + vertHeight / 11 * 11; y += 11) {
            tl.drawRectangleWithUV(maxX - 3, y, 3, 11, 0.34375F, 0.4375F, 0.09375F, 0.34375F);
        }

        tl.drawRectangleWithUV(maxX - 3, bottomRightCornerY - finalHeight, 3, finalHeight, 0.34375F, 0.4375F, 0.09375F, ((float)finalHeight / 32.0F));

        for(int x = minX + 3; x < minX + 3 + horWidth2 / 8 * 8; x += 8) {
            for(int y = minY + 3; y < minY + 3 + vertHeight2 / 8 * 8; y += 8) {
                tl.drawRectangleWithUV(x, y, 8, 8, 0.4375F, 0.4375F, 0.25F, 0.25F);
            }
        }

        int finalHeight2 = vertHeight2 - vertHeight2 / 8 * 8;
        int finalWidth2 = horWidth2 - horWidth2 / 8 * 8;

        for(int x = minX + 3; x < minX + 3 + horWidth2 / 8 * 8; x += 8) {
            tl.drawRectangleWithUV(x, maxY - 3 - finalHeight2, 8, finalHeight2, 0.4375F, 0.4375F, 0.25F, ((float)finalHeight2 / 32.0F));
        }

        for(int y = minY + 3; y < minY + 3 + vertHeight2 / 8 * 8; y += 8) {
            tl.drawRectangleWithUV(maxX - 3 - finalWidth2, y, finalWidth2, 8, 0.4375F, 0.4375F, ((float)finalWidth2 / 32.0F), 0.25F);
        }

        tl.drawRectangleWithUV(maxX - 3 - finalWidth2, maxY - 3 - finalHeight2, finalWidth2, finalHeight2, 0.4375F, 0.4375F, ((float)finalWidth / 32.0F), ((float)finalWidth2 / 32.0F));
        tl.draw();
        GL11.glColor4f(1, 1, 1 , 1);
    }

    public void renderBackground(int xScreenSize, int yScreenSize) {
        if (!isVisible(minecraft) || !drawing) return;
        int minX = getLayout().getComponentX(minecraft, this, xScreenSize);
        int minY = getLayout().getComponentY(minecraft, this, yScreenSize);
        int maxX = minX + getXSize(minecraft);
        int maxY = minY + getYSize(minecraft);
        for (HudComponent component : HudComponents.INSTANCE.getComponents()) {
            if (component == this) continue;
            try {
                if (!component.isVisible(minecraft)) continue;
            } catch (NullPointerException e) {
                continue;
            }
            if (!WailaTextComponent.isComponentDeepAnchoredTo(component, this)) continue;

            int compMinX = component.getLayout().getComponentX(minecraft, component, xScreenSize);
            if (compMinX < minX) minX = compMinX;

            int compMinY = component.getLayout().getComponentY(minecraft, component, yScreenSize);
            if (compMinY < minY) minY = compMinY;

            int compMaxX = compMinX+component.getXSize(minecraft);
            if (compMaxX > maxX) maxX = compMaxX;

            int compMaxY = compMinY+component.getYSize(minecraft);
            if (compMaxY > maxY) maxY = compMaxY;
        }

        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 1);
        TextureManager textureManager = minecraft.textureManager;
        //String style = modSettings().bTWaila$getBarStyle().value.name();
        Texture tex = minecraft.textureManager.loadTexture((modSettings.bTWaila$getBackgroundStyle().value).getFilePath());//textureManager.loadTexture("minecraft:gui/tooltip/battle_ui.png");
        textureManager.bindTexture(tex);
        float opacity = modSettings.bTWaila$getBackgroundOpacity().value;
        drawBoxyTexture(minX-backgroundHPadding, minY, maxX+backgroundHPadding*2-minX, maxY+backgroundVPadding-minY, opacity);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        //drawRect(minX-backgroundHPadding, minY-backgroundVPadding, maxX+backgroundHPadding, maxY+backgroundVPadding, 0xff000000);
    }

    @Override
    public boolean isVisible(Minecraft minecraft) {
        return minecraft.gameSettings.immersiveMode.drawHotbar();
    }

    @Override
    public void render(Minecraft minecraft, HudIngame HudIngame, int xScreenSize, int yScreenSize, float partialTick) {
        renderBackground(xScreenSize, yScreenSize);
        super.render(minecraft, HudIngame, xScreenSize, yScreenSize, partialTick);
    }

    @Override
    public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
        renderBackground(xScreenSize, yScreenSize);
        super.renderPreview(minecraft, gui, layout, xScreenSize, yScreenSize);
    }

    @Override
    public void renderPost(Minecraft minecraft, HudIngame HudIngame, int xScreenSize, int yScreenSize, float f) {
        HitResult hitResult = minecraft.objectMouseOver;
        drawing = hitResult != null;
        if (hitResult == null) {return;}
        addOffY(topPadding);
        if (hitResult.hitType == HitResult.HitType.TILE) {
            Block<?> block = minecraft.currentWorld.getBlock(hitResult.x, hitResult.y, hitResult.z);
            int meta = minecraft.currentWorld.getBlockMetadata(hitResult.x, hitResult.y, hitResult.z);
            ItemStack[] drops = null;
            if (block != null) {
                drops = block.getBreakResult(minecraft.currentWorld, EnumDropCause.PICK_BLOCK, hitResult.x, hitResult.y, hitResult.z, minecraft.currentWorld.getBlockMetadata(hitResult.x, hitResult.y, hitResult.z), null);
            }
            baseBlockInfo(block, meta, drops);
        } else if (hitResult.hitType == HitResult.HitType.ENTITY) {
            baseEntityInfo(hitResult.entity);
        }
    }

    @Override
    public void renderPreviewPost(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
        addOffY(topPadding);
        drawing = true;
        Block<?> block = DemoManager.getCurrentEntry().block;
        int meta = DemoManager.getCurrentEntry().meta;
        ItemStack[] drops = DemoManager.getCurrentEntry().drops;
        Entity entity = DemoManager.getCurrentEntry().entity;
        if (block != null){
            baseBlockInfo(block, meta, drops);
        } else if (entity != null) {
            baseEntityInfo(entity);
        }
    }
    protected void baseBlockInfo(Block<?> block, int blockMetadata, ItemStack[] blockDrops){
        if (!modSettings().bTWaila$getBlockTooltips().value) return;
        if (minecraft.font == null) return;

        ItemStack renderItem = new ItemStack(block, 1, blockMetadata);
        if (blockDrops != null && blockDrops.length > 0) renderItem = blockDrops[0];

        String languageKey = renderItem.getItemKey();

        String blockName = translator.translateNameKey(languageKey);
        String blockDesc = translator.translateDescKey(languageKey);
        String blockSource = "Minecraft";
        for (String modId: BTWailaClient.modIds.keySet()){
            if (languageKey.contains(modId)){
                blockSource = BTWailaClient.modIds.get(modId);
            }
        }
        String idString = block.id() + ":" + blockMetadata;
        if (modSettings().bTWaila$getShowBlockId().value){
            blockName += " " + idString;
        }

        drawStringJustified(blockName,0,getXSize(minecraft), Colors.WHITE);
        drawStringJustified(blockSource,0,getXSize(minecraft), Colors.BLUE);
        if (modSettings().bTWaila$getShowBlockDesc().value){
            drawStringJustified(blockDesc,0,getXSize(minecraft), Colors.LIGHT_GRAY);
        }
    }
    protected void baseEntityInfo(Entity entity){
        if (!modSettings().bTWaila$getEntityTooltips().value) return;
        boolean isLivingEntity = (entity instanceof Mob);
        Mob Mob = isLivingEntity ? (Mob) entity : null;

        int color = Colors.WHITE;
        if (isLivingEntity) {
            color = Colors.GREEN;
            if (entity instanceof MobMonster) {
                color = Colors.RED;
            }
            else if (entity instanceof Player) {
                color = Mob.chatColor;
            }
        }
        drawStringJustified(AdvancedInfoComponent.getEntityName(entity), 0, getXSize(minecraft), color);
    }

}
