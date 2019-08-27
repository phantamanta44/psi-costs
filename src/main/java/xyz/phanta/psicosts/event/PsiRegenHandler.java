package xyz.phanta.psicosts.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
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

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class PsiRegenHandler {

    @SubscribeEvent
    public void onPsiRegen(RegenPsiEvent event) {
        EntityPlayer player = event.getPlayer();
        if (!player.capabilities.isCreativeMode && event.getRegenCooldown() <= 0) {
            int cost = event.getPlayerRegen();
            if (cost > 0) {
                Collection<PsiCell> cells = Psio.PROXY.getIntegrations().getInv(player)
                        .filter(s -> s.hasCapability(PsioCaps.PSI_CELL, null))
                        .map(s -> Objects.requireNonNull(s.getCapability(PsioCaps.PSI_CELL, null)))
                        .collect(Collectors.toList());
                for (PsiCell cell : cells) {
                    cost -= cell.extractCharge(cost, player);
                    if (cost <= 0) {
                        break;
                    }
                }
                cost = event.getPlayerRegen() - cost;
                if (cost > 0) {
                    event.setMaxPlayerRegen(cost);
                } else {
                    event.setMaxPlayerRegen(0);
                    if (event.getCadRegen() <= 0) {
                        event.setRegenCooldown(20);
                    }
                }
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
