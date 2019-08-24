package xyz.phanta.psicosts.init;

import io.github.phantamanta44.libnine.InitMe;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.item.ItemCreativeCell;
import xyz.phanta.psicosts.item.ItemPsiCell;
import xyz.phanta.psicosts.item.ItemPsiHypoStim;

@SuppressWarnings("NullableProblems")
public class PsioItems {

    public static ItemPsiCell PSI_CELL;
    public static ItemCreativeCell CREATIVE_CELL;
    public static ItemPsiHypoStim PSI_HYPOSTIM;

    @InitMe(Psio.MOD_ID)
    public static void init() {
        PSI_CELL = new ItemPsiCell();
        CREATIVE_CELL = new ItemCreativeCell();
        PSI_HYPOSTIM = new ItemPsiHypoStim();
    }

}
