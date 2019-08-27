package xyz.phanta.psicosts.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import java.util.Objects;

public class FluidTankUtil {

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

}
