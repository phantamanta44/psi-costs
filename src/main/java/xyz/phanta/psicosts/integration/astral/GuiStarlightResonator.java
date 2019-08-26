package xyz.phanta.psicosts.integration.astral;

import net.minecraft.client.resources.I18n;
import xyz.phanta.psicosts.client.gui.base.GuiPsiCharger;
import xyz.phanta.psicosts.client.gui.component.PsioGuiComponentFactory;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.constant.ResConst;

public class GuiStarlightResonator extends GuiPsiCharger<ContainerStarlightResonator> {

    public GuiStarlightResonator(ContainerStarlightResonator container) {
        super(container);
        addComponent(PsioGuiComponentFactory.createFluidBar(
                160, ResConst.GC_STARLIGHT_BAR_BG, ResConst.GC_STARLIGHT_BAR_FG, container.getStarlightTank()));
    }

    @Override
    public void drawForeground(float partialTicks, int mX, int mY) {
        super.drawForeground(partialTicks, mX, mY);
        drawContainerName(I18n.format(LangConst.GUI_STARLIGHT_RESONATOR));
    }

}
