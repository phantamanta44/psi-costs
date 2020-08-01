package xyz.phanta.psicosts.init;

import io.github.phantamanta44.libnine.InitMe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.item.ItemCreativeCell;
import xyz.phanta.psicosts.item.ItemPsiCell;
import xyz.phanta.psicosts.item.ItemPsiHypoStim;

public class PsioItems {

    @GameRegistry.ObjectHolder(Psio.MOD_ID + ":" + LangConst.ITEM_PSI_CELL)
    public static ItemPsiCell PSI_CELL;
    @GameRegistry.ObjectHolder(Psio.MOD_ID + ":" + LangConst.ITEM_CREATIVE_CELL)
    public static ItemCreativeCell CREATIVE_CELL;
    @GameRegistry.ObjectHolder(Psio.MOD_ID + ":" + LangConst.ITEM_PSI_HYPOSTIM)
    public static ItemPsiHypoStim PSI_HYPOSTIM;

    @InitMe(Psio.MOD_ID)
    public static void init() {
        new ItemPsiCell();
        new ItemCreativeCell();
        new ItemPsiHypoStim();
    }

}
