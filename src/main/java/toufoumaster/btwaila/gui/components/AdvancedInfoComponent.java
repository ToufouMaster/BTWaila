package toufoumaster.btwaila.gui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityClientPlayerMP;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.hud.ComponentAnchor;
import net.minecraft.client.gui.hud.Layout;
import net.minecraft.client.gui.hud.MovableHudComponent;
import net.minecraft.client.render.Lighting;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextureFX;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.core.Global;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.EntityPainting;
import net.minecraft.core.entity.animal.EntityChicken;
import net.minecraft.core.entity.animal.EntityCow;
import net.minecraft.core.entity.animal.EntityPig;
import net.minecraft.core.entity.animal.EntitySheep;
import net.minecraft.core.entity.animal.EntitySquid;
import net.minecraft.core.entity.animal.EntityWolf;
import net.minecraft.core.entity.monster.EntityArmoredZombie;
import net.minecraft.core.entity.monster.EntityCreeper;
import net.minecraft.core.entity.monster.EntityGhast;
import net.minecraft.core.entity.monster.EntityPigZombie;
import net.minecraft.core.entity.monster.EntityScorpion;
import net.minecraft.core.entity.monster.EntitySkeleton;
import net.minecraft.core.entity.monster.EntitySlime;
import net.minecraft.core.entity.monster.EntitySnowman;
import net.minecraft.core.entity.monster.EntitySpider;
import net.minecraft.core.entity.monster.EntityZombie;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.vehicle.EntityBoat;
import net.minecraft.core.entity.vehicle.EntityMinecart;
import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import toufoumaster.btwaila.BTWaila;
import toufoumaster.btwaila.tooltips.interfaces.IBTWailaCustomBlockTooltip;
import toufoumaster.btwaila.tooltips.interfaces.IBTWailaCustomEntityTooltip;
import toufoumaster.btwaila.mixin.interfaces.IOptions;
import toufoumaster.btwaila.tooltips.TooltipGroup;
import toufoumaster.btwaila.tooltips.TooltipRegistry;
import toufoumaster.btwaila.demo.DemoEntry;
import toufoumaster.btwaila.network.packet.PacketRequestEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestTileEntityData;
import toufoumaster.btwaila.util.ColorOptions;
import toufoumaster.btwaila.util.Colors;
import toufoumaster.btwaila.util.ProgressBarOptions;
import toufoumaster.btwaila.util.TextureOptions;

import java.util.HashMap;
import java.util.Random;

public class AdvancedInfoComponent extends MovableHudComponent {

    private static boolean keyPressed = false;
    private  final int padding = 8;
    private  int offY = padding;
    private  int posX = 0;
    private  float scale = 1f;

    public static final ItemEntityRenderer itemRender = new ItemEntityRenderer();
    public  Minecraft minecraft = Minecraft.getMinecraft(Minecraft.class);
    private Gui activeGUI;
    private int xScreenSize;
    private int yScreenSize;
    public static final HashMap<Class<? extends Entity>, ItemStack> entityIconMap = new HashMap<>();
    static {
        addEntityIcon(EntityPlayer.class, Item.flag);
        addEntityIcon(EntityPlayerSP.class, Item.flag);
        addEntityIcon(EntityPlayerMP.class, Item.flag);
        addEntityIcon(EntityArmoredZombie.class, Item.chainlink);
        addEntityIcon(EntityCreeper.class, Item.sulphur);
        addEntityIcon(EntityGhast.class, Item.ammoFireball);
        addEntityIcon(EntityPigZombie.class, Item.foodPorkchopCooked);
        addEntityIcon(EntityPig.class, Item.foodPorkchopRaw);
        addEntityIcon(EntityScorpion.class, Item.string);
        addEntityIcon(EntitySpider.class, Item.string);
        addEntityIcon(EntitySkeleton.class, Item.bone);
        addEntityIcon(EntitySlime.class, Item.slimeball);
        addEntityIcon(EntitySnowman.class, Item.ammoSnowball);
        addEntityIcon(EntityZombie.class, Item.cloth);
        addEntityIcon(EntityChicken.class, Item.featherChicken);
        addEntityIcon(EntityCow.class, Item.leather);
        addEntityIcon(EntityPainting.class, Item.painting);
        addEntityIcon(EntitySheep.class, Block.wool);
        addEntityIcon(EntitySquid.class, Item.dye);
        addEntityIcon(EntityWolf.class, Item.bone);
        addEntityIcon(EntityMinecart.class, Item.minecart);
        addEntityIcon(EntityBoat.class, Item.boat);
    }
    public static void addEntityIcon(Class<? extends Entity> entityClass, IItemConvertible displayItem){
        addEntityIcon(entityClass, displayItem.getDefaultStack());
    }
    public static void addEntityIcon(Class<? extends Entity> entityClass, ItemStack displayStack){
        entityIconMap.put(entityClass, displayStack);
    }
    public AdvancedInfoComponent(String key, Layout layout) {
        super(key, 16 * 9, 100 - 32, layout);
    }
    @Override
    public int getAnchorY(ComponentAnchor anchor) {
        return (int)(anchor.yPosition * getYSize(Minecraft.getMinecraft(this)));
    }

