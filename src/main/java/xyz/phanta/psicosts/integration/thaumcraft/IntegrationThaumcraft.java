package xyz.phanta.psicosts.integration.thaumcraft;

import io.github.phantamanta44.libnine.LibNine;
import io.github.phantamanta44.libnine.gui.GuiIdentity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.init.PsioGuis;
import xyz.phanta.psicosts.integration.PsioIntegration;

@PsioIntegration.Register(IntegrationThaumcraft.MOD_ID)
public class IntegrationThaumcraft implements PsioIntegration {

    public static final String MOD_ID = "thaumcraft";

    public static final GuiIdentity<ContainerEssentiaResonator, GuiEssentiaResonator> ESSENTIA_RESONATOR
            = new GuiIdentity<>(LangConst.INV_ESSENTIA_RESONATOR, ContainerEssentiaResonator.class);

    @Override
    public void registerEntries() {
        if (PsioConfig.convThaumEssentia.enabled) {
            new BlockEssentiaResonator();
        }
        LibNine.PROXY.getRegistrar().queueGuiServerReg(ESSENTIA_RESONATOR,
                (p, w, x, y, z) -> new ContainerEssentiaResonator(PsioGuis.getTile(w, x, y, z), p.inventory));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerEntriesClient() {
        LibNine.PROXY.getRegistrar().queueGuiClientReg(ESSENTIA_RESONATOR,
                (c, p, w, x, y, z) -> new GuiEssentiaResonator(c));
    }

}
