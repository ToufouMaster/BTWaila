package toufoumaster.btwaila.tooltips;

import org.jetbrains.annotations.ApiStatus;
import toufoumaster.btwaila.gui.components.AdvancedInfoComponent;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
@ApiStatus.Internal
public abstract class Tooltip<T> {
    private final List<Class<? extends T>> toolTipClasses = new ArrayList<>();
    public void addClass(Class<? extends T> entityClass) {
        toolTipClasses.add(entityClass);
    }
    public boolean isInstance(Object object){
        return getClassType().isInstance(object);
    }
    public Class getClassType() {
        return ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public boolean isInList(Class<?> entityClass) {
        return toolTipClasses.contains(entityClass);
    }
    abstract public void initTooltip();
    abstract public void drawAdvancedTooltip(T interfaceObject, AdvancedInfoComponent advancedInfoComponent);
}
