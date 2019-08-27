package xyz.phanta.psicosts.integration.bloodmagic;

import net.minecraft.client.resources.I18n;
import xyz.phanta.psicosts.client.gui.base.GuiPsiCharger;
import xyz.phanta.psicosts.client.gui.component.PsioGuiComponentFactory;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.constant.ResConst;

public class GuiBloodResonator extends GuiPsiCharger<ContainerBloodResonator> {

    public GuiBloodResonator(ContainerBloodResonator container) {
        super(container, ResConst.GUI_BLOOD_RESONATOR);
        addComponent(PsioGuiComponentFactory.createReservoirBar(
                160, ResConst.GC_BLOOD_BAR_BG, ResConst.GC_BLOOD_BAR_FG, container.getBloodTank(), "LP"));
    }

    @Override
    public void drawForeground(float partialTicks, int mX, int mY) {
        super.drawForeground(partialTicks, mX, mY);
        drawContainerName(I18n.format(LangConst.GUI_BLOOD_RESONATOR));
    }

}
