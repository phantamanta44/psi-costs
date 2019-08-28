package xyz.phanta.psicosts.integration.thaumcraft;

import io.github.phantamanta44.libnine.component.reservoir.IIntReservoir;
import net.minecraft.entity.player.InventoryPlayer;
import xyz.phanta.psicosts.inventory.base.ContainerPsiCharger;

public class ContainerEssentiaResonator extends ContainerPsiCharger<TileEssentiaResonator> {

    public ContainerEssentiaResonator(TileEssentiaResonator tile, InventoryPlayer ipl) {
        super(tile, ipl);
    }

    public IIntReservoir getEssentiaTank() {
        return tile.getEssentiaTank();
    }

}
