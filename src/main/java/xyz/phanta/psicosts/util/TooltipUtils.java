package xyz.phanta.psicosts.util;

import io.github.phantamanta44.libnine.util.format.FormatUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import xyz.phanta.psicosts.capability.PsiCell;
import xyz.phanta.psicosts.init.PsioCaps;

import java.util.Objects;

public class TooltipUtils {

    public static String formatPsiEnergy(ItemStack stack) {
        return formatPsiEnergy(Objects.requireNonNull(stack.getCapability(PsioCaps.PSI_CELL, null)));
    }

    public static String formatPsiEnergy(PsiCell cell) {
        return TextFormatting.AQUA + FormatUtils.formatSI(cell.getStoredCharge(), "PSI")
                + TextFormatting.GRAY + " / "
                + TextFormatting.AQUA + FormatUtils.formatSI(cell.getMaxCharge(), "PSI");
    }

    public static String formatInfo(String key) {
        return TextFormatting.GRAY + I18n.format(key);
    }

}
