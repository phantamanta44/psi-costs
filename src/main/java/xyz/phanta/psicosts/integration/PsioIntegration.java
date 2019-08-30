package xyz.phanta.psicosts.integration;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface PsioIntegration {

    default void registerEntries() {
        // NO-OP
    }

    @SideOnly(Side.CLIENT)
    default void registerEntriesClient() {
        // NO-OP
    }

    default void registerRecipes() {
        // NO-OP
    }

    @interface Register {

        String value();

    }

}
