package xyz.phanta.psicosts.tile;

import io.github.phantamanta44.libnine.capability.impl.L9AspectEnergy;
import io.github.phantamanta44.libnine.capability.impl.L9AspectSlot;
import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import io.github.phantamanta44.libnine.component.reservoir.IIntReservoir;
import io.github.phantamanta44.libnine.component.reservoir.SimpleIntReservoir;
import io.github.phantamanta44.libnine.tile.L9TileEntityTicking;
import io.github.phantamanta44.libnine.tile.RegisterTile;
import io.github.phantamanta44.libnine.util.data.serialization.AutoSerialize;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.capability.PsiCell;
import xyz.phanta.psicosts.component.PsiCharger;
import xyz.phanta.psicosts.init.PsioCaps;

@RegisterTile(Psio.MOD_ID)
public class TileFluxResonator extends L9TileEntityTicking {

    @AutoSerialize
    private final IIntReservoir energy = new SimpleIntReservoir(
            (int)Math.ceil(PsioConfig.convForgeEnergy.psiBuffer / PsioConfig.convForgeEnergy.ratio));
    @AutoSerialize
    private final PsiCharger psiCharge = new PsiCharger(PsioConfig.convForgeEnergy, this::setDirty);
    @AutoSerialize
    private final L9AspectSlot inputSlot = new L9AspectSlot.Observable(
            s -> OptUtils.capability(s, PsioCaps.PSI_CELL).map(PsiCell::canReceiveCharge).orElse(false),
            (i, o, n) -> setDirty());

    public TileFluxResonator() {
        energy.onQuantityChange((o, n) -> setDirty());
        markRequiresSync();
        setInitialized();
    }

    @Override
    protected ICapabilityProvider initCapabilities() {
        return new CapabilityBroker()
                .with(CapabilityEnergy.ENERGY, new L9AspectEnergy(energy))
                .with(PsioCaps.PSI_PROVIDER, psiCharge.createCapabilityPsi())
                .with(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inputSlot);
    }

    @Override
    protected void tick() {
        if (!world.isRemote) {
            if (psiCharge.isNotFull()) {
                int energyQty = energy.getQuantity();
                if (energyQty > 0) {
                    energy.offsetQuantity(-(int)Math.ceil(psiCharge.offer(Math.min(energyQty, getConversionRate()))));
                }
            }
            psiCharge.tryCharge(inputSlot.getStackInSlot());
        }
    }

    private static double getConversionRate() {
        return PsioConfig.convForgeEnergy.maxConversionRate / PsioConfig.convForgeEnergy.ratio;
    }

}
