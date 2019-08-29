package xyz.phanta.psicosts.integration.astral;

import io.github.phantamanta44.libnine.LibNine;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.client.render.RenderInWorldPsiCharger;
import xyz.phanta.psicosts.integration.PsioIntegration;

@PsioIntegration.Register(IntegrationAstral.MOD_ID)
public class IntegrationAstral implements PsioIntegration {

    public static final String MOD_ID = "astralsorcery";

    @Override
    public void registerEntries() {
        if (PsioConfig.convAstralStarlight.enabled) {
            new BlockStarlightResonator();
        }
        LibNine.PROXY.getRegistrar().queueTESRReg(
                TileStarlightResonator.class, new RenderInWorldPsiCharger<>(0.875D, 1.25F, 0.975D, 0.05D, 1.25F));
    }

}
