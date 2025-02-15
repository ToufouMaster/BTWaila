package toufoumaster.btwaila.util;

import net.minecraft.client.render.texture.stitcher.IconCoordinate;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;

public class TextureOptions extends ColorOptions {
    public IconCoordinate coordinate;

    public TextureOptions setColor(int color) {
        this.color = color;
        return this;
    }
    public TextureOptions(int color, String textureId) {
        this(color, TextureRegistry.getTexture(textureId));
    }
    public TextureOptions(int color, IconCoordinate coordinate) {
        this.color = color;
        this.coordinate = coordinate;
    }
    public TextureOptions setCoordinate(IconCoordinate coordinate){
        this.coordinate = coordinate;
        return this;
    }
}
