package toufoumaster.btwaila.util;

public class ColorOptions {
    public int color = Colors.WHITE;

    public ColorOptions() {
    }

    public ColorOptions(int color) {
        this.color = color;
    }

    public ColorOptions setColor(int color) {
        this.color = color;
        return this;
    }
}
