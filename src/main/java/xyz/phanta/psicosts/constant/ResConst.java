package xyz.phanta.psicosts.constant;

import io.github.phantamanta44.libnine.util.render.TextureRegion;
import io.github.phantamanta44.libnine.util.render.TextureResource;
import net.minecraft.util.ResourceLocation;
import xyz.phanta.psicosts.Psio;

public class ResConst {

    private static final String TEX_KEY = "textures/";

    private static final String GUI_KEY = TEX_KEY + "gui/";
    public static final ResourceLocation GUI_PSI_CHARGER = Psio.INSTANCE.newResourceLocation(GUI_KEY + "psi_charger.png");
    public static final ResourceLocation GUI_BLOOD_RESONATOR = Psio.INSTANCE.newResourceLocation(GUI_KEY + "blood_resonator.png");

    private static final String GC_KEY = GUI_KEY + "component/";
    private static final TextureResource GC_PSI_BAR = Psio.INSTANCE.newTextureResource(GC_KEY + "psi_bar.png", 6, 72);
    public static final TextureRegion GC_PSI_BAR_BG = GC_PSI_BAR.getRegion(0, 0, 4, 72);
    public static final TextureRegion GC_PSI_BAR_FG = GC_PSI_BAR.getRegion(4, 0, 2, 70);
    private static final TextureResource GC_ENERGY_BAR = Psio.INSTANCE.newTextureResource(GC_KEY + "energy_bar.png", 6, 72);
    public static final TextureRegion GC_ENERGY_BAR_BG = GC_ENERGY_BAR.getRegion(0, 0, 4, 72);
    public static final TextureRegion GC_ENERGY_BAR_FG = GC_ENERGY_BAR.getRegion(4, 0, 2, 70);
    private static final TextureResource GC_STARLIGHT_BAR = Psio.INSTANCE.newTextureResource(GC_KEY + "starlight_bar.png", 6, 72);
    public static final TextureRegion GC_STARLIGHT_BAR_BG = GC_STARLIGHT_BAR.getRegion(0, 0, 4, 72);
    public static final TextureRegion GC_STARLIGHT_BAR_FG = GC_STARLIGHT_BAR.getRegion(4, 0, 2, 70);
    private static final TextureResource GC_BLOOD_BAR = Psio.INSTANCE.newTextureResource(GC_KEY + "blood_bar.png", 6, 72);
    public static final TextureRegion GC_BLOOD_BAR_BG = GC_BLOOD_BAR.getRegion(0, 0, 4, 72);
    public static final TextureRegion GC_BLOOD_BAR_FG = GC_BLOOD_BAR.getRegion(4, 0, 2, 70);

    public static final TextureRegion[] EXT_PSI_SPELL_CIRCLE
            = { getSpellCircle("0"), getSpellCircle("1"), getSpellCircle("2") };

    private static TextureRegion getSpellCircle(String suffix) {
        return new TextureResource(getPsiResource("textures/misc/spell_circle" + suffix + ".png"), 64, 64)
                .getRegion(0, 0, 64, 64);
    }

    private static ResourceLocation getPsiResource(String path) {
        return new ResourceLocation("psi", path);
    }

}
