package xyz.phanta.psicosts.event;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import vazkii.arl.network.NetworkHandler;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.internal.IPlayerData;
import vazkii.psi.common.command.CommandPsiLearn;
import vazkii.psi.common.core.handler.PlayerDataHandler;
import vazkii.psi.common.network.message.MessageDataSync;
import xyz.phanta.psicosts.PsioConfig;

public class PsiLevelUpHandler {

    private static final CommandPsiLearn teacher = new CommandPsiLearn();

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (PsioConfig.maximumPsiOnJoin) {
            IPlayerData dataUnchecked = PsiAPI.internalHandler.getDataForPlayer(event.player);
            if (dataUnchecked instanceof PlayerDataHandler.PlayerData) {
                PlayerDataHandler.PlayerData data = (PlayerDataHandler.PlayerData)dataUnchecked;
                teacher.applyAll(data, event.player, event.player);
                if (event.player instanceof EntityPlayerMP) {
                    NetworkHandler.INSTANCE.sendTo(new MessageDataSync(data), (EntityPlayerMP)event.player);
                }
            }
        }
    }

}
