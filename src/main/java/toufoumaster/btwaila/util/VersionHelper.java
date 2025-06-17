package toufoumaster.btwaila.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.PacketCustomPayload;
import toufoumaster.btwaila.BTWaila;

public class VersionHelper {
    public final byte major;
    public final byte minor;
    public final byte patch;

    public VersionHelper(byte major, byte minor, byte patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public static VersionHelper getModVersionBasedOnString(String text) {
        try {
            String stringVersion = text.split("\\[§[a-r0-9]")[1].split("§[a-r0-9]]")[0]; // get the major.minor.patch version string
            String[] versionArray = stringVersion.split("\\.");
            return new VersionHelper(Byte.parseByte(versionArray[0]), Byte.parseByte(versionArray[1]), Byte.parseByte(versionArray[2]));
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean checkModVersion(VersionHelper versionHelper) {
        boolean isValid = false;
        boolean isUpToDate = false;
        boolean serverOutDated = false;
        if (BTWaila.modVersion.major == versionHelper.major) {
            if (BTWaila.modVersion.minor == versionHelper.minor) {
                if (BTWaila.modVersion.patch >= versionHelper.patch) {
                    isUpToDate = true;
                }
                isValid = true;
            } else if (BTWaila.modVersion.minor >= versionHelper.minor) {
                serverOutDated = true;
            }
        } else if (BTWaila.modVersion.major >= versionHelper.major) {
            serverOutDated = true;
        }
        if (serverOutDated) serverOutDatedPrint(versionHelper); else
        if (!isValid) outDatedPrint(versionHelper); else
        if (!isUpToDate) upgradeAvailablePrint(versionHelper);
        return isValid;
    }

    private static void outDatedPrint(VersionHelper versionHelper) {
        String text = "§eThe current BTWaila version installed §0[§5"+BTWaila.modVersion.major+"."+BTWaila.modVersion.minor+"."+BTWaila.modVersion.patch+"§0]§e is outdated.";
        String text2 = "Server requirement §0[§5"+versionHelper.major+"."+versionHelper.minor+".x§0]";
        BTWaila.LOGGER.warn(text+text2);
        Minecraft mc = Minecraft.getMinecraft();
        mc.hudIngame.addChatMessage((text));
        mc.hudIngame.addChatMessage((text2));
    }

    private static void upgradeAvailablePrint(VersionHelper versionHelper) {
        String text = "§4The current BTWaila version installed §0[§5"+BTWaila.modVersion.major+"."+BTWaila.modVersion.minor+"."+BTWaila.modVersion.patch+"§0]§4 got an update. ";
        String text2 = "§4The actual server is using it §0[§5"+versionHelper.major+"."+versionHelper.minor+"."+versionHelper.patch+"§0]§4, don't forget to update";
        BTWaila.LOGGER.info(text+text2);
        Minecraft mc = Minecraft.getMinecraft();
        mc.hudIngame.addChatMessage((text));
        mc.hudIngame.addChatMessage((text2));
    }

    private static void serverOutDatedPrint(VersionHelper versionHelper) {
        String text = "§eThe current BTWaila version installed on this server is ";
        String text2 = "§eoutdated §0[§5"+versionHelper.major+"."+versionHelper.minor+"."+versionHelper.patch+"§0]§e. Please contact the server owner";
        BTWaila.LOGGER.warn(text+text2);
        Minecraft mc = Minecraft.getMinecraft();
        mc.hudIngame.addChatMessage((text));
        mc.hudIngame.addChatMessage((text2));
    }

    @Override
    public String toString()
    {
        return "BTWaila VersionHelper: major="+major+", minor="+minor+", patch="+patch+" ["+major+"."+minor+"."+patch+"]";
    }

//    public String generateCheckString() {
//        return "§6This server uses BetterThanWaila to display advanced tooltips.[§5"+major+"."+minor+"."+patch+"§0] Link:§4 github.com/ToufouMaster/BTWaila/releases";
//    }

    public Packet getPacket(){
        return new PacketCustomPayload("BTWaila|VersionCheck", compilePayload());
    }
    public byte[] compilePayload(){
        final byte payloadVersion = 1; // Change if the data format changes
        return new byte[]{payloadVersion, major, minor, patch};
    }
    public static void handlePacket(PacketCustomPayload payloadPacket){
        byte version = payloadPacket.data[0];
        switch (version){
            case 1:
                byte major = payloadPacket.data[1];
                byte minor = payloadPacket.data[2];
                byte patch = payloadPacket.data[3];
                VersionHelper serverHelper = new VersionHelper(major, minor, patch);
                if (VersionHelper.checkModVersion(serverHelper)) {
                    BTWaila.canUseAdvancedTooltips = true;
                }
                break;
            default:
                BTWaila.LOGGER.warn("Unrecognized version '" + version + "' ignoring!");
        }
    }
}
