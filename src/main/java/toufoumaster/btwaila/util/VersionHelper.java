package toufoumaster.btwaila.util;

import net.minecraft.client.Minecraft;
import toufoumaster.btwaila.BTWaila;

public class VersionHelper {
    final int major;
    final int minor;
    final int patch;

    public VersionHelper(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public static VersionHelper getModVersionBasedOnString(String text) {
        try {
            String stringVersion = text.split("\\[§[a-r0-9]")[1].split("§[a-r0-9]]")[0]; // get the major.minor.patch version string
            String[] versionArray = stringVersion.split("\\.");
            return new VersionHelper(Integer.parseInt(versionArray[0]), Integer.parseInt(versionArray[1]), Integer.parseInt(versionArray[2]));
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
        Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
        mc.ingameGUI.addChatMessage((text));
        mc.ingameGUI.addChatMessage((text2));
    }

    private static void upgradeAvailablePrint(VersionHelper versionHelper) {
        String text = "§4The current BTWaila version installed §0[§5"+BTWaila.modVersion.major+"."+BTWaila.modVersion.minor+"."+BTWaila.modVersion.patch+"§0]§4 got an update. ";
        String text2 = "§4The actual server is using it §0[§5"+versionHelper.major+"."+versionHelper.minor+"."+versionHelper.patch+"§0]§4, don't forget to update";
        BTWaila.LOGGER.info(text+text2);
        Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
        mc.ingameGUI.addChatMessage((text));
        mc.ingameGUI.addChatMessage((text2));
    }

    private static void serverOutDatedPrint(VersionHelper versionHelper) {
        String text = "§eThe current BTWaila version installed on this server is ";
        String text2 = "§eoutdated §0[§5"+versionHelper.major+"."+versionHelper.minor+"."+versionHelper.patch+"§0]§e. Please contact the server owner";
        BTWaila.LOGGER.warn(text+text2);
        Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
        mc.ingameGUI.addChatMessage((text));
        mc.ingameGUI.addChatMessage((text2));
    }

    @Override
    public String toString()
    {
        return "BTWaila VersionHelper: major="+major+", minor="+minor+", patch="+patch+" ["+major+"."+minor+"."+patch+"]";
    }

    public String generateCheckString() {
        return "§6This server uses BetterThanWaila to display advanced tooltips.[§5"+major+"."+minor+"."+patch+"§0] Link:§4 github.com/ToufouMaster/BTWaila/releases";
    }
}
