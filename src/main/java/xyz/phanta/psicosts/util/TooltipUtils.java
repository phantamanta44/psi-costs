package xyz.phanta.psicosts.util;

import io.github.phantamanta44.libnine.util.format.FormatUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import xyz.phanta.psicosts.capability.PsiCell;
import xyz.phanta.psicosts.capability.PsiProvider;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.init.PsioCaps;

import java.util.Objects;

public class TooltipUtils {

    public static String formatPsiEnergy(ItemStack stack) {
        return formatPsiEnergy(Objects.requireNonNull(stack.getCapability(PsioCaps.PSI_CELL, null)));
    }

    public static String formatPsiEnergy(PsiCell cell) {
        return formatPsiEnergy(cell.getStoredCharge(), cell.getMaxCharge());
    }

    public static String formatPsiEnergy(PsiProvider psi) {
        return formatPsiEnergy(psi.getPsiEnergy(), psi.getPsiEnergyMax());
    }

    public static String formatPsiEnergy(int stored, int max) {
        return formatFraction(stored, max, "PSI");
    }

    public static String formatFraction(int num, int denom, String unit) {
        return TextFormatting.AQUA + FormatUtils.formatSI(num, unit)
                + TextFormatting.GRAY + " / " + TextFormatting.AQUA + FormatUtils.formatSI(denom, unit);
    }

    public static String formatInfo(String key) {
        return TextFormatting.GRAY + I18n.format(key);
    }

    public static String getCreativeMarker() {
        return TextFormatting.DARK_GRAY + I18n.format(LangConst.TT_CREATIVE_ONLY);
    }

}
