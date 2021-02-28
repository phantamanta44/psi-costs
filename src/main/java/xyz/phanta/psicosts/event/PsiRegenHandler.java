package xyz.phanta.psicosts.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.cad.RegenPsiEvent;
import vazkii.psi.api.internal.IPlayerData;
import vazkii.psi.common.core.handler.PlayerDataHandler;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.PsioConfig;
import xyz.phanta.psicosts.capability.PsiCell;
import xyz.phanta.psicosts.init.PsioCaps;
import xyz.phanta.psicosts.net.SPacketSyncPsiEnergy;

import java.util.Iterator;
import java.util.Objects;

public class PsiRegenHandler {

    @SubscribeEvent(priority=EventPriority.LOWEST)
    public void onPsiRegen(RegenPsiEvent event) {
        EntityPlayer player = event.getPlayer();
        if (!player.capabilities.isCreativeMode && event.getRegenCooldown() <= 0) {
            int cost_P = event.getPlayerRegen();
            int cost_C = event.getCadRegen();
            if (cost_P + cost_C > 0) {
                Iterator<PsiCell> cellIter = Psio.PROXY.getIntegrations().getInv(player)
                        .filter(s -> s.hasCapability(PsioCaps.PSI_CELL, null))
                        .map(s -> Objects.requireNonNull(s.getCapability(PsioCaps.PSI_CELL, null)))
                        .iterator();
                int[] cost = new int[2];
                if(event.willRegenCadFirst()) {
                	cost[0] = cost_C;
                	cost[1] = cost_P;		
                }
                else {
                	cost[0] = cost_P;
                	cost[1] = cost_C;	
                }
                for(int i = 0; i < 2; i++)
                	while (cost[i] > 0 && cellIter.hasNext()) {
                		cost[i] -= cellIter.next().extractCharge(cost[i], player);
                	}
                if(event.willRegenCadFirst()) {
                	cost_C = cost_C - cost[0];
                	cost_P = cost_P - cost[1];		
                }
                else {
                	cost_P = cost_P - cost[0];
                	cost_C = cost_C - cost[1];	
                }
                event.setMaxPlayerRegen(cost_P);
                event.setMaxCadRegen(cost_C);
                if(cost_P <= 0 && cost_C <= 0)
                	event.setRegenCooldown(20);
            }
        }
    }

    @SubscribeEvent
    public void onXpGain(PlayerPickupXpEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        IPlayerData psiData = PsiAPI.internalHandler.getDataForPlayer(player);
        if (psiData instanceof PlayerDataHandler.PlayerData) {
            int missingPsi = psiData.getTotalPsi() - psiData.getAvailablePsi();
            if (missingPsi > 0) {
                double portion = Math.min(missingPsi,
                        event.getOrb().xpValue * PsioConfig.xpConfig.xpPsiFraction * PsioConfig.xpConfig.xpPsiMultiplier);
                event.getOrb().xpValue -= Math.floor(portion / PsioConfig.xpConfig.xpPsiMultiplier);
                int newPsiEnergy = Math.min(psiData.getAvailablePsi() + (int)Math.ceil(portion), psiData.getTotalPsi());
                ((PlayerDataHandler.PlayerData)psiData).availablePsi = newPsiEnergy;
                psiData.save();
                if (player instanceof EntityPlayerMP) {
                    Psio.INSTANCE.getNetworkHandler().sendTo(new SPacketSyncPsiEnergy(newPsiEnergy), (EntityPlayerMP)player);
                }
            }
        }
    }

}
