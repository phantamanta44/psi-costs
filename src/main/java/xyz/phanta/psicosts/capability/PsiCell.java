package xyz.phanta.psicosts.capability;

import net.minecraft.entity.player.EntityPlayer;

public interface PsiCell {

    int getStoredCharge();

    int getMaxCharge();

    default float getPercentCharge() {
        return getStoredCharge() / (float)getMaxCharge();
    }

    int extractCharge(int amount, EntityPlayer player);

    default boolean canReceiveCharge() {
        return true;
    }

    int injectCharge(int amount);

    class Impl implements PsiCell {

        @Override
        public int getStoredCharge() {
            return 0;
        }

        @Override
        public int getMaxCharge() {
            return 0;
        }

        @Override
        public int extractCharge(int amount, EntityPlayer player) {
            return 0;
        }

        @Override
        public int injectCharge(int amount) {
            return 0;
        }

    }

}
