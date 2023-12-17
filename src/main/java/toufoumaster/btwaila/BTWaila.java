package toufoumaster.btwaila;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Global;
import net.minecraft.core.HitResult;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityFlag;
import net.minecraft.core.block.entity.TileEntitySign;
import net.minecraft.core.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import toufoumaster.btwaila.mixin.PacketMixin;
import toufoumaster.btwaila.network.packet.PacketEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestTileEntityData;
import toufoumaster.btwaila.util.VersionHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;

import java.util.HashMap;
import java.util.Map;


public class BTWaila implements ModInitializer, GameStartEntrypoint {
    public static final String MOD_ID = "btwaila";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static boolean canUseAdvancedTooltips = false;
    public static VersionHelper modVersion = new VersionHelper(0, 2, 2);
    public static String checkString = modVersion.generateCheckString();
    public static Map<Class<TileEntity>, Boolean> excludeContinousTileEntityData = new HashMap<Class<TileEntity>, Boolean>();
    public static void excludeContinuousTileEntityPacketUpdateClass(Class tileEntityClass) {
        excludeContinousTileEntityData.put(tileEntityClass, true);
    }
    static {
        excludeContinuousTileEntityPacketUpdateClass(TileEntitySign.class);
        excludeContinuousTileEntityPacketUpdateClass(TileEntityFlag.class);
    }
    @Override
    public void beforeGameStart() {

    }

    @Override
    public void afterGameStart() {
        LOGGER.info("Loading implementations.");

        if (!Global.isServer){
            BTWailaClient.onLoad();
        }

        FabricLoader.getInstance().getEntrypointContainers("btwaila", BTWailaCustomTootltipPlugin.class).forEach(plugin -> {
            plugin.getEntrypoint().initializePlugin(LOGGER);
        });

        LOGGER.info("BTWaila initialized.");
        System.out.println(modVersion);
    }

    @Override
    public void onInitialize() {
        PacketMixin.callAddIdClassMapping(220, false, true, PacketRequestTileEntityData.class);
        PacketMixin.callAddIdClassMapping(221, false, true, PacketRequestEntityData.class);
        PacketMixin.callAddIdClassMapping(222, true, false, PacketEntityData.class);
    }
}
