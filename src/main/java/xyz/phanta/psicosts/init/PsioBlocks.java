package xyz.phanta.psicosts.init;

import io.github.phantamanta44.libnine.InitMe;
import net.minecraft.block.Block;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.block.BlockFluxResonator;
import xyz.phanta.psicosts.block.BlockMaterialGlassy;

@SuppressWarnings("NullableProblems")
public class PsioBlocks {

    public static Block MATERIAL_GLASSY;

    @InitMe(Psio.MOD_ID)
    public static void init() {
        MATERIAL_GLASSY = new BlockMaterialGlassy();
        if (PsioConfig.convForgeEnergy.enabled) {
            new BlockFluxResonator();
        }
    }

}
