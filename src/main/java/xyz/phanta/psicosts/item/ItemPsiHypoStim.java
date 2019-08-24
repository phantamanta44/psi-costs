package xyz.phanta.psicosts.item;

import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import io.github.phantamanta44.libnine.item.L9Item;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.capability.PsiCell;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.init.PsioCaps;
import xyz.phanta.psicosts.util.TooltipUtils;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPsiHypoStim extends L9Item {

    public ItemPsiHypoStim() {
        super(LangConst.ITEM_PSI_HYPOSTIM);
        setMaxStackSize(1);
        setMaxDamage(PsioConfig.psiStimCapacity - 1);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new CapabilityBroker().with(PsioCaps.PSI_CELL, new HypoStim(stack));
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return 0x01bffe;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TooltipUtils.formatPsiEnergy(stack));
        tooltip.add(TooltipUtils.formatInfo(LangConst.TT_HYPOSTIM));
    }

    private static class HypoStim implements PsiCell {

        private final ItemStack stack;

        HypoStim(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public int getStoredCharge() {
            return PsioConfig.psiStimCapacity - stack.getItemDamage();
        }

        @Override
        public int getMaxCharge() {
            return PsioConfig.psiStimCapacity;
        }

        @Override
        public int extractCharge(int amount, EntityPlayer player) {
            int toTransfer = Math.min(amount, getStoredCharge());
            stack.damageItem(toTransfer, player);
            return toTransfer;
        }

        @Override
        public boolean canReceiveCharge() {
            return false;
        }

        @Override
        public int injectCharge(int amount) {
            return 0;
        }

    }

}
