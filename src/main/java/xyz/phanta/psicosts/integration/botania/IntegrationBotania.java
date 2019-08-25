package xyz.phanta.psicosts.integration.botania;

import io.github.phantamanta44.libnine.LibNine;
import xyz.phanta.psicosts.integration.PsioIntegration;

@PsioIntegration.Register("botania")
public class IntegrationBotania implements PsioIntegration {

    @Override
    public void registerEntries() {
        new BlockManaResonator();
        LibNine.PROXY.getRegistrar().queueTESRReg(TileManaResonator.class, new RenderManaResonator());
    }

}
