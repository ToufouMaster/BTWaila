package toufoumaster.btwaila.gui;

import com.mojang.nbt.CompoundTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityClientPlayerMP;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.render.RenderEngine;
import net.minecraft.client.render.TextureFX;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.core.Global;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.*;
import net.minecraft.core.block.entity.*;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.EntityPainting;
import net.minecraft.core.entity.animal.*;
import net.minecraft.core.entity.monster.*;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.vehicle.EntityMinecart;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlockLamp;
import net.minecraft.core.item.block.ItemBlockPainted;
import net.minecraft.core.item.block.ItemBlockSlabPainted;
import net.minecraft.core.item.block.ItemBlockStairsPainted;
import net.minecraft.core.item.tool.*;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.world.World;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;
import toufoumaster.btwaila.*;
import toufoumaster.btwaila.mixin.IPlayerControllerMixin;
import toufoumaster.btwaila.network.packet.PacketRequestEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestTileEntityData;
import toufoumaster.btwaila.util.ColorOptions;
import toufoumaster.btwaila.util.Colors;
import toufoumaster.btwaila.util.ProgressBarOptions;
import toufoumaster.btwaila.util.TextureOptions;

import java.util.*;

public class GuiBlockOverlay extends Gui {
    private Minecraft theGame;
    private final ItemEntityRenderer itemRender;
    private HashMap<Class, Item> entityIconMap;
    private boolean entityIconMapReady = false;
    private final int paddingY = 8;
    private int offY = paddingY;
    private int posX = 0;

    public GuiBlockOverlay() {
        this.itemRender = new ItemEntityRenderer();
    }

    public void setMinecraftInstance(Minecraft minecraft) {
        this.theGame = minecraft;
    }
    public Minecraft getGame() {
        return theGame;
    }

    public void addOffY(int offset) {
        this.offY += offset;
    }
    public void subOffY(int offset) {
        this.offY -= offset;
    }

    public int getOffY() { return offY; }
    public int getPosX() { return posX; }

    public void setOffY(int y) { this.offY = y; }
    public void setPosX(int x) { this.posX = x; }

    public void drawStringWithShadow(String text, int offX, int color) {
        this.theGame.fontRenderer.drawStringWithShadow(text, posX+32+offX, offY, color);
        addOffY(8);
    }

    public void drawStringWithShadow(String text, int offX) {
        drawStringWithShadow(text, offX, Colors.WHITE);
    }

