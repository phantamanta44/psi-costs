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
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.tile.base.TilePsiCharger;

import java.util.List;

@RegisterTile(value = Psio.MOD_ID, deps = { IntegrationAstral.MOD_ID })
public class TileStarlightResonator extends TilePsiCharger implements ILiquidStarlightPowered {

    @AutoSerialize
    private final FluidReservoir tank = new FluidReservoir(
            BlocksAS.fluidLiquidStarlight, new SimpleIntReservoir(getBufferSizeInt()));

    public TileStarlightResonator() {
        super(PsioConfig.convAstralStarlight);
        tank.onQuantityChange((o, n) -> setDirty());
    }

    @Override
    protected CapabilityBroker initCapabilities() {
        return super.initCapabilities()
                .with(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, new L9AspectFluidHandler(tank));
    }

    public FluidReservoir getStarlightTank() {
        return tank;
    }

    @Override
    protected void tick() {
        if (!world.isRemote && world.rand.nextFloat() < 0.05F) {
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
        return tank.canFill();
    }

}
