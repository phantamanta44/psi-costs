package xyz.phanta.psicosts.integration.botania;

import io.github.phantamanta44.libnine.component.reservoir.IIntReservoir;
import io.github.phantamanta44.libnine.component.reservoir.SimpleIntReservoir;
import io.github.phantamanta44.libnine.tile.RegisterTile;
import io.github.phantamanta44.libnine.util.data.serialization.AutoSerialize;
import vazkii.botania.api.mana.IManaBlock;
import vazkii.botania.api.mana.IManaReceiver;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.tile.base.TilePsiCharger;

@RegisterTile(value = Psio.MOD_ID, deps = { "botania" })
public class TileManaResonator extends TilePsiCharger implements IManaBlock, IManaReceiver {

    @AutoSerialize
    private IIntReservoir mana = new SimpleIntReservoir(getBufferSizeInt());

    public TileManaResonator() {
        super(PsioConfig.convBotaniaMana);
        mana.onQuantityChange((o, n) -> setDirty());
    }

    public IIntReservoir getManaReservoir() {
        return mana;
    }

    @Override
    protected void tick() {
        genPsiFromIntReservoir(mana);
        super.tick();
    }

    @Override
    public boolean isFull() {
        return mana.getQuantity() == mana.getCapacity();
    }

    @Override
    public void recieveMana(int mana) {
        this.mana.offer(mana, true);
    }

    @Override
    public boolean canRecieveManaFromBursts() {
        return true;
    }

    @Override
    public int getCurrentMana() {
        return mana.getQuantity();
    }

}
