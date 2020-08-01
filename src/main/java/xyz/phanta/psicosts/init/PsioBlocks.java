package xyz.phanta.psicosts.init;

import io.github.phantamanta44.libnine.InitMe;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.block.BlockFluxResonator;
import xyz.phanta.psicosts.block.BlockMaterialGlassy;
import xyz.phanta.psicosts.constant.LangConst;

public class PsioBlocks {

    @GameRegistry.ObjectHolder(Psio.MOD_ID + ":" + LangConst.BLOCK_MATERIAL_GLASSY)
    public static Block MATERIAL_GLASSY;

    @InitMe(Psio.MOD_ID)
    public static void init() {
        new BlockMaterialGlassy();
        if (PsioConfig.convForgeEnergy.enabled) {
            new BlockFluxResonator();
        }
    }

}
