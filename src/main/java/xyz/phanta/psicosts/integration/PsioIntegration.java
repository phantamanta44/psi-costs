package xyz.phanta.psicosts.integration;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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

    default void lateRegister() {
        // NO-OP
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface Register {

        String value();

    }

}