    @Override
    public boolean isVisible(Minecraft minecraft) {
        return minecraft.gameSettings.immersiveMode.drawHotbar();
    }

    @Override
    public void render(Minecraft minecraft, GuiIngame guiIngame, int xScreenSize, int yScreenSize, float partialTick) {
        this.minecraft = minecraft;
        this.activeGUI = guiIngame;
        this.xScreenSize = xScreenSize;
        this.yScreenSize = yScreenSize;
        HitResult hitResult = minecraft.objectMouseOver;
        if (hitResult == null) {return;}
        if (hitResult.hitType == HitResult.HitType.TILE) {
            TileEntity tileEntity = minecraft.theWorld.getBlockTileEntity(hitResult.x, hitResult.y, hitResult.z);
            renderBlockOverlay(tileEntity);
        } else if (hitResult.hitType == HitResult.HitType.ENTITY) {
            renderEntityOverlay(hitResult.entity);
        }
    }
    @Override
    public void renderPreview(Minecraft minecraft, Gui gui, Layout layout, int xScreenSize, int yScreenSize) {
        this.minecraft = minecraft;
        this.activeGUI = gui;
        this.xScreenSize = xScreenSize;
        this.yScreenSize = yScreenSize;
        if (Keyboard.isKeyDown(Keyboard.KEY_F9)){
            if (!keyPressed){
                DemoEntry.demoOffset += 1;
                keyPressed = true;
            }
        } else {
            keyPressed = false;
        }
        TileEntity demoEntity = DemoEntry.getCurrentEntry().tileEntity;
        Entity demoAnimal = DemoEntry.getCurrentEntry().entity;
        if (demoEntity != null){
            renderBlockOverlay(demoEntity);
        } else if (demoAnimal != null) {
            renderEntityOverlay(demoAnimal);
        }
    }
    private void renderBlockOverlay(TileEntity tileEntity){
        offY = generateOriginalPosY();
        posX = generateOriginalPosX();
        IOptions modSettings = (IOptions)minecraft.gameSettings;
        setScale(modSettings.getScaleTooltips().value+0.5f);

        if (!modSettings.getBlockTooltips().value) return;
        if (minecraft.fontRenderer != null) {
            if (modSettings.getBlockAdvancedTooltips().value) {
                drawFunctionalBlocksData(tileEntity);
            }
        }
    }
    private void renderEntityOverlay(Entity entity){
        offY = generateOriginalPosY();
        posX = generateOriginalPosX();
        IOptions gameSettings = (IOptions)minecraft.gameSettings;
        setScale(gameSettings.getScaleTooltips().value+0.5f);
        if (!gameSettings.getEntityTooltips().value) return;
        boolean isLivingEntity = (entity instanceof EntityLiving);
        EntityLiving entityLiving = isLivingEntity ? (EntityLiving) entity : null;

        if (gameSettings.getEntityAdvancedTooltips().value) {
            if (minecraft.thePlayer instanceof EntityClientPlayerMP && BTWaila.canUseAdvancedTooltips) {
                EntityClientPlayerMP playerMP = (EntityClientPlayerMP) minecraft.thePlayer;
                playerMP.sendQueue.addToSendQueue(new PacketRequestEntityData(entity.id));
            }

            if (isLivingEntity) drawEntityHealth(entityLiving);
            for (TooltipGroup e : TooltipRegistry.tooltipMap) {
                if (e.getInterfaceClass().isInstance(entity) && e.isInList(entity.getClass()) && e.getCustomTooltip() instanceof IBTWailaCustomEntityTooltip) {
                    IBTWailaCustomEntityTooltip tooltip = (IBTWailaCustomEntityTooltip) e.getCustomTooltip();
                    tooltip.drawAdvancedTooltip(entity, this);
                }
            }
        }
    }
    private void drawFunctionalBlocksData(TileEntity tileEntity) {
        if (tileEntity != null) {
            boolean askTileEntity = !(BTWaila.excludeContinuousTileEntityData.get(tileEntity.getClass()) != null ? BTWaila.excludeContinuousTileEntityData.get(tileEntity.getClass()) : false);
            if (minecraft.thePlayer instanceof EntityClientPlayerMP && BTWaila.canUseAdvancedTooltips && askTileEntity) {
                EntityClientPlayerMP playerMP = (EntityClientPlayerMP) minecraft.thePlayer;
                playerMP.sendQueue.addToSendQueue(new PacketRequestTileEntityData(tileEntity.x, tileEntity.y, tileEntity.z));
            }
            for (TooltipGroup e : TooltipRegistry.tooltipMap) {
                if (e.getInterfaceClass().isInstance(tileEntity) && e.isInList(tileEntity.getClass()) && e.getCustomTooltip() instanceof IBTWailaCustomBlockTooltip) {
                    IBTWailaCustomBlockTooltip tooltip = (IBTWailaCustomBlockTooltip) e.getCustomTooltip();
                    tooltip.drawAdvancedTooltip(tileEntity, this);
                }
            }
        }
    }
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

