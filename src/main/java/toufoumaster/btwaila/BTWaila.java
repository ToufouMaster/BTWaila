package toufoumaster.btwaila;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Global;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.entity.TileEntityFlag;
import net.minecraft.core.block.entity.TileEntitySign;
import net.minecraft.core.lang.I18n;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import toufoumaster.btwaila.entryplugins.waila.BTWailaCustomTooltipPlugin;
import toufoumaster.btwaila.entryplugins.waila.BTWailaPlugin;
import toufoumaster.btwaila.mixin.mixins.accessors.PacketAccessor;
import toufoumaster.btwaila.network.packet.PacketEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestEntityData;
import toufoumaster.btwaila.network.packet.PacketRequestTileEntityData;
import toufoumaster.btwaila.tooltips.TooltipRegistry;
import toufoumaster.btwaila.util.VersionHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;

import java.util.HashMap;
import java.util.Map;


public class BTWaila implements GameStartEntrypoint, ModInitializer {
    public static final String MOD_ID = "btwaila";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static I18n translator = null;
    public static boolean canUseAdvancedTooltips = false;
    public static String versionString = FabricLoader.getInstance().getModContainer(MOD_ID).get().getMetadata().getVersion().getFriendlyString();
    private static String[] versionNumbers = versionString.split("-")[0].split("\\.");
    static {
        if (versionNumbers.length != 3){
            versionNumbers = new String[]{"9", "9", "99"};
        }
    }
    public static final VersionHelper modVersion = new VersionHelper(Integer.decode(versionNumbers[0]), Integer.decode(versionNumbers[1]), Integer.decode(versionNumbers[2]));
    public static final String checkString = modVersion.generateCheckString();
    public static final Map<Class<? extends TileEntity>, Boolean> excludeContinuousTileEntityData = new HashMap<>();
    public static void excludeContinuousTileEntityPacketUpdateClass(Class<? extends TileEntity> tileEntityClass) {
        excludeContinuousTileEntityData.put(tileEntityClass, true);
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
        translator = I18n.getInstance();
        LOGGER.info("Loading implementations.");

        if (!Global.isServer){
            BTWailaClient.onLoad();
        }

        new BTWailaPlugin().initializePlugin(TooltipRegistry.getInstance(), LOGGER); // Load BTWaila tooltips first
        FabricLoader.getInstance().getEntrypointContainers("btwaila", BTWailaCustomTooltipPlugin.class).forEach(plugin -> plugin.getEntrypoint().initializePlugin(TooltipRegistry.getInstance(), LOGGER));

        LOGGER.info("BTWaila initialized.");
        System.out.println(modVersion);
    }

    @Override
    public void onInitialize() {
        PacketAccessor.callAddIdClassMapping(220, false, true, PacketRequestTileEntityData.class);
        PacketAccessor.callAddIdClassMapping(221, false, true, PacketRequestEntityData.class);
        PacketAccessor.callAddIdClassMapping(222, true, false, PacketEntityData.class);
    }
}
