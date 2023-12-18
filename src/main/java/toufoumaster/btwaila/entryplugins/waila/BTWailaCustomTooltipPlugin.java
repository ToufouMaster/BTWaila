package toufoumaster.btwaila.entryplugins.waila;

import org.slf4j.Logger;
import toufoumaster.btwaila.tooltips.TooltipRegistry;

public interface BTWailaCustomTooltipPlugin {
    void initializePlugin(TooltipRegistry registry, Logger logger);
}
