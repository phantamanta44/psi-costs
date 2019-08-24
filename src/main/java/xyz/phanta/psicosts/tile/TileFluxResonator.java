package xyz.phanta.psicosts.tile;

import io.github.phantamanta44.libnine.capability.impl.L9AspectEnergy;
import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import io.github.phantamanta44.libnine.component.reservoir.IIntReservoir;
import io.github.phantamanta44.libnine.component.reservoir.SimpleIntReservoir;
import io.github.phantamanta44.libnine.tile.RegisterTile;
import io.github.phantamanta44.libnine.util.data.serialization.AutoSerialize;
import net.minecraftforge.energy.CapabilityEnergy;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.tile.base.TilePsiCharger;

@RegisterTile(Psio.MOD_ID)
public class TileFluxResonator extends TilePsiCharger {

    @AutoSerialize
    private final IIntReservoir energy = new SimpleIntReservoir((int)Math.ceil(getBufferSize()));

    public TileFluxResonator() {
        super(PsioConfig.convForgeEnergy);
        energy.onQuantityChange((o, n) -> setDirty());
    }

    @Override
    protected CapabilityBroker initCapabilities() {
        return super.initCapabilities().with(CapabilityEnergy.ENERGY, new L9AspectEnergy(energy));
    }

    public IIntReservoir getEnergyReservoir() {
        return energy;
    }

    @Override
    protected void tick() {
        if (!world.isRemote && psiCharge.isNotFull()) {
            int energyQty = energy.getQuantity();
            if (energyQty > 0) {
                energy.offsetQuantity(-(int)Math.ceil(psiCharge.offer(Math.min(energyQty, getConversionRate()))));
            }
        }
        super.tick();
    }

}
