package toufoumaster.btwaila.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public enum BackgroundStyle {
    DEFAULT("default"),
    STONE("stone"),
    PLANKS("planks"),
    RPG("rpg"),
    CRT("crt"),
    DETAILED("detailed"),
    COMFY("comfy"),
    NOTEBLOCK("noteblock"),
    RETRO("retro"),
    PICNIC("picnic"),
    BATTLE_UI("battle_ui"),
    INTERDIMENSIONAL("interdimensional"),
    REDSTONE_GOGGLES("redstone_goggles"),
    CREEP_O_VISION("creep-o-vision"),
    STEVE("steve");

    public final String name;

    BackgroundStyle(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return "/assets/minecraft/textures/gui/tooltip/" + this.name + ".png";
    }

    public String getTranslationKey() {
        return "options.backgroundStyle." + this.name;
    }

    public String toString() {
        return this.name;
    }
}
