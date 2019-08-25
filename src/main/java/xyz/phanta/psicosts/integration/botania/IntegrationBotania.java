package xyz.phanta.psicosts.integration.botania;

import xyz.phanta.psicosts.integration.PsioIntegration;

@PsioIntegration.Register("botania")
public class IntegrationBotania implements PsioIntegration {

    @Override
    public void registerEntries() {
        new BlockManaResonator();
    }

}
