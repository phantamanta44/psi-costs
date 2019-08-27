package xyz.phanta.psicosts.integration.bloodmagic;

import io.github.phantamanta44.libnine.component.reservoir.FluidReservoir;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import xyz.phanta.psicosts.inventory.base.ContainerPsiCharger;

public class ContainerBloodResonator extends ContainerPsiCharger<TileBloodResonator> {

    public ContainerBloodResonator(TileBloodResonator tile, InventoryPlayer ipl) {
        super(tile, ipl);
        addSlotToContainer(new SlotItemHandler(tile.getBloodOrbSlot(), 0, 142, 62));
    }

    public FluidReservoir getBloodTank() {
        return tile.getBloodTank();
    }

    public ItemStack getBloodOrb() {
        return tile.getBloodOrbSlot().getStackInSlot();
    }

}
