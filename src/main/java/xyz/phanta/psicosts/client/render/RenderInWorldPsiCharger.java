package xyz.phanta.psicosts.client.render;

import io.github.phantamanta44.libnine.client.event.ClientTickHandler;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import io.github.phantamanta44.libnine.util.math.MathUtils;
import io.github.phantamanta44.libnine.util.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import xyz.phanta.psicosts.init.PsioCaps;
import xyz.phanta.psicosts.tile.base.TileInWorldPsiCharger;
import xyz.phanta.psicosts.util.MagicCircleRender;

public class RenderInWorldPsiCharger<T extends TileInWorldPsiCharger> extends TileEntitySpecialRenderer<T> {

    private final double circleAltitude;
    private final float circleScale;
    private final double itemAltitude;
    private final double itemAltitudeVariance;
    private final float itemScale;

    public RenderInWorldPsiCharger(double circleAltitude, float circleScale,
                                   double itemAltitude, double itemAltitudeVariance, float itemScale) {
        this.circleAltitude = circleAltitude;
        this.circleScale = circleScale;
        this.itemAltitude = itemAltitude;
        this.itemAltitudeVariance = itemAltitudeVariance;
        this.itemScale = itemScale;
    }

    @Override
    public void render(T tile, double x, double y, double z, float partialTicks,
                       int destroyStage, float alpha) {
        Minecraft mc = Minecraft.getMinecraft();
        ItemStack stack = tile.getInputSlot().getStackInSlot();

        // render circles
        MagicCircleRender circleRender = tile.getCircleRender();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.disableAlpha();
        GlStateManager.enableAlpha();
        GlStateManager.disableLighting();
        RenderUtils.enableFullBrightness();
        if (stack.hasCapability(PsioCaps.PSI_CELL, null)) {
            float charge = OptUtils.capability(stack, PsioCaps.PSI_CELL)
                    .map(c -> c.getStoredCharge() / (float)c.getMaxCharge()).orElse(0F);
            circleRender.render3d(x + 0.5D, y + circleAltitude, z + 0.5D, circleScale, charge);
            circleRender.tick(charge, partialTicks);
        } else {
            circleRender.reset();
            circleRender.render3d(x + 0.5D, y + circleAltitude, z + 0.5D, circleScale, 0F);
        }
        RenderUtils.restoreLightmap();
        GlStateManager.enableLighting();

        // render item
        if (!stack.isEmpty()) {
            float ticks = ClientTickHandler.getTick() + (float)(x + y + z) + partialTicks;
            GlStateManager.pushMatrix();
            GlStateManager.translate(
                    x + 0.5D, y + itemAltitude + itemAltitudeVariance * MathHelper.sin(ticks / 12F), z + 0.5D);
            GlStateManager.scale(itemScale, itemScale, itemScale);
            GlStateManager.rotate(ticks / 14F * MathUtils.R2D_F, 0F, 1F, 0F);
            mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            mc.getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
            GlStateManager.popMatrix();
        }
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
    }

}
