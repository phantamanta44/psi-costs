package xyz.phanta.psicosts.constant;

import xyz.phanta.psicosts.Psio;

public class LangConst {

    public static final String ITEM_PSI_CELL = "psi_cell";
    public static final String ITEM_CREATIVE_CELL = "creative_cell";
    public static final String ITEM_PSI_HYPOSTIM = "psi_hypostim";

    public static final String BLOCK_FLUX_RESONATOR = "flux_resonator";

    public static final String INV_FLUX_RESONATOR = BLOCK_FLUX_RESONATOR;

    private static final String MISC_KEY = Psio.MOD_ID + ".misc.";

    private static final String GUI_KEY = MISC_KEY + "gui.";
    public static final String GUI_FLUX_RESONATOR = GUI_KEY + INV_FLUX_RESONATOR;

    private static final String TT_KEY = MISC_KEY + "tooltip.";
    public static final String TT_CREATIVE_ONLY = TT_KEY + "creative_only";
    public static final String TT_CELL = TT_KEY + "cell";
    public static final String TT_CREATIVE_CELL = TT_KEY + "creative_cell";
    public static final String TT_HYPOSTIM = TT_KEY + "hypostim";

}
