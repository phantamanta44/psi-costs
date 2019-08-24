package xyz.phanta.psicosts.client.gui.base;

import io.github.phantamanta44.libnine.client.gui.L9GuiContainer;
import io.github.phantamanta44.libnine.util.format.TextFormatUtils;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import io.github.phantamanta44.libnine.util.render.TextureRegion;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xyz.phanta.psicosts.client.gui.component.PsioGuiComponentFactory;
import xyz.phanta.psicosts.constant.ResConst;
import xyz.phanta.psicosts.init.PsioCaps;
import xyz.phanta.psicosts.inventory.base.ContainerPsiCharger;

import java.awt.Color;

public class GuiPsiCharger<C extends ContainerPsiCharger<?>> extends L9GuiContainer {

    private final C container;
    private float hue = 0F;
    private float[] angles = { 0F, 0F, 0F };

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
            TextFormatUtils.setGlColour(Color.HSBtoRGB(hue, charge, 0.5F + 0.5F * charge), 1F);
            hue += charge / 90F;
            for (int i = 0; i < 3; i++) {
                drawRing(ResConst.EXT_PSI_SPELL_CIRCLE[i], angles[i], i == 1 ? -1 : 1);
                angles[i] += charge * (i + 1);
            }
        } else {
            GlStateManager.color(0.5F, 0.5F, 0.5F, 1F);
            for (int i = 0; i < 3; i++) {
                drawRing(ResConst.EXT_PSI_SPELL_CIRCLE[i], angles[i], i == 1 ? -1 : 1);
            }
        }
        GlStateManager.color(1F, 1F, 1F, 1F);
    }

    private void drawRing(TextureRegion tex, float angle, float sign) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(88F + guiLeft, 43F + guiTop, 0F);
        GlStateManager.rotate(angle * sign, 0F, 0F, 1F);
        tex.draw(-32, -32, 64, 64);
        GlStateManager.popMatrix();
    }

}
