package xyz.phanta.psicosts.init;

import vazkii.psi.api.PsiAPI;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.spell.PieceTrickDrawEnergy;

public class PsioPieces {

    public static final String TRICK_DRAW_ENERGY = "psioTrickDrawEnergy";

    // no InitMe because botania registration is sensitive to the active mod container
    public static void init() {
        if (PsioConfig.trickDrawEnergyConfig.enabled) {
            PsiAPI.registerSpellPieceAndTexture(TRICK_DRAW_ENERGY, PieceTrickDrawEnergy.class);
        }
    }

}
