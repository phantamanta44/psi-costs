package xyz.phanta.psicosts.util;

import io.github.phantamanta44.libnine.client.event.ClientTickHandler;
import io.github.phantamanta44.libnine.util.format.TextFormatUtils;
import io.github.phantamanta44.libnine.util.render.TextureRegion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import xyz.phanta.psicosts.constant.ResConst;

import java.awt.Color;

public interface MagicCircleRender {

    void render2d(float x, float y, float charge);

    void render3d(double x, double y, double z, float scale, float charge);

    void tick(float charge, float partialTicks);

    void reset();

    class Impl implements MagicCircleRender {

        private final float[] angles = { 0F, 0F, 0F };
        private float hue = 0F;
        private long lastUpdate = ClientTickHandler.getTick();

        @Override
        public void render2d(float x, float y, float charge) {
            TextFormatUtils.setGlColour(Color.HSBtoRGB(hue, charge, 0.5F + 0.5F * charge), 1F);
            drawRing2d(ResConst.EXT_PSI_SPELL_CIRCLE[0], x, y, angles[0], 1);
            drawRing2d(ResConst.EXT_PSI_SPELL_CIRCLE[1], x, y, angles[1], -1);
            drawRing2d(ResConst.EXT_PSI_SPELL_CIRCLE[2], x, y, angles[2], 1);
            GlStateManager.color(1F, 1F, 1F, 1F);
        }

        private static void drawRing2d(TextureRegion tex, float x, float y, float angle, float sign) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(88D + x, 43D + y, 0D);
            GlStateManager.rotate(angle * sign, 0F, 0F, 1F);
            tex.draw(-32, -32, 64, 64);
            GlStateManager.popMatrix();
        }

        @Override
        public void render3d(double x, double y, double z, float scale, float charge) {
            scale /= 2F;
            TextFormatUtils.setGlColour(Color.HSBtoRGB(hue, charge, 0.5F + 0.5F * charge), 1F);
            Tessellator tess = Tessellator.getInstance();
            BufferBuilder buf = tess.getBuffer();
            drawRing3d(ResConst.EXT_PSI_SPELL_CIRCLE[0], tess, buf, x, y, z, scale, angles[0], 1);
            drawRing3d(ResConst.EXT_PSI_SPELL_CIRCLE[1], tess, buf, x, y + 0.025D * scale, z, scale, angles[1], -1);
            drawRing3d(ResConst.EXT_PSI_SPELL_CIRCLE[2], tess, buf, x, y + 0.05D * scale, z, scale, angles[2], 1);
            GlStateManager.color(1F, 1F, 1F, 1F);
        }

        private static void drawRing3d(TextureRegion tex, Tessellator tess, BufferBuilder buf,
                                       double x, double y, double z, float scale, float angle, float sign) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y, z);
            GlStateManager.rotate(angle * sign, 0F, 1F, 0F);
            tex.getTexture().bind();
            buf.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_TEX);
            buf.pos(-scale, 0D, -scale).tex(0, 0).endVertex();
            buf.pos(-scale, 0D, scale).tex(0, 1).endVertex();
            buf.pos(scale, 0D, -scale).tex(1, 0).endVertex();
            buf.pos(scale, 0D, scale).tex(1, 1).endVertex();
            buf.pos(-scale, 0D, -scale).tex(0, 0).endVertex();
            buf.pos(-scale, 0D, scale).tex(0, 1).endVertex();
            tess.draw();
            GlStateManager.popMatrix();
        }

        @Override
        public void tick(float charge, float partialTicks) {
            long now = ClientTickHandler.getTick();
            float dt = (float)(now - lastUpdate) + partialTicks;
            lastUpdate = now;
            hue += charge * dt / 90F;
            for (int i = 0; i < 3; i++) {
                angles[i] += charge * dt * (i + 1);
            }
        }

        @Override
        public void reset() {
            hue = angles[0] = angles[1] = angles[2] = 0F;
        }

    }

    class Noop implements MagicCircleRender {

        @Override
        public void render2d(float x, float y, float charge) {
            // NO-OP
        }

        @Override
        public void render3d(double x, double y, double z, float scale, float charge) {
            // NO-OP
        }

        @Override
        public void tick(float charge, float partialTicks) {
            // NO-OP
        }

        @Override
        public void reset() {
            // NO-OP
        }

    }

}
