package xyz.phanta.psicosts.integration.botania;

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
import xyz.phanta.psicosts.util.MagicCircleRender;

public class RenderManaResonator extends TileEntitySpecialRenderer<TileManaResonator> {

    @Override
    public void render(TileManaResonator tile, double x, double y, double z, float partialTicks,
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
            circleRender.render3d(x + 0.5D, y + 0.91125D, z + 0.5D, 1.5F, charge);
            circleRender.tick(charge, partialTicks);
        } else {
            circleRender.reset();
            circleRender.render3d(x + 0.5D, y + 0.91125D, z + 0.5D, 1.5F, 0F);
        }
        RenderUtils.restoreLightmap();
        GlStateManager.enableLighting();

        // render item
        if (!stack.isEmpty()) {
            float ticks = ClientTickHandler.getTick() + (float)(x + y + z) + partialTicks;
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5D, y + 1.025D + 0.075D * MathHelper.sin(ticks / 12F), z + 0.5D);
            GlStateManager.scale(1.25F, 1.25F, 1.25F);
            GlStateManager.rotate(ticks / 14F * MathUtils.R2D_F, 0F, 1F, 0F);
            mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            mc.getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
            GlStateManager.popMatrix();
        }
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
    }

}
