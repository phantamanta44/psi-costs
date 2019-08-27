package xyz.phanta.psicosts.integration.astral;

import io.github.phantamanta44.libnine.LibNine;
import io.github.phantamanta44.libnine.gui.GuiIdentity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.init.PsioGuis;
import xyz.phanta.psicosts.integration.PsioIntegration;

@PsioIntegration.Register(IntegrationAstral.MOD_ID)
public class IntegrationAstral implements PsioIntegration {

    public static final String MOD_ID = "astralsorcery";

    public static final GuiIdentity<ContainerStarlightResonator, GuiStarlightResonator> STARLIGHT_RESONATOR
            = new GuiIdentity<>(LangConst.INV_STARLIGHT_RESONATOR, ContainerStarlightResonator.class);

    @Override
    public void registerEntries() {
        if (PsioConfig.convAstralStarlight.enabled) {
            new BlockStarlightResonator();
        }
        LibNine.PROXY.getRegistrar().queueGuiServerReg(STARLIGHT_RESONATOR,
                (p, w, x, y, z) -> new ContainerStarlightResonator(PsioGuis.getTile(w, x, y, z), p.inventory));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerEntriesClient() {
        LibNine.PROXY.getRegistrar().queueGuiClientReg(STARLIGHT_RESONATOR,
                (c, p, w, x, y, z) -> new GuiStarlightResonator(c));
    }

}
