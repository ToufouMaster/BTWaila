package toufoumaster.btwaila.tooltips;

import toufoumaster.btwaila.tooltips.interfaces.IBTWailaCustomTooltip;

import java.util.ArrayList;
import java.util.List;

public class TooltipGroup {
    @SuppressWarnings("FieldCanBeLocal")
    private final String modId;
    private final Class<?> interfaceClass;
    private final IBTWailaCustomTooltip customTooltip;
    private final List<Class<?>> tileEntityList = new ArrayList<>();
    public TooltipGroup(String modId, Class<?> interfaceClass, IBTWailaCustomTooltip customTooltip) {
        this.modId = modId;
        this.interfaceClass = interfaceClass;
        this.customTooltip = customTooltip;
    }

    public void addTooltip(Class<?> entityClass) {
        tileEntityList.add(entityClass);
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public IBTWailaCustomTooltip getCustomTooltip() {
        return customTooltip;
    }

    public boolean isInList(Class<?> entityClass) {
        return tileEntityList.contains(entityClass);
    }
}
