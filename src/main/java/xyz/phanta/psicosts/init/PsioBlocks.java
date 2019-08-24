package xyz.phanta.psicosts.init;

import io.github.phantamanta44.libnine.InitMe;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.block.BlockFluxResonator;

public class PsioBlocks {

    @InitMe(Psio.MOD_ID)
    public static void init() {
        if (PsioConfig.convForgeEnergy.enabled) {
            new BlockFluxResonator();
        }
    }

}
