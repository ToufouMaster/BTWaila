package toufoumaster.btwaila.util;

public class ProgressBarOptions {

    public int boxWidth = 0;
    public String text;
    public boolean values = true;
    public boolean percentage = true;
    public TextureOptions bgOptions = new TextureOptions(Colors.GRAY, 0);
    public TextureOptions fgOptions = new TextureOptions(Colors.LIGHT_GRAY, 0);

    public ProgressBarOptions() {

    }

    public ProgressBarOptions(int boxWidth, String text, boolean values, boolean percentage) {
        this.boxWidth = boxWidth;
        this.text = text;
        this.values = values;
        this.percentage = percentage;
    }

    public ProgressBarOptions(int boxWidth, String text, boolean values, boolean percentage, TextureOptions bgTexOpt, TextureOptions fgTexOpt) {
        this(boxWidth, text, values, percentage);
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
