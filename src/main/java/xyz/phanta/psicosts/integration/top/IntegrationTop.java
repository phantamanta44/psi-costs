package xyz.phanta.psicosts.integration.top;

import mcjty.theoneprobe.TheOneProbe;
import xyz.phanta.psicosts.integration.PsioIntegration;

@PsioIntegration.Register("theoneprobe")
public class IntegrationTop implements PsioIntegration {

    @Override
    public void lateRegister() {
        TheOneProbe.theOneProbeImp.registerProvider(new ProbeProviderPsiBlock());
    }

}
