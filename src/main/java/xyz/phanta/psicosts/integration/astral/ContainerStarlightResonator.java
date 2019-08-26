package xyz.phanta.psicosts.integration.astral;

import io.github.phantamanta44.libnine.component.reservoir.FluidReservoir;
import net.minecraft.entity.player.InventoryPlayer;
import xyz.phanta.psicosts.inventory.base.ContainerPsiCharger;

public class ContainerStarlightResonator extends ContainerPsiCharger<TileStarlightResonator> {

    public ContainerStarlightResonator(TileStarlightResonator tile, InventoryPlayer ipl) {
        super(tile, ipl);
    }

    public FluidReservoir getStarlightTank() {
        return tile.getStarlightTank();
    }

}
