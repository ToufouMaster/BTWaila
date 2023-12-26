package toufoumaster.btwaila.util;

import net.minecraft.core.util.helper.Side;

public class TextureOptions extends ColorOptions {
    public int blockId = 0;
    public Side side;
    public int metadata = 0;

    public TextureOptions() {
        super();
    }

    public TextureOptions setColor(int color) {
        this.color = color;
        return this;
    }

    public TextureOptions(int color, int blockId, int metadata, Side side) {
        super(color);
        this.blockId = blockId;
        this.side = side;
        this.metadata = metadata;
    }

    public TextureOptions setBlockId(int blockId) {
        this.blockId = blockId;
        return this;
    }

    public TextureOptions setMetadata(int metadata) {
        this.metadata = metadata;
        return this;
    }

    public TextureOptions setSide(Side side) {
        this.side = side;
        return this;
    }
}
