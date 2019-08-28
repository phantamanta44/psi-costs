package xyz.phanta.psicosts.constant;

import xyz.phanta.psicosts.Psio;

public class LangConst {

    public static final String ITEM_PSI_CELL = "psi_cell";
    public static final String ITEM_CREATIVE_CELL = "creative_cell";
    public static final String ITEM_PSI_HYPOSTIM = "psi_hypostim";

    public static final String BLOCK_FLUX_RESONATOR = "flux_resonator";
    public static final String BLOCK_MANA_RESONATOR = "mana_resonator";
    public static final String BLOCK_STARLIGHT_RESONATOR = "starlight_resonator";
    public static final String BLOCK_BLOOD_RESONATOR = "blood_resonator";
    public static final String BLOCK_EMBER_RESONATOR = "ember_resonator";
    public static final String BLOCK_ESSENTIA_RESONATOR = "essentia_resonator";

    public static final String INV_FLUX_RESONATOR = BLOCK_FLUX_RESONATOR;
    public static final String INV_STARLIGHT_RESONATOR = BLOCK_STARLIGHT_RESONATOR;
    public static final String INV_BLOOD_RESONATOR = BLOCK_BLOOD_RESONATOR;
    public static final String INV_ESSENTIA_RESONATOR = BLOCK_ESSENTIA_RESONATOR;

    public static final String MISC_KEY = Psio.MOD_ID + ".misc.";

    private static final String GUI_KEY = MISC_KEY + "gui.";
    public static final String GUI_FLUX_RESONATOR = GUI_KEY + INV_FLUX_RESONATOR;
    public static final String GUI_STARLIGHT_RESONATOR = GUI_KEY + INV_STARLIGHT_RESONATOR;
    public static final String GUI_BLOOD_RESONATOR = GUI_KEY + INV_BLOOD_RESONATOR;
    public static final String GUI_ESSENTIA_RESONATOR = GUI_KEY + INV_ESSENTIA_RESONATOR;

    private static final String TT_KEY = MISC_KEY + "tooltip.";
    public static final String TT_CREATIVE_ONLY = TT_KEY + "creative_only";
    public static final String TT_CELL = TT_KEY + "cell";
    public static final String TT_CREATIVE_CELL = TT_KEY + "creative_cell";
    public static final String TT_HYPOSTIM = TT_KEY + "hypostim";

    private static final String TT_CHARGER_KEY = TT_KEY + "charger.";
    public static final String TT_CHARGER_FORGE_ENERGY = TT_CHARGER_KEY + "forge_energy";
    public static final String TT_CHARGER_BOTANIA_MANA = TT_CHARGER_KEY + "botania_mana";
    public static final String TT_CHARGER_ASTRAL_STARLIGHT = TT_CHARGER_KEY + "astral_starlight";
    public static final String TT_CHARGER_BLOOD_MAGIC_LP = TT_CHARGER_KEY + "bloodmagic_lp";
    public static final String TT_CHARGER_EMBERS_EMBER = TT_CHARGER_KEY + "embers_ember";
    public static final String TT_CHARGER_THAUM_ESSENTIA = TT_CHARGER_KEY + "thaum_essentia";

}