    private  void setScale(float scale) { this.scale = scale; }
    public  float getScale() { return scale; }

    public int generateOriginalPosY() {
        return getLayout().getComponentY(minecraft, this, yScreenSize);
    }

    public int generateOriginalPosX() {
        return getLayout().getComponentX(minecraft, this, xScreenSize);
    }

    public void drawStringWithShadow(String text, int offX, int color) {
        minecraft.fontRenderer.drawStringWithShadow(text, posX+offX, offY, color);
        addOffY(8);
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
            if (minecraft.fontRenderer.getStringWidth(line.toString().trim()) > maxWidth){
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

    public void drawTextureRectRepeat(int x, int y, int w, int h, int texX, int texY, int tileWidth, int color) {
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        GL11.glColor4f(r, g, b, 1f);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(0, minecraft.resolution.height - h * minecraft.resolution.scale, w * minecraft.resolution.scale, minecraft.resolution.height);

        for (int i = x; i < w; i += tileWidth) {
            for (int j = y; j < h; j += tileWidth) {
                //noinspection SuspiciousNameCombination
                itemRender.renderTexturedQuad(i, j, texX, texY, tileWidth, tileWidth);
            }
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    private String generateTemplateString(String text, int max, boolean values, boolean percentage) {
        String template = text;
        if (values) {
            template += max + "/" + max;
            if (percentage) template += " ";
        }
        if (percentage) {
            template += "("+String.format("%.1f",100f)+"%)";
        }
        return template;
    }

    private String generateProgressBarString(String text, int value, int max, boolean values , boolean percentage) {
        float ratio = (float) value / (float) max;
        String template = text;
        if (values) {
            template += value + "/" + max;
            if (percentage) template += " ";
        }
        if (percentage) {
            template += "("+String.format("%.1f",ratio*100)+"%)";
        }
        return template;
    }

    public void drawProgressBar(int value, int max, int boxWidth, ColorOptions bgOptions, ColorOptions fgOptions, int offX) {
        float ratio = (float) value / (float) max;
        final int sizeY = 16;
        final int offset = 2;
        int progress = (int)((boxWidth)*ratio);

        this.drawRect(posX+offX, offY, posX+offX+boxWidth, offY+sizeY, 0xff000000);
        this.drawRect(posX+offX+offset, offY+offset, posX+offX+boxWidth-offset, offY+sizeY-offset, 0xff000000+bgOptions.color);
        this.drawRect(posX+offX+offset, offY+offset, posX+offX+offset+progress, offY+sizeY-offset, 0xff000000+fgOptions.color);
        addOffY(sizeY);
    }

    public void drawProgressBarTexture(int value, int max, int boxWidth, TextureOptions bgOptions, TextureOptions fgOptions, int offX) {
        float ratio = (float) value / (float) max;
        final int sizeY = 16;
        int progress = (int)((boxWidth)*ratio);

        RenderEngine renderEngine = minecraft.renderEngine;
        renderEngine.bindTexture(renderEngine.getTexture("/terrain.png"));
        int tileWidth = TextureFX.tileWidthTerrain;
        int bgTexId = Block.getBlock(bgOptions.blockId).getBlockTextureFromSideAndMetadata(bgOptions.side, bgOptions.metadata);
        int bgTexX = bgTexId % Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth;
        int bgTexY = bgTexId / Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth;
        int fgTexId = Block.getBlock(fgOptions.blockId).getBlockTextureFromSideAndMetadata(fgOptions.side, fgOptions.metadata);
        int fgTexX = fgTexId % Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth;
        int fgTexY = fgTexId / Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth;

        this.drawRect(posX+offX, offY, posX+offX+boxWidth, offY+sizeY, 0xff000000);
        drawTextureRectRepeat(posX+offX, offY, posX+offX+boxWidth, offY+sizeY, bgTexX, bgTexY, tileWidth, bgOptions.color);
        drawTextureRectRepeat(posX+offX, offY, posX+offX+progress, offY+sizeY, fgTexX, fgTexY, tileWidth, fgOptions.color);
        addOffY(sizeY);
    }

    public void drawProgressBarWithText(int value, int max, ProgressBarOptions options, int offX) {
        int stringPadding = 5;
        int stringWidth = minecraft.fontRenderer.getStringWidth(generateTemplateString(options.text, max, options.values, options.percentage));
        String toDrawText = generateProgressBarString(options.text, value, max, options.values, options.percentage);
        int textWidthDif = stringWidth - minecraft.fontRenderer.getStringWidth(toDrawText);
        int width = options.boxWidth;
        if (width == 0) {
            width = stringWidth + stringPadding * 2;
        } else {
            stringPadding = (width-stringWidth)/2;
        }

        drawProgressBar(value, max, width, options.bgOptions, options.fgOptions, offX);
        subOffY(12);
        drawStringWithShadow(toDrawText, offX+stringPadding + textWidthDif/2);
        addOffY(4);
    }

    public void drawProgressBarTextureWithText(int value, int max, ProgressBarOptions options, int offX) {
        int stringPadding = 5;
        int stringWidth = minecraft.fontRenderer.getStringWidth(generateTemplateString(options.text, max, options.values, options.percentage));
        String toDrawText = generateProgressBarString(options.text, value, max, options.values, options.percentage);
        int textWidthDif = stringWidth - minecraft.fontRenderer.getStringWidth(toDrawText);
        int width = options.boxWidth;
        if (width == 0) {
            width = stringWidth + stringPadding * 2;
        } else {
            stringPadding = (width-stringWidth)/2;
        }

        drawProgressBarTexture(value, max, width, (TextureOptions) options.bgOptions, (TextureOptions) options.fgOptions, offX);
        subOffY(12);
        drawStringWithShadow(toDrawText, offX+stringPadding + textWidthDif/2);
        addOffY(4);
    }

    public void drawInfiniteStackSizeInventory(IInventory inventory, int offX) {
        HashMap<Integer, ItemStack> itemList = new HashMap<>();
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack itemStack = inventory.getStackInSlot(i);
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

        drawItemList(itemList.values().toArray(new ItemStack[0]), offX);

    }

    public void drawItemList(ItemStack[] itemList, int offX) {
        Lighting.enableInventoryLight();
        GL11.glEnable(32826);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        int itemX = 0;
        int itemY = 0;
        for (ItemStack itemStack : itemList) {
            if (itemStack != null) {
                itemRender.renderItemIntoGUI(minecraft.fontRenderer, minecraft.renderEngine, itemStack, posX + offX + itemX * 16, offY + itemY * 16, 1.0F);
                itemRender.renderItemOverlayIntoGUI(minecraft.fontRenderer, minecraft.renderEngine, itemStack, posX + offX + itemX * 16, offY + itemY * 16, 1.0F);
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

    public void drawInventory(IInventory inventory, int offX) {
        Lighting.enableInventoryLight();
        GL11.glEnable(32826);

        int invWidth = (9 * 16);
        int invHeight = (3 * 16);
        int invArea = invHeight * invWidth;
        int iconLength = (int) Math.sqrt(((double) invArea) /inventory.getSizeInventory());
        iconLength = Math.min(16, iconLength);
        int itemsWide = invWidth/iconLength;
        double scale = iconLength/16d;

        GL11.glScaled(scale, scale, scale);

        int itemX = 0;
        int itemY = 0;
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack itemStack = inventory.getStackInSlot(i);
            if (itemStack != null) {
                int renderX = (int) ((posX + offX + itemX * iconLength) /scale);
                int renderY = (int) ((offY + itemY * iconLength)/scale);
                itemRender.renderItemIntoGUI(minecraft.fontRenderer, minecraft.renderEngine, itemStack, renderX, renderY, 1.0F);
                itemRender.renderItemOverlayIntoGUI(minecraft.fontRenderer, minecraft.renderEngine, itemStack, renderX, renderY, 1.0F);
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
    private void drawEntityHealth(EntityLiving entity) {
        Lighting.disable();
        boolean heartsFlash;
        if (minecraft.thePlayer != null){
            heartsFlash = minecraft.thePlayer.heartsFlashTime / 3 % 2 == 1;
            if (minecraft.thePlayer.heartsFlashTime < 10) {
                heartsFlash = false;
            }
        } else {
            heartsFlash = false;
        }


        int health = entity.health;
        int prevHealth = entity.prevHealth;
        Random rand = new Random();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.renderEngine.bindTexture(minecraft.renderEngine.getTexture("/gui/icons.png"));

        for(int index = 0; index < (int)Math.ceil((float)health/2f); ++index) {
            int y = offY;
            int heartOffset = 0;
            if (heartsFlash) {
                heartOffset = 1;
            }

            int x = posX + index * 8;
            if (health <= 4) {
                y += rand.nextInt(2);
            }

            activeGUI.drawTexturedModalRect(x, y, 16 + heartOffset * 9, 0, 9, 9);
            if (heartsFlash) {
                if (index * 2 + 1 < prevHealth) {
                    activeGUI.drawTexturedModalRect(x, y, 70, 0, 9, 9);
                }

                if (index * 2 + 1 == prevHealth) {
                    activeGUI.drawTexturedModalRect(x, y, 79, 0, 9, 9);
                }
            }

            if (index * 2 + 1 < health) {
                activeGUI.drawTexturedModalRect(x, y, 52, 0, 9, 9);
            }

            if (index * 2 + 1 == health) {
                activeGUI.drawTexturedModalRect(x, y, 61, 0, 9, 9);
            }
        }
        offY += 8;
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

}
