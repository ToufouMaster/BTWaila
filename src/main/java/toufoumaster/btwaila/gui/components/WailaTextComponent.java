package toufoumaster.btwaila.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScreenHudDesigner;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.client.gui.hud.HudIngame;
import net.minecraft.client.gui.hud.component.HudComponentMovable;
import net.minecraft.client.gui.hud.component.layout.Layout;
import net.minecraft.client.gui.modelviewer.categories.entries.entity.EntityEntryArmoredZombie;
import net.minecraft.client.render.Lighting;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.entity.EntityRendererItem;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.EntityPainting;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.animal.*;
import net.minecraft.core.entity.monster.*;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.vehicle.EntityBoat;
import net.minecraft.core.entity.vehicle.EntityMinecart;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.player.inventory.container.Container;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.server.entity.player.PlayerServer;
import org.lwjgl.opengl.GL11;
import toufoumaster.btwaila.BTWailaClient;
import toufoumaster.btwaila.mixin.interfaces.IOptions;
import toufoumaster.btwaila.util.ColorOptions;
import toufoumaster.btwaila.util.Colors;
import toufoumaster.btwaila.util.ProgressBarOptions;
import toufoumaster.btwaila.util.TextureOptions;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;

import static toufoumaster.btwaila.BTWaila.translator;

public abstract class WailaTextComponent extends HudComponentMovable {
    public static EntityRendererItem itemRender = null;
    public static final HashMap<Class<? extends Entity>, ItemStack> entityIconMap = new HashMap<>();

    public static void init(){
        addEntityIcon(Player.class, Items.FLAG);
        addEntityIcon(PlayerLocal.class, Items.FLAG);
        //addEntityIcon(PlayerServer.class, Items.FLAG);
        addEntityIcon(MobZombieArmored.class, Items.CHAINLINK);
        addEntityIcon(MobCreeper.class, Items.SULPHUR);
        addEntityIcon(MobGhast.class, Items.AMMO_FIREBALL);
        addEntityIcon(MobZombiePig.class, Items.FOOD_PORKCHOP_COOKED);
        addEntityIcon(MobPig.class, Items.FOOD_PORKCHOP_RAW);
        addEntityIcon(MobScorpion.class, Items.STRING);
        addEntityIcon(MobSpider.class, Items.STRING);
        addEntityIcon(MobSkeleton.class, Items.BONE);
        addEntityIcon(MobSlime.class, Items.SLIMEBALL);
        addEntityIcon(MobSnowman.class, Items.AMMO_SNOWBALL);
        addEntityIcon(MobZombie.class, Items.CLOTH);
        addEntityIcon(MobChicken.class, Items.FEATHER_CHICKEN);
        addEntityIcon(MobCow.class, Items.LEATHER);
        addEntityIcon(EntityPainting.class, Items.PAINTING);
        addEntityIcon(MobSheep.class, Blocks.WOOL);
        addEntityIcon(MobSquid.class, Items.DYE);
        addEntityIcon(MobWolf.class, Items.BONE);
        addEntityIcon(EntityMinecart.class, Items.MINECART);
        addEntityIcon(EntityBoat.class, Items.BOAT);
    }

    public static void addEntityIcon(Class<? extends Entity> entityClass, IItemConvertible displayItem){
        addEntityIcon(entityClass, displayItem.getDefaultStack());
    }
    public static void addEntityIcon(Class<? extends Entity> entityClass, ItemStack displayStack){
        entityIconMap.put(entityClass, displayStack);
    }
    public static final int componentTextWidth = 152;
    public int lineHeight = 9;
    protected final int padding = 8;
    protected int offY = padding;
    protected int posX = 0;
    protected float scale = 1f;
    public Minecraft minecraft = Minecraft.getMinecraft();
    public IOptions modSettings(){
        return BTWailaClient.modSettings;
    }
    protected Gui activeGUI;
    protected int xScreenSize;
    protected int yScreenSize;
    protected int ySize;
    private int startY = 0;
    public WailaTextComponent(String key, int ySize, Layout layout) {
        super(key, componentTextWidth, ySize, layout);
        this.ySize = ySize;
    }
    @Override
    public int getXSize(Minecraft mc) {
        return componentTextWidth;
    }
    public void render(Minecraft minecraft, HudIngame HudIngame, int xScreenSize, int yScreenSize, float partialTick){
        this.minecraft = minecraft;
        this.activeGUI = HudIngame;
        this.xScreenSize = xScreenSize;
        this.yScreenSize = yScreenSize;
        if (minecraft.currentScreen instanceof ScreenHudDesigner) return; // Fixes weird placement issues while editing HUD in world
        startY = offY = generateOriginalPosY();
        posX = generateOriginalPosX();
        renderPost(minecraft, HudIngame, xScreenSize, yScreenSize, partialTick);
    }

