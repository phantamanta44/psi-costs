package xyz.phanta.psicosts.integration.botania;

import io.github.phantamanta44.libnine.LibNine;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.client.render.RenderInWorldPsiCharger;
import xyz.phanta.psicosts.integration.PsioIntegration;

@PsioIntegration.Register(IntegrationBotania.MOD_ID)
public class IntegrationBotania implements PsioIntegration {

    public static final String MOD_ID = "botania";

    @Override
    public void registerEntries() {
        if (PsioConfig.convBotaniaMana.enabled) {
            new BlockManaResonator();
        }
        LibNine.PROXY.getRegistrar().queueTESRReg(
                TileManaResonator.class, new RenderInWorldPsiCharger<>(0.91125D, 1.5F, 1.025D, 0.075D, 1.25F));
    }

}
