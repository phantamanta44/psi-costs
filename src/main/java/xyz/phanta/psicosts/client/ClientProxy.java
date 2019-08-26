package xyz.phanta.psicosts.client;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.phanta.psicosts.CommonProxy;
import xyz.phanta.psicosts.util.MagicCircleRender;

public class ClientProxy extends CommonProxy {

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        super.onPreInit(event);
        intManager.initClient();
    }

    @Override
    public MagicCircleRender newMagicCircleRender() {
        return new MagicCircleRender.Impl();
    }

}
