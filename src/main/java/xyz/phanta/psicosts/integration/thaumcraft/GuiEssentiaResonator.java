package xyz.phanta.psicosts.integration.thaumcraft;

import net.minecraft.client.resources.I18n;
import xyz.phanta.psicosts.client.gui.base.GuiPsiCharger;
import xyz.phanta.psicosts.client.gui.component.PsioGuiComponentFactory;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.constant.ResConst;

public class GuiEssentiaResonator extends GuiPsiCharger<ContainerEssentiaResonator> {

    public GuiEssentiaResonator(ContainerEssentiaResonator container) {
        super(container);
        addComponent(PsioGuiComponentFactory.createReservoirBar(
                160, ResConst.GC_ESSENTIA_BAR_BG, ResConst.GC_ESSENTIA_BAR_FG, container.getEssentiaTank(), "Ess"));
    }

    @Override
    public void drawForeground(float partialTicks, int mX, int mY) {
        super.drawForeground(partialTicks, mX, mY);
        drawContainerName(I18n.format(LangConst.GUI_ESSENTIA_RESONATOR));
    }

}
