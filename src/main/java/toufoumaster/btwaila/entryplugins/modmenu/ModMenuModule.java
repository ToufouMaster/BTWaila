package toufoumaster.btwaila.entryplugins.modmenu;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.Screen;
import toufoumaster.btwaila.BTWailaClient;

import java.util.function.Function;

public class ModMenuModule implements ModMenuApi {
    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory() {
        return BTWailaClient::getOptionsPage;
    }
}
