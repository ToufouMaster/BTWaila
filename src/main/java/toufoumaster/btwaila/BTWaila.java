package toufoumaster.btwaila;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Global;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityFlag;
import net.minecraft.core.block.entity.TileEntitySign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import toufoumaster.btwaila.entryplugins.waila.BTWailaCustomTooltipPlugin;
import toufoumaster.btwaila.mixin.mixins.accessors.PacketAccessor;
import toufoumaster.btwaila.network.packet.PacketEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestTileEntityData;
import toufoumaster.btwaila.util.VersionHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;

import java.util.HashMap;
import java.util.Map;


public class BTWaila implements GameStartEntrypoint {
    public static final String MOD_ID = "btwaila";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static boolean canUseAdvancedTooltips = false;
    public static VersionHelper modVersion = new VersionHelper(0, 2, 2);
    public static String checkString = modVersion.generateCheckString();
    public static Map<Class<? extends TileEntity>, Boolean> excludeContinuousTileEntityData = new HashMap<>();
    public static void excludeContinuousTileEntityPacketUpdateClass(Class<? extends TileEntity> tileEntityClass) {
        excludeContinuousTileEntityData.put(tileEntityClass, true);
    }
    static {
        excludeContinuousTileEntityPacketUpdateClass(TileEntitySign.class);
        excludeContinuousTileEntityPacketUpdateClass(TileEntityFlag.class);
    }
    @Override
    public void beforeGameStart() {
        PacketAccessor.callAddIdClassMapping(220, false, true, PacketRequestTileEntityData.class);
        PacketAccessor.callAddIdClassMapping(221, false, true, PacketRequestEntityData.class);
        PacketAccessor.callAddIdClassMapping(222, true, false, PacketEntityData.class);
    }

    @Override
    public void afterGameStart() {
        LOGGER.info("Loading implementations.");

        if (!Global.isServer){
            BTWailaClient.onLoad();
        }

        FabricLoader.getInstance().getEntrypointContainers("btwaila", BTWailaCustomTooltipPlugin.class).forEach(plugin -> plugin.getEntrypoint().initializePlugin(LOGGER));

        LOGGER.info("BTWaila initialized.");
        System.out.println(modVersion);
    }
}
