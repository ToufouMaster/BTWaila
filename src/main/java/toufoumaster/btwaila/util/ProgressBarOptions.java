package toufoumaster.btwaila.util;

import net.minecraft.client.render.texture.stitcher.TextureRegistry;

public class ProgressBarOptions {

    public int boxWidth;
    public String text;
    public boolean values;
    public boolean percentage;
    public TextureOptions bgOptions;
    public TextureOptions fgOptions;

    public ProgressBarOptions() {
        this(0, "", true, true);
    }

    public ProgressBarOptions(int boxWidth, String text, boolean values, boolean percentage) {
        this(boxWidth, text, values, percentage, new TextureOptions(Colors.GRAY, TextureRegistry.getTexture("minecraft:block/polished_stone_top")), new TextureOptions(Colors.LIGHT_GRAY, TextureRegistry.getTexture("minecraft:block/stone")));
    }

    public ProgressBarOptions(int boxWidth, String text, boolean values, boolean percentage, TextureOptions bgTexOpt, TextureOptions fgTexOpt) {
        this.boxWidth = boxWidth;
        this.text = text;
        this.values = values;
        this.percentage = percentage;
        this.bgOptions = bgTexOpt;
        this.fgOptions = fgTexOpt;
    }

    public ProgressBarOptions setBoxWidth(int boxWidth) {
        this.boxWidth = boxWidth;
        return this;
    }

    public ProgressBarOptions setText(String text) {
        this.text = text;
        return this;
    }

    public ProgressBarOptions setValues(boolean values) {
        this.values = values;
        return this;
    }

    public ProgressBarOptions setPercentage(boolean percentage) {
        this.percentage = percentage;
        return this;
    }

    public ProgressBarOptions setBackgroundOptions(TextureOptions bgOptions) {
        this.bgOptions = bgOptions;
        return this;
    }

    public ProgressBarOptions setForegroundOptions(TextureOptions fgOptions) {
        this.fgOptions = fgOptions;
        return this;
    }
}
