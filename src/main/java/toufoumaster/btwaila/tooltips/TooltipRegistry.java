package toufoumaster.btwaila.tooltips;

import java.util.ArrayList;
import java.util.List;

public class TooltipRegistry {
    private static final TooltipRegistry INSTANCE = new TooltipRegistry();
    public static TooltipRegistry getInstance(){
        return INSTANCE;
    }
    public <T extends Tooltip<?>> T register(T tooltip){
        if (tooltip instanceof TileTooltip){
            tileTooltips.add((TileTooltip<?>) tooltip);
            return tooltip;
        }
        if (tooltip instanceof EntityTooltip){
            entityTooltips.add((EntityTooltip<?>) tooltip);
            return tooltip;
        }
        throw new IllegalArgumentException("Tooltip '" + tooltip.getClass().getSimpleName() + "' is not a valid type for registration!");
    }

    public static final List<TileTooltip<?>> tileTooltips = new ArrayList<>();
    public static final List<EntityTooltip<?>> entityTooltips = new ArrayList<>();
}
