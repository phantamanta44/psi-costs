package xyz.phanta.psicosts.integration;

public interface PsioIntegration {

    default void registerEntries() {
        // NO-OP
    }

    @interface Register {

        String value();

    }

}
