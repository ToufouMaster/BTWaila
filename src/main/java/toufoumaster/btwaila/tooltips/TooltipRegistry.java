package toufoumaster.btwaila.tooltips;

import java.util.ArrayList;
import java.util.List;

public class TooltipRegistry {
    private static final TooltipRegistry INSTANCE = new TooltipRegistry();
    public static TooltipRegistry getInstance(){
        return INSTANCE;
    }
    public Tooltip<?> registerTooltip(Tooltip<?> tooltip){
        if (tooltip instanceof TileTooltip){
            tileTooltips.add((TileTooltip<?>) tooltip);
            return tooltip;
        }
        if (tooltip instanceof EntityTooltip){
            entityTooltips.add((EntityTooltip<?>) tooltip);
            return tooltip;
        }
        return tooltip;
    }

    public static final List<TileTooltip<?>> tileTooltips = new ArrayList<>();
    public static final List<EntityTooltip<?>> entityTooltips = new ArrayList<>();
}
