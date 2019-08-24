package xyz.phanta.psicosts.capability;

public interface PsiProvider {

    int getPsiEnergy();

    int getPsiEnergyMax();

    int extractPsiEnergy(int amount, boolean mutate);

    class Impl implements PsiProvider {

        @Override
        public int getPsiEnergy() {
            return 0;
        }

        @Override
        public int getPsiEnergyMax() {
            return 0;
        }

        @Override
        public int extractPsiEnergy(int amount, boolean mutate) {
            return 0;
        }

    }

}
