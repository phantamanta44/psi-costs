package xyz.phanta.psicosts.client.gui.base;

import io.github.phantamanta44.libnine.client.gui.L9GuiContainer;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.client.gui.component.PsioGuiComponentFactory;
import xyz.phanta.psicosts.constant.ResConst;
import xyz.phanta.psicosts.init.PsioCaps;
import xyz.phanta.psicosts.inventory.base.ContainerPsiCharger;
import xyz.phanta.psicosts.util.MagicCircleRender;

public class GuiPsiCharger<C extends ContainerPsiCharger<?>> extends L9GuiContainer {

    private final C container;
    private final MagicCircleRender circleRender = Psio.PROXY.newMagicCircleRender();

    public GuiPsiCharger(C container, ResourceLocation bg) {
        super(container, bg);
        this.container = container;
        addComponent(PsioGuiComponentFactory.createPsiBar(container.getPsiProvider()));
    }

    public GuiPsiCharger(C container) {
        this(container, ResConst.GUI_PSI_CHARGER);
    }

    @Override
    public void drawBackground(float partialTicks, int mX, int mY) {
        super.drawBackground(partialTicks, mX, mY);
        ItemStack stack = container.getInputStack();
        if (stack.hasCapability(PsioCaps.PSI_CELL, null)) {
            float charge = OptUtils.capability(stack, PsioCaps.PSI_CELL)
                    .map(c -> c.getStoredCharge() / (float)c.getMaxCharge()).orElse(0F);
            circleRender.render2d(guiLeft, guiTop, charge);
            circleRender.tick(charge, partialTicks);
        } else {
            circleRender.reset();
            circleRender.render2d(guiLeft, guiTop, 0F);
        }
    }

}