    public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize){
        this.minecraft = minecraft;
        this.activeGUI = gui;
        this.xScreenSize = xScreenSize;
        this.yScreenSize = yScreenSize;
        startY = offY = generateOriginalPosY();
        posX = generateOriginalPosX();
        renderPreviewPost(minecraft, gui, layout, xScreenSize, yScreenSize);
    }
    public int getLineHeight(){
        return lineHeight;
    }
    public int height(){
        return offY - startY;
    }
    public abstract void renderPost(Minecraft minecraft, HudIngame HudIngame, int i, int j, float f);

    public abstract void renderPreviewPost(Minecraft minecraft, Gui gui, Layout layout, int i, int j);
    public Minecraft getGame() {
        return minecraft;
    }

    public  void addOffY(int offset) {
        offY += offset;
    }
    public  void subOffY(int offset) {
        offY -= offset;
    }

    public  int getOffY() { return offY; }
    public  int getPosX() { return posX; }

    public  void setOffY(int y) { offY = y; }
    public  void setPosX(int x) { posX = x; }

    protected void setScale(float scale) { this.scale = scale; }
    public  float getScale() { return scale; }

    public int generateOriginalPosY() {
        return getLayout().getComponentY(minecraft, this, yScreenSize);
    }

    public int generateOriginalPosX() {
        return getLayout().getComponentX(minecraft, this, xScreenSize);
    }

    public void drawStringWithShadow(String text, int offX, int color) {
        int width = minecraft.font.getStringWidth(text);
        minecraft.font.drawStringWithShadow(text, posX+offX + getStartingX(width), offY, color);
        addOffY(getLineHeight());
    }
    public int getStartingX(int width){
        int diff = getXSize(minecraft) - width;
        int startX;
        switch (modSettings().bTWaila$getTooltipFormatting().value){
            case LEFT:
                startX = 0;
                break;
            case CENTERED:
                startX = diff/2;
                break;
            case RIGHT:
                startX = diff;
                break;
            default:
                throw new IllegalArgumentException("Unexpected enum: " + modSettings().bTWaila$getTooltipFormatting().value);
        }
        return startX;
    }

    public void drawStringWithShadow(String text, int offX) {
        drawStringWithShadow(text, offX, Colors.WHITE);
    }
    public void drawStringJustified(String text, int offX, int maxWidth, int color){
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        StringBuilder prevline;
        int wordCount = 0;
        for (String word: words) {
            prevline = new StringBuilder(line.toString());
            line.append(word).append(" ");
            wordCount++;
            if (minecraft.font.getStringWidth(line.toString().trim()) > maxWidth){
                if (wordCount <= 1){
                    drawStringWithShadow(line.toString(), offX, color);
                    line = new StringBuilder(word).append(" ");
                    wordCount = 0;
                    continue;
                }
                drawStringWithShadow(prevline.toString(), offX, color);
                line = new StringBuilder(word).append(" ");
                wordCount = 0;
            }
        }
        String remainder = line.toString();
        if (!remainder.isEmpty()){
            drawStringWithShadow(remainder, offX, color);
        }
    }
    public void drawStringCentered(String text){
        drawStringCentered(text, Colors.WHITE);
    }
    public void drawStringCentered(String text, int color){
        minecraft.font.drawCenteredString(text, posX + (componentTextWidth/2), offY, color);
        addOffY(getLineHeight());
    }

    @Deprecated
    public void drawTextureRectRepeat(int x, int y, int w, int h, int texX, int texY, int tileWidth, int color) {
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        GL11.glColor4f(r, g, b, 1f);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(0, minecraft.resolution.getWidthScreenCoords() - h * minecraft.resolution.getScale(), w * minecraft.resolution.getScale(), minecraft.resolution.getHeightScreenCoords());

        for (int i = x; i < w; i += tileWidth) {
            for (int j = y; j < h; j += tileWidth) {
                //noinspection SuspiciousNameCombination
                activeGUI.drawTexturedModalRect(i, j, texX, texY, tileWidth, tileWidth);
            }
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
    public void drawIcon(double x, double y, double w, double h, IconCoordinate coordinate, int color){
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        GL11.glColor4f(r, g, b, 1f);

        coordinate.parentAtlas.bind();

        double minU = coordinate.getIconUMin();
        double maxU = coordinate.getIconUMax();
        double minV = coordinate.getIconVMin();
        double maxV = coordinate.getIconVMax();

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(
            0,
            MathHelper.floor(minecraft.resolution.getHeightScreenCoords() - h * minecraft.resolution.getScale()),
            MathHelper.floor(w * minecraft.resolution.getScale()),
            minecraft.resolution.getHeightScreenCoords());

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        for (double i = x; i < w; i += coordinate.width) {
            for (double j = y; j < h; j += coordinate.height) {
                tessellator.addVertexWithUV(i + 0,                  j + coordinate.height,  activeGUI.zLevel, minU, maxV);
                tessellator.addVertexWithUV(i + coordinate.width,   j + coordinate.height,  activeGUI.zLevel, maxU, maxV);
                tessellator.addVertexWithUV(i + coordinate.width,   j + 0,                  activeGUI.zLevel, maxU, minV);
                tessellator.addVertexWithUV(i + 0,                  j + 0,                  activeGUI.zLevel, minU, minV);
            }
        }
        tessellator.draw();

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    private String generateTemplateString(String text, int max, boolean values, boolean percentage) {
        String template = text;
        if (values) {
            template += translator.translateKey("btwaila.util.template.values").replace("{max}", String.valueOf(max)).replace("{current}", String.valueOf(max));
            if (percentage) template += " ";
        }
        if (percentage) {
            template += translator.translateKey("btwaila.util.template.percentage").replace("{value}", String.format("%.1f",100f));
        }
        return template;
    }

    private String generateProgressBarString(String text, int value, int max, boolean values , boolean percentage) {
        float ratio = (float) value / (float) max;
        String template = text;
        if (values) {
            template += translator.translateKey("btwaila.util.template.values").replace("{max}", String.valueOf(max)).replace("{current}", String.valueOf(value));
            if (percentage) template += " ";
        }
        if (percentage) {
            template += translator.translateKey("btwaila.util.template.percentage").replace("{value}", String.format("%.1f",ratio * 100f));
        }
        return template;
    }


    public void drawTexturedModalRect(double x, double y, double width, double height, float percent) {
        float z = 0.0f;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((x + 0),     (y + height), z, 0,       1);
        tessellator.addVertexWithUV((x + width), (y + height), z, percent, 1);
        tessellator.addVertexWithUV((x + width), (y + 0),      z, percent, 0);
        tessellator.addVertexWithUV((x + 0),     (y + 0),      z, 0,       0);
        tessellator.draw();
    }

    public void drawProgressBar(int value, int max, int boxWidth, ColorOptions bgOptions, ColorOptions fgOptions, int offX) {
        float ratio = Math.min((float) value / (float) max, 1.f);
        final int sizeY = 16;
        float progress = (boxWidth*ratio);

        GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 1);
        TextureManager textureManager = minecraft.textureManager;
        String style = modSettings().bTWaila$getBarStyle().value.name();
        textureManager.bindTexture(textureManager.loadTexture("/assets/btwaila/gui/progressBg_" + style + ".png"));
        drawTexturedModalRect(posX + offX, offY, boxWidth, sizeY, 1.f);
        if (progress > 0) {
            textureManager.bindTexture(textureManager.loadTexture("/assets/btwaila/gui/progressOverlay_" + style + ".png"));
            drawTexturedModalRect(posX + offX, offY, progress, sizeY, ratio);
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        addOffY(sizeY);
    }

    public void drawProgressBarTexture(int value, int max, int boxWidth, TextureOptions bgOptions, TextureOptions fgOptions, int offX) {
        float ratio = (float) value / (float) max;
        final int sizeY = 16;
        int progress = (int)Math.ceil((boxWidth)*ratio);

        IconCoordinate bgCoord = bgOptions.coordinate;
        IconCoordinate fgCoord = fgOptions.coordinate;

        this.drawRect(posX+offX, offY, posX+offX+boxWidth, offY+sizeY, 0xff000000);
        GL11.glEnable(GL11.GL_BLEND);
        drawIcon(posX+offX, offY, posX+offX+boxWidth, offY+sizeY, bgCoord, bgOptions.color);
        drawIcon(posX+offX, offY, posX+offX+progress, offY+sizeY, fgCoord, fgOptions.color);
        addOffY(sizeY);
    }

    public void drawProgressBarWithText(int value, int max, ProgressBarOptions options, int offX) {
        int stringPadding = 5;
        int stringWidth = minecraft.font.getStringWidth(generateTemplateString(options.text, max, options.values, options.percentage));
        String toDrawText = generateProgressBarString(options.text, value, max, options.values, options.percentage);
        int textWidthDif = stringWidth - minecraft.font.getStringWidth(toDrawText);
        int width = options.boxWidth;
        if (width == 0) {
            width = stringWidth + stringPadding * 2;
        } else {
            stringPadding = (width-stringWidth)/2;
        }

        drawProgressBar(value, max, width, options.bgOptions, options.fgOptions, offX);
        subOffY(12);
        drawStringCentered(toDrawText);
        addOffY(4);
    }

    public void drawProgressBarTextureWithText(int value, int max, ProgressBarOptions options, int offX) {
        int stringPadding = 5;
        int stringWidth = minecraft.font.getStringWidth(generateTemplateString(options.text, max, options.values, options.percentage));
        String toDrawText = generateProgressBarString(options.text, value, max, options.values, options.percentage);
        int textWidthDif = stringWidth - minecraft.font.getStringWidth(toDrawText);
        int width = options.boxWidth;
        if (width == 0) {
            width = stringWidth + stringPadding * 2;
        } else {
            stringPadding = (width-stringWidth)/2;
        }

        drawProgressBarTexture(value, max, width, options.bgOptions, options.fgOptions, offX);
        subOffY(12);
        drawStringCentered(toDrawText);
        addOffY(4);
    }

    public void drawInfiniteStackSizeInventory(Container inventory, int offX) {
        HashMap<Integer, ItemStack> itemList = new HashMap<>();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if(itemStack != null){
                int itemId = itemStack.getItem().id;
                int stackSize = itemStack.stackSize;
                if (itemList.containsKey(itemId)) {
                    ItemStack stack = itemList.get(itemId);
                    stack.stackSize = stack.stackSize + stackSize;
                    itemList.put(itemId, stack);
                } else {
                    itemList.put(itemId, new ItemStack(itemStack.itemID, itemStack.stackSize, itemStack.getMetadata()));
                }
            }
        }

        drawItemList(itemList.values().toArray(new ItemStack[0]), offX);

    }

    public void drawItemList(ItemStack[] itemList, int offX) {
        Lighting.enableInventoryLight();
        GL11.glEnable(32826);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        Tessellator t = Tessellator.instance;

        int itemX = 0;
        int itemY = 0;
        for (ItemStack itemStack : itemList) {
            if (itemStack != null) {
                ItemModel model = ItemModelDispatcher.getInstance().getDispatch(itemStack);
                model.renderItemIntoGui(t, minecraft.font, minecraft.textureManager, itemStack, posX + offX + itemX * 16, offY + itemY * 16, 1.0F);
                model.renderItemOverlayIntoGUI(t, minecraft.font, minecraft.textureManager, itemStack, posX + offX + itemX * 16, offY + itemY * 16, 1.0F);
                itemX++;
                if (itemX >= 9) {
                    itemX = 0;
                    itemY += 1;
                }
            }
        }
        addOffY(8*(1+itemY));
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        Lighting.disable();
    }

    public void drawInventory(Container inventory, int offX) {
        Lighting.enableInventoryLight();
        GL11.glEnable(32826);

        int invWidth = getXSize(minecraft);
        int invHeight = getYSize(minecraft) - (offY - generateOriginalPosY());
        int invArea = invHeight * invWidth;
        final int maxLength = 16;
        float iconLength;
        if (inventory.getContainerSize() < 1){
            iconLength = maxLength;
        } else {
            iconLength = (float) Math.sqrt(((double) invArea) /inventory.getContainerSize());
        }
        iconLength = Math.min(maxLength, iconLength); // Min is correct, the intent is to cap the size at 16
        iconLength = Math.max(iconLength, 1);
        int itemsWide = (int) Math.floor(invWidth/iconLength);
        double scale = iconLength/16d;

        GL11.glScaled(scale, scale, scale);

        Tessellator t = Tessellator.instance;

        int itemX = 0;
        int itemY = 0;
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack != null) {
                int renderX = (int) ((posX + offX + itemX * iconLength) /scale);
                int renderY = (int) ((offY + itemY * iconLength)/scale);
                ItemModel model = ItemModelDispatcher.getInstance().getDispatch(itemStack);
                model.renderItemIntoGui(t, minecraft.font, minecraft.textureManager, itemStack, renderX, renderY, 1.0F);
                model.renderItemOverlayIntoGUI(t, minecraft.font, minecraft.textureManager, itemStack, renderX, renderY, 1.0F);
                itemX++;
                if (itemX >= itemsWide) {
                    itemX = 0;
                    itemY += 1;
                }
            }
        }

        GL11.glScaled(1/scale, 1/scale, 1/scale);

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        addOffY(8*(1+itemY));

        Lighting.disable();
    }

    protected void drawEntityHealth(Mob entity) {
        Random rand = new Random();

        Lighting.disable();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.textureManager.bindTexture(minecraft.textureManager.loadTexture("/gui/icons.png"));

        boolean heartsFlash;
        heartsFlash = entity.heartsFlashTime / 3 % 2 == 1;
        if (entity.heartsFlashTime < 10) {
            heartsFlash = false;
        }



        int health = entity.getHealth();
        int prevHealth = entity.prevHealth;
        int hearts = (int) Math.ceil(entity.getHealth()/2f);
        int heartsPerRow = componentTextWidth/8;
        int rows = (int) Math.ceil(((float)hearts)/heartsPerRow);
//        int additionalHearts = 0;

        if (rows > modSettings().bTWaila$getSmallEntityHealthBar().value) {
            // rip :(
//            rows = modSettings().bTWaila$getSmallEntityHealthBar().value;
//            additionalHearts = hearts - rows * heartsPerRow;
//            hearts = rows * heartsPerRow;

            int x = posX + getStartingX(0);
            int y = offY;
            activeGUI.drawTexturedModalRect(x, y, 16, 0, 9, 9);
            if (heartsFlash) {
                activeGUI.drawTexturedModalRect(x, y, 25, 0, 9, 9);
                activeGUI.drawTexturedModalRect(x, y, 70, 0, 9, 9);
            }
            activeGUI.drawTexturedModalRect(x, y, 52, 0, 9, 9);
            DecimalFormat df = new DecimalFormat("#.#");
            drawStringWithShadow(String.format("x %s", df.format(health/2f)), 10, Colors.WHITE);
            addOffY(getLineHeight());
            return;
        }

        int trueHeartNum = 0;
        for (int row = 0; row < rows; row++){
            int heartsToDraw = heartsPerRow;
            if (row == rows - 1){
                heartsToDraw = hearts - (row * heartsPerRow);
            }
            for (int heart = 0; heart < heartsToDraw; heart++) {
                int width = heartsToDraw * 8;
                int y = offY;

                int heartOffset = 0;
                if (heartsFlash) {
                    heartOffset = 1;
                }

                int x = posX + getStartingX(width) + heart * 8;
                if (health <= 4) {
                    y += rand.nextInt(2);
                }

                activeGUI.drawTexturedModalRect(x, y, 16 + heartOffset * 9, 0, 9, 9);
                if (heartsFlash) {
                    if (trueHeartNum * 2 + 1 < prevHealth) {
                        activeGUI.drawTexturedModalRect(x, y, 70, 0, 9, 9);
                    }

                    if (trueHeartNum * 2 + 1 == prevHealth) {
                        activeGUI.drawTexturedModalRect(x, y, 79, 0, 9, 9);
                    }
                }

                if (trueHeartNum * 2 + 1 < health) {
                    activeGUI.drawTexturedModalRect(x, y, 52, 0, 9, 9);
                }

                if (trueHeartNum * 2 + 1 == health) {
                    activeGUI.drawTexturedModalRect(x, y, 61, 0, 9, 9);
                }
                trueHeartNum++;
            }
            addOffY(getLineHeight());
        }
        // Rip :(
//        if (additionalHearts > 0){
//            int x = posX + getStartingX(0);
//            int y = offY;
//            activeGUI.drawTexturedModalRect(x, y, 16, 0, 9, 9);
//            if (heartsFlash) {
//                activeGUI.drawTexturedModalRect(x, y, 25, 0, 9, 9);
//                activeGUI.drawTexturedModalRect(x, y, 70, 0, 9, 9);
//            }
//            activeGUI.drawTexturedModalRect(x, y, 52, 0, 9, 9);
//            drawStringWithShadow("x " + additionalHearts, 10, Colors.WHITE);
//            addOffY(getLineHeight());
//        }
    }

    protected void drawRect(int minX, int minY, int maxX, int maxY, int argb) {
        int temp;
        if (minX < maxX) {
            temp = minX;
            minX = maxX;
            maxX = temp;
        }
        if (minY < maxY) {
            temp = minY;
            minY = maxY;
            maxY = temp;
        }
        float a = (float)(argb >> 24 & 0xFF) / 255.0f;
        float r = (float)(argb >> 16 & 0xFF) / 255.0f;
        float g = (float)(argb >> 8 & 0xFF) / 255.0f;
        float b = (float)(argb & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(r, g, b, a);
        tessellator.startDrawingQuads();
        tessellator.addVertex(minX, maxY, 0.0);
        tessellator.addVertex(maxX, maxY, 0.0);
        tessellator.addVertex(maxX, minY, 0.0);
        tessellator.addVertex(minX, minY, 0.0);
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    public EntityRendererItem getItemRenderer(){
        if (itemRender == null) itemRender = new EntityRendererItem();
        return itemRender;
    }

    public static String getEntityName(Entity entity){
        if (entity == null){
            return translator.translateKey("btwaila.tooltip.general.entity.null");
        }
        String entityName = entity instanceof Mob ? ((Mob)entity).getDisplayName() : null;

        if (entityName == null || entityName.equalsIgnoreCase("§0")) {
            MobInfoRegistry.MobInfo info = MobInfoRegistry.getMobInfo(entity.getClass());
            if (info != null){
                entityName = translator.translateKey(info.getNameTranslationKey());
            } else {
                entityName = EntityDispatcher.classToIdMap.get(entity.getClass()).toString();
            }
        }

        if (entityName == null){
            entityName = entity.getClass().getSimpleName();
        }
        return entityName;
    }

    public String getNameFromEntity(Entity entity){
        return getEntityName(entity);
    }

    @Nullable
    public static String getEntityDesc(Entity entity){
        if (entity == null){
            return null;
        }
        MobInfoRegistry.MobInfo info = MobInfoRegistry.getMobInfo(entity.getClass());
        if (info == null){
            return null;
        }
        return translator.translateKey(info.getDescriptionTranslationKey());
    }

    @Nullable
    public String getDescFromEntity(Entity entity){
        return getEntityDesc(entity);
    }
}
