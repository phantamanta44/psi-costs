package xyz.phanta.psicosts.client.gui;

import net.minecraft.client.resources.I18n;
import xyz.phanta.psicosts.client.gui.base.GuiPsiCharger;
import xyz.phanta.psicosts.client.gui.component.PsioGuiComponentFactory;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.constant.ResConst;
import xyz.phanta.psicosts.inventory.ContainerFluxResonator;

public class GuiFluxResonator extends GuiPsiCharger<ContainerFluxResonator> {

    public GuiFluxResonator(ContainerFluxResonator container) {
        super(container);
        addComponent(PsioGuiComponentFactory.createReservoirBar(
                160, ResConst.GC_ENERGY_BAR_BG, ResConst.GC_ENERGY_BAR_FG, container.getEnergyReservoir(), "FE"));
    }

    @Override
    public void drawForeground(float partialTicks, int mX, int mY) {
        super.drawForeground(partialTicks, mX, mY);
        drawContainerName(I18n.format(LangConst.GUI_FLUX_RESONATOR));
    }

}