    public void drawTextureRectRepeat(int x, int y, int w, int h, int texX, int texY, int tileWidth, int color) {
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        GL11.glColor4f(r, g, b, 1f);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(0, this.theGame.resolution.height - h * this.theGame.resolution.scale, w * this.theGame.resolution.scale, this.theGame.resolution.height);

        for (int i = x; i < w; i += tileWidth) {
            for (int j = y; j < h; j += tileWidth) {
                this.itemRender.renderTexturedQuad(i, j, texX, texY, tileWidth, tileWidth);
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
        final int offset = 2;
        final int sizeY = 16;
        int progress = (int)((boxWidth-offset*2)*ratio);

        this.drawRect(posX+offX, offY, posX+offX+boxWidth, offY+sizeY, 0xff000000);
        this.drawRect(posX+offX+offset, offY+offset, posX+offX+boxWidth-offset, offY+sizeY-offset, 0xff000000+bgOptions.color);
        this.drawRect(posX+offX+offset, offY+offset, posX+offX+offset+progress, offY+sizeY-offset, 0xff000000+fgOptions.color);
        addOffY(sizeY);
    }

    public void drawProgressBarTexture(int value, int max, int boxWidth, TextureOptions bgOptions, TextureOptions fgOptions, int offX) {
        float ratio = (float) value / (float) max;
        final int offset = 2;
        final int sizeY = 16;
        int progress = (int)((boxWidth-offset*2)*ratio);

        RenderEngine renderEngine = this.theGame.renderEngine;
        renderEngine.bindTexture(renderEngine.getTexture("/terrain.png"));
        int tileWidth = TextureFX.tileWidthTerrain;
        int bgTexId = Block.getBlock(bgOptions.blockId).getBlockTextureFromSideAndMetadata(bgOptions.side, bgOptions.metadata);
        int bgTexX = bgTexId % Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth;
        int bgTexY = bgTexId / Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth;
        int fgTexId = Block.getBlock(fgOptions.blockId).getBlockTextureFromSideAndMetadata(fgOptions.side, fgOptions.metadata);
        int fgTexX = fgTexId % Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth;
        int fgTexY = fgTexId / Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth;

        //this.drawRect(posX+offX+offset, offY+offset, posX+offX+boxWidth-offset, offY+sizeY-offset, 0xff000000+bgColor);
        this.drawRect(posX+offX, offY, posX+offX+boxWidth, offY+sizeY, 0xff000000);
        drawTextureRectRepeat(posX+offX+offset, offY+offset, posX+offX+boxWidth-offset, offY+sizeY-offset, bgTexX, bgTexY, tileWidth, bgOptions.color);
        drawTextureRectRepeat(posX+offX+offset, offY+offset, posX+offX+offset+progress, offY+sizeY-offset, fgTexX, fgTexY, tileWidth, fgOptions.color);
        addOffY(sizeY);
    }

    public void drawProgressBarWithText(int value, int max, ProgressBarOptions options, int offX) {
        int stringPadding = 5;
        int stringWidth = this.theGame.fontRenderer.getStringWidth(generateTemplateString(options.text, max, options.values, options.percentage));
        String toDrawText = generateProgressBarString(options.text, value, max, options.values, options.percentage);
        int textWidthDif = stringWidth - this.theGame.fontRenderer.getStringWidth(toDrawText);
        int width = options.boxWidth;
        if (width == 0) {
            width = stringWidth + stringPadding * 2;
        } else {
            stringPadding = (width-stringWidth)/2;
        }

        drawProgressBar(value, max, width, options.bgOptions, options.fgOptions, offX);
        subOffY(12);
        drawStringWithShadow(toDrawText, offX-32+stringPadding + textWidthDif/2);
        addOffY(4);
    }

    public void drawProgressBarTextureWithText(int value, int max, ProgressBarOptions options, int offX) {
        int stringPadding = 5;
        int stringWidth = this.theGame.fontRenderer.getStringWidth(generateTemplateString(options.text, max, options.values, options.percentage));
        String toDrawText = generateProgressBarString(options.text, value, max, options.values, options.percentage);
        int textWidthDif = stringWidth - this.theGame.fontRenderer.getStringWidth(toDrawText);
        int width = options.boxWidth;
        if (width == 0) {
            width = stringWidth + stringPadding * 2;
        } else {
            stringPadding = (width-stringWidth)/2;
        }

        drawProgressBarTexture(value, max, width, (TextureOptions) options.bgOptions, (TextureOptions) options.fgOptions, offX);
        subOffY(12);
        drawStringWithShadow(toDrawText, offX-32+stringPadding + textWidthDif/2);
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

        drawItemList((ItemStack[]) itemList.values().toArray(), offX);

    }

    public void drawItemList(ItemStack[] itemList, int offX) {
        int itemX = 0;
        int itemY = 0;
        for (int i = 0; i < itemList.length; i++) {
            ItemStack itemStack = itemList[i];
            if (itemStack != null) {
                this.itemRender.renderItemIntoGUI(this.theGame.fontRenderer, this.theGame.renderEngine, itemStack, 32+posX+offX + itemX*16, offY + itemY*16, 1.0F);
                this.itemRender.renderItemOverlayIntoGUI(this.theGame.fontRenderer, this.theGame.renderEngine, itemStack, 32+posX+offX + itemX*16, offY + itemY*16, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
                itemX++;
                if (itemX >= 9) {
                    itemX = 0;
                    itemY += 1;
                }
            }
        }
        addOffY(8*(1+itemY));
    }

    public void drawInventory(IInventory inventory, int offX) {
        int itemX = 0;
        int itemY = 0;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack itemStack = inventory.getStackInSlot(i);
            if (itemStack != null) {
                this.itemRender.renderItemIntoGUI(this.theGame.fontRenderer, this.theGame.renderEngine, itemStack, 32+posX+offX + itemX*16, offY + itemY*16, 1.0F);
                this.itemRender.renderItemOverlayIntoGUI(this.theGame.fontRenderer, this.theGame.renderEngine, itemStack, 32+posX+offX + itemX*16, offY + itemY*16, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
                itemX++;
                if (itemX >= 9) {
                    itemX = 0;
                    itemY += 1;
                }
            }
        }
        addOffY(8*(1+itemY));
    }

    private void init() {
        if (entityIconMapReady) return;
        entityIconMap = new HashMap<Class, Item>() {{
            put(EntityPlayer.class, Item.flag);
            put(EntityArmoredZombie.class, Item.chainlink);
            put(EntityCreeper.class, Item.sulphur);
            put(EntityGhast.class, Item.ammoFireball);
            put(EntityPigZombie.class, Item.foodPorkchopCooked);
            put(EntityPig.class, Item.foodPorkchopRaw);
            put(EntityScorpion.class, Item.string);
            put(EntitySpider.class, Item.string);
            put(EntitySkeleton.class, Item.bone);
            put(EntitySlime.class, Item.slimeball);
            put(EntitySnowman.class, Item.ammoSnowball);
            put(EntityZombie.class, Item.cloth);
            put(EntityChicken.class, Item.featherChicken);
            put(EntityCow.class, Item.leather);
            put(EntityPainting.class, Item.painting);
            put(EntitySheep.class, Block.wool.asItem());
            put(EntitySquid.class, Item.dye);
            put(EntityWolf.class, Item.bone);
            put(EntityMinecart.class, Item.minecart);
        }};
    }

    public void updateBlockOverlayWindow() {
        init();
        I18n stringTranslate = I18n.getInstance();
        int OverlayWidth = this.theGame.resolution.scaledWidth;
        HitResult hitResult = BTWaila.blockToDraw;
        IOptions gameSettings = (IOptions)this.theGame.gameSettings;
        Block block = Block.getBlock(this.theGame.theWorld.getBlockId(hitResult.x, hitResult.y, hitResult.z));
        if (!gameSettings.getBlockTooltips().value) return;
        if (this.theGame.fontRenderer != null) {
            String languageKey = block.getLanguageKey(BTWaila.blockMetadata);
            if (block.asItem() instanceof ItemBlockPainted || block.asItem() instanceof ItemBlockSlabPainted || block.asItem() instanceof ItemBlockStairsPainted || block.asItem() instanceof ItemBlockLamp)
                languageKey = block.asItem().getLanguageKey(new ItemStack(block.asItem(), 1, BTWaila.blockMetadata));
            String blockName = stringTranslate.translateNameKey(languageKey);
            String blockDesc = stringTranslate.translateDescKey(languageKey);
            int maxTextWidth = Math.max(this.theGame.fontRenderer.getStringWidth("Cannot be harvested with current tool"), Math.max(this.theGame.fontRenderer.getStringWidth(blockName), this.theGame.fontRenderer.getStringWidth(blockDesc)));
            posX = OverlayWidth/2 - maxTextWidth/2;

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glBlendFunc(770, 771);


            ItemStack[] items = block.getBreakResult(this.theGame.theWorld, EnumDropCause.PICK_BLOCK, hitResult.x, hitResult.y, hitResult.z, this.theGame.theWorld.getBlockMetadata(hitResult.x, hitResult.y, hitResult.z), null);
            int renderItem = block.asItem().id;
            if (items != null && items.length > 0) renderItem = items[0].itemID;

            if (block != null) {
                this.itemRender.renderItemIntoGUI(this.theGame.fontRenderer, this.theGame.renderEngine, new ItemStack(renderItem, 1, BTWaila.blockMetadata), posX+8, offY, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
            }

            drawStringWithShadow(blockName, 0);
            drawStringWithShadow(blockDesc, 0, Colors.LIGHT_GRAY);
            EntityPlayerSP player = this.theGame.thePlayer;
            int itemId = 0;
            if (player != null && player.getGamemode() == Gamemode.survival) {
                if (Item.toolPickaxeSteel.canHarvestBlock(block)) {
                    itemId = Item.toolPickaxeSteel.id;
                } else if (Item.toolShearsSteel.canHarvestBlock(block)) {
                    itemId = Item.toolShearsSteel.id;
                } else if (Item.toolAxeSteel.canHarvestBlock(block)) {
                    itemId = Item.toolAxeSteel.id;
                } else if (Item.toolSwordSteel.canHarvestBlock(block)) {
                    itemId = Item.toolSwordSteel.id;
                } else if (Item.toolShovelSteel.canHarvestBlock(block)) {
                    itemId = Item.toolShovelSteel.id;
                } else if (Item.toolHoeSteel.canHarvestBlock(block)) {
                    itemId = Item.toolHoeSteel.id;
                }

                int miningLevelColor = Colors.LIGHT_GREEN;
                String harvestString = "Harvestable with current tool";
                if (!player.canHarvestBlock(block)) {
                    harvestString = "Cannot be harvested with current tool";
                    miningLevelColor = Colors.LIGHT_RED;
                }
                float damage = ((IPlayerControllerMixin)this.theGame.playerController).getCurrentDamage();
                if (damage != 0) {
                    harvestString = "Harvesting: "+(int)(damage*100)+"%";
                }

                if (itemId != 0) {
                    this.itemRender.renderItemIntoGUI(this.theGame.fontRenderer, this.theGame.renderEngine, new ItemStack(itemId, 1, 0), posX+8, offY, 1.0F);
                    GL11.glDisable(GL11.GL_LIGHTING);
                }
                drawStringWithShadow(harvestString, 0, miningLevelColor);
                if (itemId == ItemToolPickaxe.toolPickaxeSteel.id) {
                    Object miningLevel = ItemToolPickaxe.miningLevels.get(block);
                    if (miningLevel == null) miningLevel = 0;
                    drawStringWithShadow("Required mining level: " + miningLevel, 0, miningLevelColor);
                }
            }

            if (gameSettings.getBlockAdvancedTooltips().value) drawFunctionalBlocksData(block);
        }
        offY = paddingY;
    }

    private void drawFunctionalBlocksData(Block block) {
        World world = this.theGame.theWorld;
        HitResult hitResult = BTWaila.blockToDraw;
        TileEntity tileEntity = world.getBlockTileEntity(hitResult.x, hitResult.y, hitResult.z);
        if (tileEntity != null) {
            if (this.theGame.thePlayer instanceof EntityClientPlayerMP && BTWaila.canUseAdvancedTooltips) {
                EntityClientPlayerMP playerMP = (EntityClientPlayerMP) this.theGame.thePlayer;
                playerMP.sendQueue.addToSendQueue(new PacketRequestTileEntityData(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord));
            }
            for (TooltipGroup e : TooltipRegistry.tooltipMap) {
                if (e.getInterfaceClass().isInstance(tileEntity) && e.isInList(tileEntity.getClass()) && e.getCustomTooltip() instanceof IBTWailaCustomBlockTooltip) {
                    IBTWailaCustomBlockTooltip tooltip = (IBTWailaCustomBlockTooltip) e.getCustomTooltip();
                    tooltip.drawAdvancedTooltip(tileEntity, this);
                }
            }
        }
    }

    public void updateEntityOverlayWindow() {
        init();
        int OverlayWidth = this.theGame.resolution.scaledWidth;
        IOptions gameSettings = (IOptions)this.theGame.gameSettings;

        if (!gameSettings.getEntityTooltips().value) return;

        Entity entity = BTWaila.entityToDraw;
        boolean isLivingEntity = (entity instanceof EntityLiving);
        EntityLiving entityLiving = isLivingEntity ? (EntityLiving) entity : null;

        String entityName = isLivingEntity ? entityLiving.getDisplayName() : null;
        if (entityName == null || entityName.equalsIgnoreCase("§0")) entityName = EntityDispatcher.getEntityString(entity);

        int maxTextWidth = this.theGame.fontRenderer.getStringWidth(entityName);
        if (isLivingEntity) maxTextWidth = Math.max(entityLiving.health*5,maxTextWidth);
        posX = OverlayWidth / 2 - maxTextWidth / 2 - 50; // TODO: find a way to replace this 50

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBlendFunc(770, 771);
        Item itemToRender = (entityIconMap.containsKey(entity.getClass())) ? entityIconMap.get(entity.getClass()) : Item.eggChicken;
        this.itemRender.renderItemIntoGUI(this.theGame.fontRenderer, this.theGame.renderEngine, new ItemStack(itemToRender, 1, 0), posX+8, offY, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        int color = Colors.WHITE;
        if (isLivingEntity) {
            color = Colors.GREEN;
            if (entity instanceof EntityMonster) color = Colors.RED;
            else if (entity instanceof EntityPlayer) color = (int) entityLiving.chatColor;
        }

        drawStringWithShadow(entityName, 0, color);

        if (gameSettings.getEntityAdvancedTooltips().value) {
            if (this.theGame.thePlayer instanceof EntityClientPlayerMP && BTWaila.canUseAdvancedTooltips) {
                EntityClientPlayerMP playerMP = (EntityClientPlayerMP) this.theGame.thePlayer;
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
        offY = paddingY;
    }
    
    private void drawEntityHealth(EntityLiving entity) {
        boolean heartsFlash = this.theGame.thePlayer.heartsFlashTime / 3 % 2 == 1;
        if (this.theGame.thePlayer.heartsFlashTime < 10) {
            heartsFlash = false;
        }

        int health = entity.health;
        int prevHealth = entity.prevHealth;
        Random rand = new Random();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.theGame.renderEngine.bindTexture(this.theGame.renderEngine.getTexture("/gui/icons.png"));

        for(int index = 0; index < (int)Math.ceil((float)health/2f); ++index) {
            int y = offY;
            int heartOffset = 0;
            if (heartsFlash) {
                heartOffset = 1;
            }

            int x = posX + 32 + index * 8;
            if (health <= 4) {
                y += rand.nextInt(2);
            }

            this.drawTexturedModalRect(x, y, 16 + heartOffset * 9, 0, 9, 9);
            if (heartsFlash) {
                if (index * 2 + 1 < prevHealth) {
                    this.drawTexturedModalRect(x, y, 70, 0, 9, 9);
                }

                if (index * 2 + 1 == prevHealth) {
                    this.drawTexturedModalRect(x, y, 79, 0, 9, 9);
                }
            }

            if (index * 2 + 1 < health) {
                this.drawTexturedModalRect(x, y, 52, 0, 9, 9);
            }

            if (index * 2 + 1 == health) {
                this.drawTexturedModalRect(x, y, 61, 0, 9, 9);
            }
        }
        offY += 8;
    }
}
