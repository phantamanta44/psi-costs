package xyz.phanta.psicosts.util;

import io.github.phantamanta44.libnine.capability.impl.L9AspectSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import xyz.phanta.psicosts.init.PsioCaps;

import java.util.Objects;

public class BlockInteractionUtil {

    public static boolean fillTankFromHand(TileEntity tile, int maxQty, EntityPlayer player, EnumHand hand) {
        if (maxQty > 0) {
            FluidActionResult result = FluidUtil.tryEmptyContainer(player.getHeldItem(hand),
                    Objects.requireNonNull(tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)),
                    maxQty, player, true);
            if (result.success) {
                if (!player.capabilities.isCreativeMode) {
                    player.setHeldItem(hand, result.result);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean placeCellFromHand(L9AspectSlot slot, EntityPlayer player, EnumHand hand) {
        ItemStack inSlot = slot.getStackInSlot();
        ItemStack held = player.getHeldItem(hand);
        if (inSlot.isEmpty()) {
            if (held.hasCapability(PsioCaps.PSI_CELL, null)) {
                player.setHeldItem(hand, ItemStack.EMPTY);
                slot.setStackInSlot(held);
                return true;
            }
        } else {
            slot.setStackInSlot(ItemStack.EMPTY);
            if (held.isEmpty()) {
                player.setHeldItem(hand, inSlot);
            } else {
                ItemHandlerHelper.giveItemToPlayer(player, inSlot);
            }
            return true;
        }
        return false;
    }

}
