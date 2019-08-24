package xyz.phanta.psicosts.inventory;

import io.github.phantamanta44.libnine.component.reservoir.IIntReservoir;
import net.minecraft.entity.player.InventoryPlayer;
import xyz.phanta.psicosts.inventory.base.ContainerPsiCharger;
import xyz.phanta.psicosts.tile.TileFluxResonator;

public class ContainerFluxResonator extends ContainerPsiCharger<TileFluxResonator> {

    public ContainerFluxResonator(TileFluxResonator tile, InventoryPlayer ipl) {
        super(tile, ipl);
    }

    public IIntReservoir getEnergyReservoir() {
        return tile.getEnergyReservoir();
    }

}
