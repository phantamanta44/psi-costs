package xyz.phanta.psicosts.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import io.github.phantamanta44.libnine.item.L9Item;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Optional;
import xyz.phanta.psicosts.capability.PsiCell;
import xyz.phanta.psicosts.constant.LangConst;
import xyz.phanta.psicosts.init.PsioCaps;
import xyz.phanta.psicosts.util.TooltipUtils;

import javax.annotation.Nullable;
import java.util.List;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles", striprefs = true)
public class ItemCreativeCell extends L9Item implements IBauble {

    public ItemCreativeCell() {
        super(LangConst.ITEM_CREATIVE_CELL);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new CapabilityBroker().with(PsioCaps.PSI_CELL, new CreativeCell());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TooltipUtils.formatInfo(LangConst.TT_CREATIVE_CELL));
        tooltip.add(TooltipUtils.getCreativeMarker());
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.TRINKET;
    }

    private static class CreativeCell implements PsiCell {

        @Override
        public int getStoredCharge() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getMaxCharge() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int extractCharge(int amount, EntityPlayer player) {
            return amount;
        }

        @Override
        public boolean canReceiveCharge() {
            return false;
        }

        @Override
        public int injectCharge(int amount) {
            return 0;
        }

        @Override
        public void setCharge(int amount) {
            // NO-OP
        }

    }

}
