package xyz.phanta.psicosts.tile.base;

import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.util.MagicCircleRender;

public class TileInWorldPsiCharger extends TilePsiCharger {

    private final MagicCircleRender circleRender = Psio.PROXY.newMagicCircleRender();

    public TileInWorldPsiCharger(PsioConfig.ConversionConfig conversion) {
        super(conversion);
    }

    public MagicCircleRender getCircleRender() {
        return circleRender;
    }

}
