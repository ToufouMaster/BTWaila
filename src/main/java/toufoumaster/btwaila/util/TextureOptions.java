package toufoumaster.btwaila.util;

import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.Side;

public class TextureOptions extends ColorOptions {
    public int blockId = 0;
    public Side side;
    public int metadata = 0;
    public int index;

    public TextureOptions() {
        super();
    }

    public TextureOptions setColor(int color) {
        this.color = color;
        return this;
    }
    public TextureOptions(int color, int texX, int texY) {
        this(color, Block.texCoordToIndex(texX, texY));
    }
    public TextureOptions(int color, int index) {
        this.color = color;
        this.index = index;
    }
    @Deprecated
    public TextureOptions(int color, int blockId, int metadata, Side side) {
        super(color);
        this.blockId = blockId;
        this.side = side;
        this.metadata = metadata;
        recalcIndex();
    }
    protected void recalcIndex(){
        Block block = Block.blocksList[blockId];
        index = block.getBlockTextureFromSideAndMetadata(side, metadata);
    }
    @Deprecated
    public TextureOptions setBlockId(int blockId) {
        this.blockId = blockId;
        recalcIndex();
        return this;
    }
    @Deprecated
    public TextureOptions setMetadata(int metadata) {
        this.metadata = metadata;
        recalcIndex();
        return this;
    }
    @Deprecated
    public TextureOptions setSide(Side side) {
        this.side = side;
        recalcIndex();
        return this;
    }
    public TextureOptions setIndex(int index){
        this.index = index;
        return this;
    }
    public TextureOptions setIcon(int texX, int texY){
        return setIndex(Block.texCoordToIndex(texX, texY));
    }
}
