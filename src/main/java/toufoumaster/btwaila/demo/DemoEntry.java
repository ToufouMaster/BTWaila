package toufoumaster.btwaila.demo;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityBasket;
import net.minecraft.core.block.entity.TileEntityFlag;
import net.minecraft.core.block.entity.TileEntityFlowerJar;
import net.minecraft.core.block.entity.TileEntityFurnace;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.block.entity.TileEntityNote;
import net.minecraft.core.block.entity.TileEntityRecordPlayer;
import net.minecraft.core.block.entity.TileEntitySeat;
import net.minecraft.core.block.entity.TileEntityTrommel;
import net.minecraft.core.crafting.LookupFuelFurnace;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.animal.EntityChicken;
import net.minecraft.core.entity.animal.EntityPig;
import net.minecraft.core.entity.monster.EntityHuman;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.monster.EntitySkeleton;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.entity.vehicle.EntityMinecart;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.server.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class DemoEntry {
    public Block block;
    public int meta;
    public Entity entity;
    public TileEntity tileEntity;
    public ItemStack[] drops;
    public DemoEntry(Block block, int meta, TileEntity tileEntity, ItemStack[] drops){
        this.block = block;
        this.meta = meta;
        this.tileEntity = tileEntity;
        this.drops = drops;
    }
    public DemoEntry(Entity entity){
        this.entity = entity;
    }
}
