package xyz.phanta.psicosts.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.internal.IPlayerData;
import vazkii.psi.common.core.handler.PlayerDataHandler;
import xyz.phanta.psicosts.PsioConfig;

public class PsiLevelUpHandler {

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (PsioConfig.maximumPsiOnJoin) {
            IPlayerData data = PsiAPI.internalHandler.getDataForPlayer(event.player);
            if (data instanceof PlayerDataHandler.PlayerData) {
                ((PlayerDataHandler.PlayerData)data).level = PsiAPI.levelCap;
            }
        }
    }

}
