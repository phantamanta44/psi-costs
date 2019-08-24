package xyz.phanta.psicosts.tile.base;

import io.github.phantamanta44.libnine.capability.impl.L9AspectSlot;
import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import io.github.phantamanta44.libnine.tile.L9TileEntityTicking;
import io.github.phantamanta44.libnine.util.data.serialization.AutoSerialize;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import net.minecraftforge.items.CapabilityItemHandler;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.capability.PsiCell;
import xyz.phanta.psicosts.capability.PsiProvider;
import xyz.phanta.psicosts.component.PsiCharger;
import xyz.phanta.psicosts.init.PsioCaps;

public class TilePsiCharger extends L9TileEntityTicking {

    @AutoSerialize
    protected final PsiCharger psiCharge;
    @AutoSerialize
    private final L9AspectSlot inputSlot = new L9AspectSlot.Observable(
            s -> OptUtils.capability(s, PsioCaps.PSI_CELL).map(PsiCell::canReceiveCharge).orElse(false),
            (i, o, n) -> setDirty());

    private final PsioConfig.ConversionConfig conversion;
    private final PsiProvider psiProvider;

    public TilePsiCharger(PsioConfig.ConversionConfig conversion) {
        this.conversion = conversion;
        this.psiCharge = new PsiCharger(conversion, this::setDirty);
        this.psiProvider = psiCharge.createCapabilityPsi();
        markRequiresSync();
        setInitialized();
    }

    @Override
    protected CapabilityBroker initCapabilities() {
        return new CapabilityBroker()
                .with(PsioCaps.PSI_PROVIDER, psiProvider)
                .with(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inputSlot);
    }

    public L9AspectSlot getInputSlot() {
        return inputSlot;
    }

    public PsiProvider getPsiProvider() {
        return psiProvider;
    }

    @Override
    protected void tick() {
        if (!world.isRemote) {
            psiCharge.tryCharge(inputSlot.getStackInSlot());
        }
    }

    protected double getBufferSize() {
        return conversion.psiBuffer / conversion.ratio;
    }

    protected double getConversionRate() {
        return conversion.maxConversionRate / conversion.ratio;
    }

}
