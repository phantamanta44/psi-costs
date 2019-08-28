package xyz.phanta.psicosts.integration.thaumcraft;

import io.github.phantamanta44.libnine.component.reservoir.IIntReservoir;
import io.github.phantamanta44.libnine.component.reservoir.SimpleIntReservoir;
import io.github.phantamanta44.libnine.tile.RegisterTile;
import io.github.phantamanta44.libnine.util.data.serialization.AutoSerialize;
import net.minecraft.util.EnumFacing;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaTransport;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.tile.base.TilePsiCharger;

@RegisterTile(value = Psio.MOD_ID, deps = { IntegrationThaumcraft.MOD_ID })
public class TileEssentiaResonator extends TilePsiCharger implements IEssentiaTransport {

    private static final int SUCTION = 128;

    @AutoSerialize
    private final IIntReservoir essentia = new SimpleIntReservoir(getBufferSizeInt());

    public TileEssentiaResonator() {
        super(PsioConfig.convThaumEssentia);
        essentia.onQuantityChange((o, n) -> setDirty());
    }

    public IIntReservoir getEssentiaTank() {
        return essentia;
    }

    @Override
    protected void tick() {
        for (EnumFacing dir : EnumFacing.VALUES) {
            IEssentiaTransport pipe = (IEssentiaTransport)ThaumcraftApiHelper.getConnectableTile(world, pos, dir);
            if (pipe != null) {
                EnumFacing face = dir.getOpposite();
                int available = pipe.getEssentiaAmount(face);
                if (available > 0 && pipe.canOutputTo(face) && pipe.getSuctionAmount(face) < getSuctionAmount(dir)) {
                    essentia.offer(pipe.takeEssentia(
                            Aspect.ENERGY, Math.min(available, essentia.getRemainingCapacity()), face), true);
                }
            }
        }
        genPsiFromIntReservoir(essentia);
        super.tick();
    }

    @Override
    public boolean isConnectable(EnumFacing enumFacing) {
        return true;
    }

    @Override
    public boolean canInputFrom(EnumFacing enumFacing) {
        return true;
    }

    @Override
    public boolean canOutputTo(EnumFacing enumFacing) {
        return false;
    }

    @Override
    public void setSuction(Aspect aspect, int i) {
        // NO-OP
    }

    @Override
    public Aspect getSuctionType(EnumFacing enumFacing) {
        return Aspect.ENERGY;
    }

    @Override
    public int getSuctionAmount(EnumFacing enumFacing) {
        return essentia.getQuantity() < essentia.getCapacity() ? SUCTION : 0;
    }

    @Override
    public int takeEssentia(Aspect aspect, int i, EnumFacing enumFacing) {
        return 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int i, EnumFacing enumFacing) {
        return aspect == Aspect.ENERGY ? essentia.offer(i, true) : 0;
    }

    @Override
    public Aspect getEssentiaType(EnumFacing enumFacing) {
        return Aspect.ENERGY;
    }

    @Override
    public int getEssentiaAmount(EnumFacing enumFacing) {
        return essentia.getQuantity();
    }

    @Override
    public int getMinimumSuction() {
        return 0;
    }

}
