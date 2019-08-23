package xyz.phanta.psicosts.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.stream.Stream;

@FunctionalInterface
public interface InventoryProvider {

    Stream<ItemStack> provide(EntityPlayer player);

}
