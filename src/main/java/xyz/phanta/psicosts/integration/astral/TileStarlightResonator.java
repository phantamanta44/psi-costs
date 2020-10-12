package xyz.phanta.psicosts.integration.astral;

import hellfirepvp.astralsorcery.common.auxiliary.LiquidStarlightChaliceHandler;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import hellfirepvp.astralsorcery.common.tile.ILiquidStarlightPowered;
import hellfirepvp.astralsorcery.common.tile.TileChalice;
import io.github.phantamanta44.libnine.capability.impl.L9AspectFluidHandler;
import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import io.github.phantamanta44.libnine.component.reservoir.FluidReservoir;
import io.github.phantamanta44.libnine.component.reservoir.SimpleIntReservoir;
import io.github.phantamanta44.libnine.tile.RegisterTile;
import io.github.phantamanta44.libnine.util.data.serialization.AutoSerialize;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import vazkii.psi.common.Psi;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.tile.base.TileInWorldPsiCharger;

import java.util.List;

@RegisterTile(value = Psio.MOD_ID, deps = { IntegrationAstral.MOD_ID })
public class TileStarlightResonator extends TileInWorldPsiCharger implements ILiquidStarlightPowered {

    @AutoSerialize
    private final FluidReservoir tank = new FluidReservoir(
            BlocksAS.fluidLiquidStarlight, null, new SimpleIntReservoir(getBufferSizeInt()));

    public TileStarlightResonator() {
        super(PsioConfig.convAstralStarlight);
        tank.onQuantityChange((o, n) -> setDirty());
    }

    @Override
    protected CapabilityBroker initCapabilities() {
        return super.initCapabilities()
                .with(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, new L9AspectFluidHandler(true, tank));
    }

    public FluidReservoir getStarlightTank() {
        return tank;
    }

    @Override
    protected void tick() {
        if (world.isRemote) {
            float frac = getPsiProvider().getPsiEnergy() / (float)getPsiProvider().getPsiEnergyMax();
            if (frac > 0F) {
                Psi.proxy.wispFX(world,
                        pos.getX() + 0.5F + (float)world.rand.nextGaussian() * 0.05F,
                        pos.getY() + 0.1D,
                        pos.getZ() + 0.5F + (float)world.rand.nextGaussian() * 0.05F,
                        0.3F, 0.4F, 0.9F, 0.05F + frac * 0.15F, 0F, 0.025F, 0F);
            }
        } else if (world.rand.nextFloat() < 0.05F) {
            int missing = tank.getRemainingCapacity();
            if (missing > 0) {
                List<TileChalice> chalices = LiquidStarlightChaliceHandler
                        .findNearbyChalicesThatContain(this, new FluidStack(BlocksAS.fluidLiquidStarlight, 1));
                if (!chalices.isEmpty()) {
                    TileChalice chalice = chalices.get(world.rand.nextInt(chalices.size()));
                    LiquidStarlightChaliceHandler.requestLiquidStarlightAndTransferTo(
                            this, chalice, 0, Math.min(missing, chalice.getFluidAmount()));
                }
            }
        }
        genPsiFromIntReservoir(tank);
        super.tick();
    }

    @Override
    public void acceptStarlight(int qty) {
        tank.offer(qty, true);
    }

    @Override
    public boolean canAcceptStarlight(int qty) {
        return true;
    }

}
