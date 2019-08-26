package xyz.phanta.psicosts;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import xyz.phanta.psicosts.event.PsiRegenHandler;
import xyz.phanta.psicosts.integration.IntegrationManager;
import xyz.phanta.psicosts.net.SPacketSyncPsiEnergy;
import xyz.phanta.psicosts.util.MagicCircleRender;

public class CommonProxy {

    protected final IntegrationManager intManager = new IntegrationManager();

    public void onPreInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PsiRegenHandler());
        intManager.loadIntegrations(event.getAsmData());
        Psio.INSTANCE.getNetworkHandler().registerMessage(
                new SPacketSyncPsiEnergy.Handler(), SPacketSyncPsiEnergy.class, 0, Side.CLIENT);
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

    public MagicCircleRender newMagicCircleRender() {
        return new MagicCircleRender.Noop();
    }

}
