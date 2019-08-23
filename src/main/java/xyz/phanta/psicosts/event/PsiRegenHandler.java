package xyz.phanta.psicosts.event;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.psi.api.cad.RegenPsiEvent;
import xyz.phanta.psicosts.Psio;
import xyz.phanta.psicosts.capability.PsiCell;
import xyz.phanta.psicosts.init.PsioCaps;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class PsiRegenHandler {

    @SubscribeEvent
    public void onPsiRegen(RegenPsiEvent event) {
        if (event.getRegenCooldown() <= 0) {
            int cost = event.getPlayerRegen();
            if (cost > 0) {
                Collection<PsiCell> cells = Psio.PROXY.getIntegrations().getInv(event.getPlayer())
                        .filter(s -> s.hasCapability(PsioCaps.PSI_CELL, null))
                        .map(s -> Objects.requireNonNull(s.getCapability(PsioCaps.PSI_CELL, null)))
                        .collect(Collectors.toList());
                for (PsiCell cell : cells) {
                    cost -= cell.extractCharge(cost, event.getPlayer());
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

}
