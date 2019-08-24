package xyz.phanta.psicosts.tile;

import io.github.phantamanta44.libnine.capability.impl.L9AspectEnergy;
import io.github.phantamanta44.libnine.capability.impl.L9AspectSlot;
import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import io.github.phantamanta44.libnine.component.reservoir.RatedIntReservoir;
import io.github.phantamanta44.libnine.component.reservoir.SimpleIntReservoir;
import io.github.phantamanta44.libnine.tile.L9TileEntityTicking;
import io.github.phantamanta44.libnine.tile.RegisterTile;
import io.github.phantamanta44.libnine.util.data.serialization.AutoSerialize;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.capability.PsiCell;
import xyz.phanta.psicosts.init.PsioCaps;

import java.util.Objects;

@RegisterTile(Psio.MOD_ID)
public class TileFluxResonator extends L9TileEntityTicking {

    private static final int ENERGY_MAX = 80000;

    @AutoSerialize
    private final RatedIntReservoir energy = new RatedIntReservoir(new SimpleIntReservoir(ENERGY_MAX), 500, 250);
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
                .with(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inputSlot);
    }

    @Override
    protected void tick() {
        if (!world.isRemote && energy.getQuantity() > 0) {
            ItemStack stack = inputSlot.getStackInSlot();
            if (stack.hasCapability(PsioCaps.PSI_CELL, null)) {
                PsiCell cell = Objects.requireNonNull(stack.getCapability(PsioCaps.PSI_CELL, null));
                int qty = (int)Math.ceil((cell.getMaxCharge() - cell.getStoredCharge()) / PsioConfig.convForgeEnergy.ratio);
                if (qty > 0) {
                    qty = energy.draw(qty, true);
                    if (qty > 0) {
                        qty -= (int)Math.ceil(cell.injectCharge((int)Math.ceil(qty * PsioConfig.convForgeEnergy.ratio))
                                / PsioConfig.convForgeEnergy.ratio);
                        if (qty > 0) {
                            energy.offer(qty, true);
                        }
                    }
                }
            }
        }
    }

}
