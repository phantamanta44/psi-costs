package xyz.phanta.psicosts;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.phanta.psicosts.event.PsiRegenHandler;
import xyz.phanta.psicosts.integration.IntegrationManager;
import xyz.phanta.psicosts.integration.PsioIntegration;

import java.util.ArrayList;
import java.util.Collection;

public class CommonProxy {

    private final IntegrationManager intManager = new IntegrationManager();

    public void onPreInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PsiRegenHandler());
        intManager.loadIntegrations(event.getAsmData());
    }

    public void onInit(FMLInitializationEvent event) {
        // NO-OP
    }

    public void onPostInit(FMLPostInitializationEvent event) {
        // NO-OP
    }

    public IntegrationManager getIntegrations() {
        return intManager;
    }

}
