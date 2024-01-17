package toufoumaster.btwaila.util;

public enum SmallHealthBarEnum {
    ALWAYS(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int row;

    SmallHealthBarEnum(int row) {
        this.row = row;
    }

    public int getRow() {
        return this.row;
    }
}
