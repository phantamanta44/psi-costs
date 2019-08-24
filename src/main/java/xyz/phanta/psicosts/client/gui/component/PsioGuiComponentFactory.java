package xyz.phanta.psicosts.client.gui.component;

import io.github.phantamanta44.libnine.client.gui.component.impl.GuiComponentVerticalBar;
import io.github.phantamanta44.libnine.component.reservoir.IIntReservoir;
import io.github.phantamanta44.libnine.util.render.TextureRegion;
import xyz.phanta.psicosts.capability.PsiProvider;
import xyz.phanta.psicosts.constant.ResConst;
import xyz.phanta.psicosts.util.TooltipUtils;

public class PsioGuiComponentFactory {

    public static GuiComponentVerticalBar createPsiBar(PsiProvider psi) {
        return new GuiComponentVerticalBar(165, 7, ResConst.GC_PSI_BAR_BG, ResConst.GC_PSI_BAR_FG, 1, 1,
                () -> psi.getPsiEnergy() / (float)psi.getPsiEnergyMax(),
                () -> TooltipUtils.formatPsiEnergy(psi));
    }

    public static GuiComponentVerticalBar createReservoirBar(int x, TextureRegion bg, TextureRegion fg,
                                                             IIntReservoir src, String unit) {
        return new GuiComponentVerticalBar(x, 7, bg, fg, 1, 1,
                () -> src.getQuantity() / (float)src.getCapacity(),
                () -> TooltipUtils.formatFraction(src.getQuantity(), src.getCapacity(), unit));
    }

}
