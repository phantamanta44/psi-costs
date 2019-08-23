package xyz.phanta.psicosts.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InvUtils {

    public static Stream<ItemStack> streamInv(IItemHandler inv) {
        return IntStream.range(0, inv.getSlots()).mapToObj(inv::getStackInSlot);
    }

}
