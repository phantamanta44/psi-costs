package xyz.phanta.psicosts.integration.baubles;

import baubles.api.BaublesApi;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.integration.PsioIntegration;
import xyz.phanta.psicosts.util.InvUtils;

@PsioIntegration.Register("baubles")
public class IntegrationBaubles implements PsioIntegration {

    public IntegrationBaubles() {
        Psio.PROXY.getIntegrations().registerInvProvider(p -> InvUtils.streamInv(BaublesApi.getBaublesHandler(p)));
    }

}
