package xyz.phanta.psicosts.item;

import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import io.github.phantamanta44.libnine.item.L9ItemSubs;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
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
import xyz.phanta.psicosts.init.PsioItems;
import xyz.phanta.psicosts.util.TooltipUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class ItemPsiCell extends L9ItemSubs {

    public ItemPsiCell() {
        super(LangConst.ITEM_PSI_CELL, Tier.VALUES.length);
        setMaxStackSize(1);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new CapabilityBroker().with(PsioCaps.PSI_CELL, new RechargableCell(stack));
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1F - Objects.requireNonNull(stack.getCapability(PsioCaps.PSI_CELL, null)).getPercentCharge();
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return 0x01bffe;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TooltipUtils.formatPsiEnergy(stack));
        tooltip.add(TooltipUtils.formatInfo(LangConst.TT_CELL));
    }

    public enum Tier {

        TIER_1(PsioConfig.psiCellConfig.tier1Capacity),
        TIER_2(PsioConfig.psiCellConfig.tier2Capacity),
        TIER_3(PsioConfig.psiCellConfig.tier3Capacity),
        TIER_4(PsioConfig.psiCellConfig.tier4Capacity);

        public static final Tier[] VALUES = values();

        public static Tier getForMeta(int meta) {
            return VALUES[meta];
        }

        public static Tier getForStack(ItemStack stack) {
            return getForMeta(stack.getMetadata());
        }

        public final int maxCharge;

        Tier(int maxCharge) {
            this.maxCharge = maxCharge;
        }

        public ItemStack newStack(int count) {
            return new ItemStack(PsioItems.PSI_CELL, count, ordinal());
        }

    }

    private static class RechargableCell implements PsiCell {

        private final ItemStack stack;

        public RechargableCell(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public int getStoredCharge() {
            return OptUtils.stackTag(stack).map(t -> t.getInteger("PsioCharge")).orElse(0);
        }

        @Override
        public int getMaxCharge() {
            return Tier.getForStack(stack).maxCharge;
        }

        @Override
        public int extractCharge(int amount, EntityPlayer player) {
            int stored = getStoredCharge();
            int toTransfer = Math.min(amount, stored);
            getTag().setInteger("PsioCharge", stored - toTransfer);
            return toTransfer;
        }

        @Override
        public int injectCharge(int amount, EntityPlayer player) {
            int stored = getStoredCharge();
            int toTransfer = Math.min(amount, getMaxCharge() - stored);
            getTag().setInteger("PsioCharge", stored + toTransfer);
            return toTransfer;
        }

        private NBTTagCompound getTag() {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag == null) {
                tag = new NBTTagCompound();
                tag.setInteger("PsioCharge", 0);
                stack.setTagCompound(tag);
            }
            return tag;
        }

    }

}
