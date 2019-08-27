package xyz.phanta.psicosts.integration.bloodmagic;

import io.github.phantamanta44.libnine.LibNine;
import io.github.phantamanta44.libnine.gui.GuiIdentity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.init.PsioGuis;
import xyz.phanta.psicosts.integration.PsioIntegration;

@PsioIntegration.Register(IntegrationBloodMagic.MOD_ID)
public class IntegrationBloodMagic implements PsioIntegration {

    public static final String MOD_ID = "bloodmagic";

    public static final GuiIdentity<ContainerBloodResonator, GuiBloodResonator> BLOOD_RESONATOR
            = new GuiIdentity<>(LangConst.INV_BLOOD_RESONATOR, ContainerBloodResonator.class);

    @Override
    public void registerEntries() {
        if (PsioConfig.convBloodMagicLp.enabled) {
            new BlockBloodResonator();
        }
        LibNine.PROXY.getRegistrar().queueGuiServerReg(BLOOD_RESONATOR,
                (p, w, x, y, z) -> new ContainerBloodResonator(PsioGuis.getTile(w, x, y, z), p.inventory));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerEntriesClient() {
        LibNine.PROXY.getRegistrar().queueGuiClientReg(BLOOD_RESONATOR,
                (c, p, w, x, y, z) -> new GuiBloodResonator(c));
    }

}
