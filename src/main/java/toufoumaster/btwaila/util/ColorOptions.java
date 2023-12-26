package toufoumaster.btwaila.util;

public class ColorOptions {
    public int color;

    public ColorOptions() {
        this(Colors.WHITE);
    }

    public ColorOptions(int color) {
        this.color = color;
    }

    public ColorOptions setColor(int color) {
        this.color = color;
        return this;
    }
}
