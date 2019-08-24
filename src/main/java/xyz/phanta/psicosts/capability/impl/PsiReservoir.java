package xyz.phanta.psicosts.capability.impl;

import io.github.phantamanta44.libnine.component.reservoir.IIntReservoir;
import xyz.phanta.psicosts.capability.PsiProvider;

public class PsiReservoir implements PsiProvider {

    private final IIntReservoir backing;

    public PsiReservoir(IIntReservoir backing) {
        this.backing = backing;
    }

    @Override
    public int getPsiEnergy() {
        return backing.getQuantity();
    }

    @Override
    public int getPsiEnergyMax() {
        return backing.getCapacity();
    }

    @Override
    public int extractPsiEnergy(int amount, boolean mutate) {
        return backing.draw(amount, mutate);
    }

}
