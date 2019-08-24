package xyz.phanta.psicosts.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import io.github.phantamanta44.libnine.client.model.ParameterizedItemModel;
import io.github.phantamanta44.libnine.item.L9ItemSubs;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import io.github.phantamanta44.libnine.util.nbt.ChainingTagCompound;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Optional;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.capability.PsiCell;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.init.PsioCaps;
import xyz.phanta.psicosts.init.PsioItems;
import xyz.phanta.psicosts.util.TooltipUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
public class ItemPsiCell extends L9ItemSubs implements ParameterizedItemModel.IParamaterized, IBauble {

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
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            for (Tier tier : Tier.VALUES) {
                items.add(tier.newStack(1));
                ItemStack full = tier.newStack(1);
                full.setTagCompound(new ChainingTagCompound().withInt("PsioCharge", tier.maxCharge));
                items.add(full);
            }
        }
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

    @Override
    public void getModelMutations(ItemStack stack, ParameterizedItemModel.Mutation m) {
        m.mutate("tier", Tier.getForStack(stack).name());
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.TRINKET;
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
        public int injectCharge(int amount) {
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
