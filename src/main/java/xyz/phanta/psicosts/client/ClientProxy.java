package xyz.phanta.psicosts.client;

import xyz.phanta.psicosts.CommonProxy;
import xyz.phanta.psicosts.util.MagicCircleRender;

public class ClientProxy extends CommonProxy {

    @Override
    public MagicCircleRender newMagicCircleRender() {
        return new MagicCircleRender.Impl();
    }

}
