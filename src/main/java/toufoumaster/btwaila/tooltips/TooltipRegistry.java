package toufoumaster.btwaila.tooltips;

import java.util.ArrayList;
import java.util.List;

public class TooltipRegistry {
    private TooltipRegistry() {}

    public static final List<TileTooltip<?>> tileTooltips = new ArrayList<>();
    public static final List<EntityTooltip<?>> entityTooltips = new ArrayList<>();
}
